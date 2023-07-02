package com.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entity.Tesis;
import com.biblioteca.repository.TesisRepository;

@Service
public class TesisServiceImp implements TesisService{

	@Autowired
	private TesisRepository repositorio;
	
	@Override
	public List<Tesis> listaTesis() {
		return repositorio.findAll();
	}

	@Override
	public Tesis insertaTesis(Tesis obj) {
		return repositorio.save(obj);
	}

	@Override
	public List<Tesis> listaTesisPorTemaAlumnoTitulo(String tema, String titulo, int idAlumno) {
		return repositorio.listaTesisPorTemaAlumnoTitulo(tema, titulo, idAlumno);
	}

}
