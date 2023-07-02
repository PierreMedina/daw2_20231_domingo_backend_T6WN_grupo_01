package com.biblioteca.service;

import java.util.List;

import com.biblioteca.entity.Sala;

public interface SalaService {

	public Sala insertaActualizaSala(Sala obj);
	public abstract List<Sala> listaTodos();

}
