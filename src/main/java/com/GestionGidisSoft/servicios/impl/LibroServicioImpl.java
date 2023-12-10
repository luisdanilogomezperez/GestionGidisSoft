package com.GestionGidisSoft.servicios.impl;

import com.GestionGidisSoft.entidades.Libro;
import com.GestionGidisSoft.repositorios.LibroRepo;
import com.GestionGidisSoft.servicios.LibroServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServicioImpl implements LibroServico {

    @Autowired
    LibroRepo libroRepo;

    @Override
    public Libro guardarLibro(Libro libro) throws Exception {
        return libroRepo.save(libro);
    }

    @Override
    public void actualizarTablaIntermedia(Long libroId, Long usuarioId) {
        libroRepo.actualizarTablaIntermedia(libroId, usuarioId);
    }

    @Override
    public void eliminarRelacionLibroUsuario(Long libroId, Long usuarioId) {
         libroRepo.deleteByLibroIdAndUsuarioId(libroId, usuarioId);
    }

    @Override
    public String actualizarLibro(Libro libro) throws Exception {
        if(libroRepo.actualizarLibro(libro.getLibroId(), libro.getTitulo(), libro.getAnio(), libro.getMes(),
                libro.getDisciplina(), libro.getEditorial(), libro.getIsbn(), libro.getLugarPublicacion(),
                libro.getMedioDivulgacion(), libro.getTipoEditorial()) == 1) {
            return "Libro actualizado exitosamente";
        } else {
            return "Hubo un error al actualizar el libro";
        }
//        libroRepo.actualizarLibro(libro);



    }

    @Override
    public List<Libro> buscarLibros() {
        List<Libro> libros = libroRepo.findAll();
        return libros;
    }

    @Override
    public Libro buscarPorId(long idLibro) {
        Libro libro = libroRepo.buscarPorId(idLibro);
        return libro;
    }

    @Override
    public Optional<Libro> findByIsbn(String isbn) {
        Optional<Libro> libro = libroRepo.findByIsbn(isbn);
        return libro;
    }

    @Override
    public void eliminar(long libroId) {
        libroRepo.deleteById(libroId);
    }

    @Override
    public List<Libro> findByUsuarioId(Long id) {
        List<Libro> libros = libroRepo.librosPorUsuario(id);
        return libros;
    }

}
