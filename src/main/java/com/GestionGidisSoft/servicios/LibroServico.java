package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.entidades.Libro;
import com.GestionGidisSoft.entidades.Usuario;

import java.util.List;
import java.util.Optional;

public interface LibroServico {

    public Libro guardarLibro(Libro libro) throws Exception;

    public void actualizarTablaIntermedia(Long idLibro, Long idAutor);

    public boolean insertarCoautor(Long idLibro, Long idAutor, Long idCoautor);

    public void eliminarRelacionLibroUsuario(Long idLibro, Long idAutor);

    void eliminarRegistrosAutorLibro(Long idLibro, Long idAutor);

    void eliminarRelacionCoautores(Long idLibro, Long idCoautor);


    public String actualizarLibro(Libro libro) throws Exception;

    public List<Libro> buscarLibros();

    public Libro buscarPorId(long idLibro);

    public Optional<Libro> findByIsbn(String isbn);

    public void eliminar(long idLibro);

    public List<Libro> findByUsuarioId(Long idAutor);


}
