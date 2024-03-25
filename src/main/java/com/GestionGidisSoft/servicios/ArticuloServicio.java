package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.entidades.Articulo;

import java.util.List;
import java.util.Optional;

public interface ArticuloServicio {

    Articulo guardarArticulo(Articulo articulo) throws Exception;

    String actualizarArticulo(Articulo articulo) throws Exception;

    String cargarDocumento(Articulo articulo) throws Exception;

    void eliminarArticulo(Long idArticulo);

    Articulo buscarArticuloPorId(Long idArticulo) throws Exception;

    Articulo buscarPorId(Long idArticulo) throws Exception;
    
    List<Articulo> listarArticulos()  throws Exception;

    List<Articulo> findByUsuarioId(Long idAutor)  throws Exception;

    void agregarRegistroAutorArticulo(Long idArticulo, Long idAutor) throws Exception;

    void eliminarRegistroAutoresArticulo(Long idArticulo, Long idAutor) throws Exception;

    Boolean agregarRegistroCoautorArticulo(Long idArticulo, Long idAutor, Long idCoautor) throws Exception;

    void eliminarRegistroCoautoresArticulo(Long idArticulo, Long idAutor);

    void eliminarRegistroCoautorArticulo(Long idArticulo, Long idCoautor);

    Optional<Articulo> findByDoi(String doi);
}
