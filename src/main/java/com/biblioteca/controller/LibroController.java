package com.biblioteca.controller;

import java.util.Date;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;

/**
 * @author Cortez
 */

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entity.Libro;
import com.biblioteca.service.LibroService;
import com.biblioteca.util.AppSettings;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;



@RestController
@RequestMapping("/url/libro")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class LibroController {
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	private ResourceLoader resourceloader;
	
	@Autowired
	private LibroService libroService;

	@GetMapping("/listaTodos")
	@ResponseBody
	public ResponseEntity<List<Libro>> listaLibro(){
		List<Libro> lista = libroService.listaTodos();
		return ResponseEntity.ok(lista);
	}

	@PostMapping
	@ResponseBody
	public  ResponseEntity<?> insertaLibro( @RequestBody Libro obj, Errors errors){
		Map<String, Object> salida = new HashMap<>();
		List<String> lstMensajes = new ArrayList<>();
		salida.put("errores", lstMensajes);
		
		obj.setFechaRegistro(new Date());
		obj.setFechaActualizacion(new Date());
		obj.setEstado(1);

		System.out.println(obj);
		Libro objSalida = libroService.insertaActualizaLibro(obj);
		if (objSalida == null) {
			lstMensajes.add("Error en el registro");
		}else {
			lstMensajes.add("Se registró el Libro con ID ==> " + objSalida.getIdLibro());
		}
		
		return ResponseEntity.ok(salida);
	}
	
	@PutMapping("/actualizaLibro")
	@ResponseBody
	public  ResponseEntity<?> actualizaLibro( @RequestBody Libro obj, Errors errors){
		Map<String, Object> salida = new HashMap<>();
		List<String> lstMensajes = new ArrayList<>();
		salida.put("errores", lstMensajes);
		
		System.out.println(obj);
		Libro objSalida = libroService.insertaActualizaLibro(obj);
		if (objSalida == null) {
			lstMensajes.add("Error al actualizar registro");
		}else {
			lstMensajes.add("Se actualizó el Libro con ID ==> " + objSalida.getIdLibro());
		}
		
		return ResponseEntity.ok(salida);
	}
	
	@GetMapping("/listaLibroConParametros")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> listarPorTituloAnioSerie(
			@RequestParam(name="titulo",required=false,defaultValue="")String titulo,
			@RequestParam(name="anio",required=false)Integer anio,
			@RequestParam(name="serie",required=false,defaultValue="")String serie) {
		Map<String, Object> salida = new HashMap<>();
		
		try {
			List<Libro> lstLibro = libroService.listaLibroPorTituloAnioSerie("%"+titulo+"%",anio,"%"+serie+"%");

			if (CollectionUtils.isEmpty(lstLibro)) {
				salida.put("mensaje","No existen datos para la consulta");
			}else {
				
				salida.put("lista",lstLibro);
				salida.put("mensaje","Existen " + lstLibro.size() + " libros que fueron econtrados");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return ResponseEntity.ok(salida);
	}
	
	@DeleteMapping("/eliminaLibro/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> eliminarLibro(@PathVariable("id") int idLibro) {
		Map<String, Object> salida = new HashMap<>();
		try {
			libroService.eliminaLibro(idLibro);
			salida.put("mensaje", "Se eliminó libro correctamente");
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "No se pudo eliminar el libro.");
			System.out.println(e.getMessage());
		}
		return ResponseEntity.ok(salida);
	}
	
	@GetMapping("/verPDF")
	public void verReporte(HttpServletResponse response) {
		
		response.setHeader("Content-Disposition", "inline;");
		response.setContentType("application/pdf");
		
		try {
			String ru = resourceloader.getResource("classpath:reporte/rptbiblioteca.jasper").getURI().getPath();
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, datasource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
