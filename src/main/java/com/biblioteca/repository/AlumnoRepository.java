package com.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.biblioteca.entity.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer>{
	
	@Query("select a from Alumno a where(?1 is '' or a.dni like ?1) and (?2 is -1 or a.pais.idPais = ?2) and (?3 is -1 or a.modalidad.idDataCatalogo = ?3)")
	List<Alumno> listaAlumnoPorDniPaisModalidad(String dni, int idPais, int idModalidad);

}
