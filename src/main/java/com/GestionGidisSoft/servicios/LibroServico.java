package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.entidades.Libro;
import com.GestionGidisSoft.entidades.Usuario;

import java.util.List;
import java.util.Optional;

public interface LibroServico {

    public Libro guardarLibro(Libro libro) throws Exception;

    public void actualizarTablaIntermedia(Long libroId, Long usuarioId);

    public boolean insertarCoautor(Long libroId, Long autorId, Long coautorId);

    public void eliminarRelacionLibroUsuario(Long libroId, Long usuarioId);

    void eliminarRegistrosAutorLibro(Long idLibro, Long idAutor);

    void eliminarRelacionCoautores(Long idLibro, Long idcoautor);


    public String actualizarLibro(Libro libro) throws Exception;

    public List<Libro> buscarLibros();

    public Libro buscarPorId(long idLibro);

    public Optional<Libro> findByIsbn(String isbn);

    public void eliminar(long libroId);

    public List<Libro> findByUsuarioId(Long id);


}
