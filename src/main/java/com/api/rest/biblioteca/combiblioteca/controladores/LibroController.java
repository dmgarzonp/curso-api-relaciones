package com.api.rest.biblioteca.combiblioteca.controladores;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.rest.biblioteca.combiblioteca.entities.Biblioteca;
import com.api.rest.biblioteca.combiblioteca.entities.Libro;
import com.api.rest.biblioteca.combiblioteca.repositorios.BibliotecaRepository;
import com.api.rest.biblioteca.combiblioteca.repositorios.LibroRepository;

@Controller
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @GetMapping
    public ResponseEntity<Page<Libro>> listarLibros(Pageable pageable) {
        return ResponseEntity.ok(libroRepository.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Libro> guardarLibro(@Valid @RequestBody Libro libro) {
        Optional<Biblioteca> bibliotecaOptional = bibliotecaRepository.findById(libro.getBiblioteca().getId());
        if (!bibliotecaOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        libro.setBiblioteca(bibliotecaOptional.get());
        Libro libroGuardado = libroRepository.save(libro);
        URI ublicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(libroGuardado.getId()).toUri();

        return ResponseEntity.created(ublicacion).body(libroGuardado);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(@Valid @RequestBody Libro libro, @PathVariable Integer id) {

        Optional<Biblioteca> bibliotecaOptional = bibliotecaRepository.findById(libro.getBiblioteca().getId());
        if (!bibliotecaOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Libro> libroOptional = libroRepository.findById(id);
        if (!libroOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        libro.setBiblioteca(bibliotecaOptional.get());
        libro.setId(libroOptional.get().getId());
        libroRepository.save(libro);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Libro> eliminarLibro(@PathVariable Integer id) {
        Optional<Libro> libroOptional = libroRepository.findById(id);
        if (!libroOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        libroRepository.delete(libroOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerLibroPorId(@PathVariable Integer id) {

        Optional<Libro> libroOptional = libroRepository.findById(id);
        if (!libroOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(libroOptional.get());

    }

}
