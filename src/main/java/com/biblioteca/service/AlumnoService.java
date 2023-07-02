package com.biblioteca.service;

import java.util.List;

import com.biblioteca.entity.Alumno;

public interface AlumnoService {

	public abstract List<Alumno> listaTodos();
	public Alumno insertaAlumno(Alumno obj);
	public abstract List<Alumno> listaAlumnoPorDniPaisModalidad(String dni, int idPais, int idModalidad);

}
