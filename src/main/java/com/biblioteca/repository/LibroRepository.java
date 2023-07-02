package com.biblioteca.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.biblioteca.entity.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer>{
	
	@Query("select t from Libro t where(?1 is '' or t.titulo like ?1) and (?2 is 0 or t.anio like ?2) and (?3 is '' or t.serie like ?3)")
	List<Libro> listaLibroPorTituloAnioSerie(String titulo, int anio, String serie);

}
