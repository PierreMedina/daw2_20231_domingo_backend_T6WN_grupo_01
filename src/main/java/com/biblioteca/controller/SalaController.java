package com.biblioteca.controller;
/**

 * @author MARILIAN PITTMAN

 */


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

/**

 * @author Marilian Pittman S

 */

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entity.Sala;
import com.biblioteca.service.SalaService;
import com.biblioteca.util.AppSettings;

@RestController
@RequestMapping("/url/sala")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class SalaController {

	@Autowired
	private SalaService salaService;

	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Sala>> listaSala(){
		List<Sala> lista = salaService.listaTodos();
		return ResponseEntity.ok(lista);
	}

	@PostMapping
	@ResponseBody
	public  ResponseEntity<?> insertaModalidad( @RequestBody Sala obj, Errors errors){
		Map<String, Object> salida = new HashMap<>();
		List<String> lstMensajes = new ArrayList<>();
		salida.put("errores", lstMensajes);
		obj.setFechaRegistro(new Date());
		obj.setFechaActualizacion(new Date());
		obj.setEstado(1);
		Sala objSalida = salaService.insertaActualizaSala(obj);
		if (objSalida == null) {
			lstMensajes.add("Error en el registro");
		}else {
			lstMensajes.add("Se registró la sala de ID ==> " + objSalida.getIdSala());
		}
		
		return ResponseEntity.ok(salida);
	}
	
	
	
	
}

