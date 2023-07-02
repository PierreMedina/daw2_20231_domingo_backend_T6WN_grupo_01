package com.biblioteca.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
/**
 * @author José Ramírez
 */
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entity.Tesis;
import com.biblioteca.service.TesisService;
import com.biblioteca.util.AppSettings;


@RestController
@RequestMapping("/url/tesis") 
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class TesisController {
	
	@Autowired
	private TesisService TesisService;

	@GetMapping()
	@ResponseBody
	public ResponseEntity<List<Tesis>> listaModalidad(){
		List<Tesis> lista = TesisService.listaTesis();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/listaTesisConParametros")
	@ResponseBody
	public  ResponseEntity<Map<String, Object>> listarPorTemaAlumnoTitulo
			(@RequestParam(name="tema",required=false,defaultValue="")String tema,
			 @RequestParam(name="titulo",required=false,defaultValue="")String titulo,
			 @RequestParam(name="idAlumno",required=false,defaultValue="-1") int idAlumno)
	{
		Map<String, Object> salida = new HashMap<>();
		
		try {
			List<Tesis> lstTesis = TesisService.listaTesisPorTemaAlumnoTitulo("%"+tema+"%","%"+titulo+"%",idAlumno);
						
			if (CollectionUtils.isEmpty(lstTesis)) {
				salida.put("mensaje","No existen datos para la consulta");
			}else {
				
				salida.put("lista",lstTesis);
				salida.put("mensaje","Existen " + lstTesis.size() + " tesis que fueron econtradas");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return ResponseEntity.ok(salida);				
	}
	
	@PostMapping
	@ResponseBody
	public  ResponseEntity<?> insertaModalidad( @RequestBody Tesis obj, Errors errors){
		Map<String, Object> salida = new HashMap<>();
		List<String> lstMensajes = new ArrayList<>();
		salida.put("errores", lstMensajes);
		obj.setFechaRegistro(new Date());
		obj.setEstado(1);

		Tesis objSalida = TesisService.insertaTesis(obj);
		if (objSalida == null) {
			lstMensajes.add("Error en el registro");
		}else {
			lstMensajes.add("Se registró la tesis de ID ==> " + objSalida.getIdTesis());
		}
		
		return ResponseEntity.ok(salida);				
	}
	
}
