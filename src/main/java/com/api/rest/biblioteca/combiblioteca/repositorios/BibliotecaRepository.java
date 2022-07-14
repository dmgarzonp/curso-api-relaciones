package com.api.rest.biblioteca.combiblioteca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rest.biblioteca.combiblioteca.entities.Biblioteca;

public interface BibliotecaRepository extends JpaRepository<Biblioteca, Integer> {

}
