package com.api.rest.biblioteca.combiblioteca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rest.biblioteca.combiblioteca.entities.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer> {

}
