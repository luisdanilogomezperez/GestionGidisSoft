package com.GestionGidisSoft.servicios.impl;

import com.GestionGidisSoft.entidades.Libro;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.repositorios.LibroRepo;
import com.GestionGidisSoft.servicios.LibroServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void actualizarTablaIntermedia(Long idLibro, Long idAutor) {
        libroRepo.actualizarTablaIntermedia(idLibro, idAutor);
    }

    @Override
    public boolean insertarCoautor(Long idLibro, Long idAutor, Long idCoautor) {
        if(libroRepo.insertarCoautor(idLibro, idAutor, idCoautor) == 1) {
            return true;
        } else {
            return false;
        }
    }

    // elimina todos los registros de la tabla coautores libro donde
    // el id del libro y del autor coinsidan
    @Override
    public void eliminarRelacionLibroUsuario(Long idLibro, Long idAutor) {

    }

    @Override
    public void eliminarRegistrosAutorLibro(Long idLibro, Long idAutor) {
        libroRepo.eliminarRegistrosAutorLibro(idLibro, idAutor);
    }

    //elimina un registro individual de la tabla coautores de la lista de coautores
    @Override
    public void eliminarRelacionCoautores(Long idLibro, Long idCoautor) {
        libroRepo.eliminarCoautor(idLibro, idCoautor);
    }

    @Override
    public String actualizarLibro(Libro libro) throws Exception {
        if(libroRepo.actualizarLibro(libro.getIdLibro(), libro.getTitulo(), libro.getAnio(), libro.getMes(),
                libro.getDisciplina(), libro.getEditorial(), libro.getIsbn(), libro.getLugarPublicacion(),
                libro.getMedioDivulgacion(), libro.getTipoEditorial(), libro.getDocumentoEvidencia(),
                libro.getCertificadoCreditos(), libro.getCertificadoInstitucionAvala()) == 1) {
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
    public void eliminar(long idLibro) {
        libroRepo.deleteById(idLibro);
    }

    @Override
    public List<Libro> findByUsuarioId(Long idAutor) {
        List<Libro> libros = libroRepo.librosPorUsuario(idAutor);
        return libros;
    }

}
