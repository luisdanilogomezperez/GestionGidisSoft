package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.entidades.Articulo;
import com.GestionGidisSoft.entidades.Libro;

import java.util.List;
import java.util.Optional;

public interface ArticuloServicio {

    Articulo guardarArticulo(Articulo articulo) throws Exception;

    Articulo actualizarArticulo(Articulo articulo) throws Exception;

    void eliminarArticulo(Long idArticulo, Long idAutor);

    Articulo buscarArticuloPorId(Long idArticulo) throws Exception;

    Articulo buscarPorId(Long isArticulo) throws Exception;
    List<Articulo> listarArticulos()  throws Exception;

    List<Articulo> findByUsuarioId(Long idAutor)  throws Exception;

    void agregarRegistroAutorArticulo(Long idArticulo, Long idAutor) throws Exception;

    void eliminarRegistroAutorArticulo(Long idArticulo, Long idAutor) throws Exception;

    Boolean agregarCoautorArticulo(Long idArticulo, Long idAutor, Long idCoautor) throws Exception;

    Boolean eliminarCoautorArticulo(Long idArticulo, Long idAutor, Long idCoautor) throws Exception;

    Optional<Articulo> findByDoi(String doi);
}
