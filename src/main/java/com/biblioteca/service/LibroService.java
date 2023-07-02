package com.biblioteca.service;

import java.util.List;
import com.biblioteca.entity.Libro;


public interface LibroService {
	
	public Libro insertaActualizaLibro(Libro obj);
	public abstract List<Libro> listaTodos();
	public abstract void eliminaLibro(int id);
	
	public List<Libro> listaLibroPorTituloAnioSerie(String titulo, Integer anio, String serie);

}
