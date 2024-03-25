package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.entidades.CapituloLibro;

import java.util.List;
import java.util.Optional;

public interface CapituloLibroServicio {

    CapituloLibro guardarCapituloLibro(CapituloLibro capituloLibro) throws Exception;

    void agregarRegistroAutorCapitulo(Long idCapituloLibro, Long idAutor);

    boolean insertarCoautor(Long idAutor, Long idCoautor, Long idCapituloLibro);

    void eliminarRegistrosAutorCapitulo(Long idCapituloLibro, Long idAutor);

    void eliminarRegistrosCoautoresCapituloLibro(Long idCapituloLibro, Long idAutor);

    void eliminarRelacionCoautores(Long idCapituloLibro, Long idCoautor);


    String actualizarCapituloLibro(CapituloLibro capituloLibro) throws Exception;

    String cargarDocumentos(CapituloLibro capituloLibro) throws Exception;
    List<CapituloLibro> buscarCapitulosLibros();


    CapituloLibro buscarPorId(long idCapituloLibro);

    void eliminar(long idCapituloLibro);

    List<CapituloLibro> findByUsuarioId(Long id);

}
