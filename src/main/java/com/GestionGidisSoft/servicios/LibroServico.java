package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.entidades.Libro;
import com.GestionGidisSoft.entidades.Usuario;

import java.util.List;
import java.util.Optional;

public interface LibroServico {

    Libro guardarLibro(Libro libro) throws Exception;

    void actualizarTablaIntermedia(Long idLibro, Long idAutor);

    boolean insertarCoautor(Long idLibro, Long idAutor, Long idCoautor);
    void eliminarRelacionLibroUsuario(Long idLibro, Long idAutor);

    void eliminarRegistrosAutorLibro(Long idLibro, Long idAutor);

    void eliminarRelacionCoautores(Long idLibro, Long idCoautor);

    String actualizarLibro(Libro libro) throws Exception;

    String cargarDocumentos(Libro libro) throws Exception;

    List<Libro> buscarLibros();

    Libro buscarPorId(long idLibro);

    Optional<Libro> findByIsbn(String isbn);

    void eliminar(long idLibro);

    List<Libro> findByUsuarioId(Long idAutor);
}
