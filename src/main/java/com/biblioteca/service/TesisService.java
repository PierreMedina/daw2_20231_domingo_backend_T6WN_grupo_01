package com.biblioteca.service;

import java.util.List;

import com.biblioteca.entity.Tesis;

public interface TesisService {
	
	public abstract List<Tesis> listaTesis();
	public abstract Tesis insertaTesis(Tesis obj);
	public abstract List<Tesis> listaTesisPorTemaAlumnoTitulo(String tema, String titulo, int idAlumno);
	
}
