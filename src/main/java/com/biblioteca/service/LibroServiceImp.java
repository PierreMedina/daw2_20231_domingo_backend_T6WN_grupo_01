package com.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entity.Libro;
import com.biblioteca.repository.LibroRepository;

@Service
public class LibroServiceImp implements LibroService {
	
	@Autowired
	private LibroRepository repository;

	@Override
	public List<Libro> listaTodos() {
		return repository.findAll();
	}

	@Override
	public Libro insertaActualizaLibro(Libro obj) {
		return repository.save(obj);
	}

	@Override
	public List<Libro> listaLibroPorTituloAnioSerie(String titulo, Integer anio, String serie) {
		return repository.listaLibroPorTituloAnioSerie(titulo, anio, serie);
	}

	@Override
	public void eliminaLibro(int id) {
		repository.deleteById(id);
	}

	

	

}
