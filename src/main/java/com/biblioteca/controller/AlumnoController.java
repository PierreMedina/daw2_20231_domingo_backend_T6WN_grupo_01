package com.biblioteca.controller;
/**
 * @author PIERRE MEDINA
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entity.Alumno;
import com.biblioteca.service.AlumnoService;
import com.biblioteca.util.AppSettings;


@RestController
@RequestMapping("/url/alumno")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;

	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Alumno>> listaAlumno(){
		List<Alumno> lista = alumnoService.listaTodos();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/listaAlumnoConParametros")
	@ResponseBody
	public  ResponseEntity<Map<String, Object>> listarPorTemaAlumnoTitulo
			(@RequestParam(name="dni",required=false,defaultValue="")String dni,
			 @RequestParam(name="pais",required=false,defaultValue="")int idPais,
			 @RequestParam(name="modalidad",required=false,defaultValue="-1") int idModalidad)
	{
		Map<String, Object> salida = new HashMap<>();
		
		try {
			List<Alumno> lstAlumno = alumnoService.listaAlumnoPorDniPaisModalidad("%"+dni+"%",idPais,idModalidad);
						
			if (CollectionUtils.isEmpty(lstAlumno)) {
				salida.put("mensaje","No existen datos para la consulta");
			}else {
				
				salida.put("lista",lstAlumno);
				salida.put("mensaje","Existen " + lstAlumno.size() + " alumnos que fueron econtradas");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return ResponseEntity.ok(salida);				
	}

	@PostMapping
	@ResponseBody
	public  ResponseEntity<?> insertaAlumno( @RequestBody Alumno obj, Errors errors){
		Map<String, Object> salida = new HashMap<>();
		List<String> lstMensajes = new ArrayList<>();
		salida.put("errores", lstMensajes);
		obj.setFechaRegistro(new Date());
		obj.setFechaActualizacion(new Date());
		obj.setEstado(1);
		Alumno objSalida = alumnoService.insertaAlumno(obj);
		if (objSalida == null) {
			lstMensajes.add("Error en el registro");
		}else {
			lstMensajes.add("Se registró el alumno de ID ==> " + objSalida.getIdAlumno());
		}
		
		return ResponseEntity.ok(salida);
	}

}
