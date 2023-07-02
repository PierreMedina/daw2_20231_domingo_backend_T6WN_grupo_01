package com.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entity.Sala;
import com.biblioteca.repository.SalaRepository;

@Service
public class SalaServiceImp implements SalaService {

	@Autowired
	private SalaRepository repository;

	@Override
	public List<Sala> listaTodos() {
		return repository.findAll();

	}

	@Override
	public Sala insertaActualizaSala(Sala obj) {
		return repository.save(obj);
	}

}
