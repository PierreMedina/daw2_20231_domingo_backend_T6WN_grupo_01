package com.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.biblioteca.entity.Tesis;

public interface TesisRepository extends JpaRepository<Tesis, Integer>{
	
	@Query("select t from Tesis t where(?1 is '' or t.tema like ?1) and (?2 is '' or t.titulo like ?2) and (?3 is -1 or t.alumno.idAlumno = ?3)")
	List<Tesis> listaTesisPorTemaAlumnoTitulo(String tema, String titulo, int idAlumno);

}
