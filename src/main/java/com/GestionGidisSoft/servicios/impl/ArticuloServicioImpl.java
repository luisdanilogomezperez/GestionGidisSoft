package com.GestionGidisSoft.servicios.impl;

import com.GestionGidisSoft.entidades.Articulo;
import com.GestionGidisSoft.entidades.Libro;
import com.GestionGidisSoft.repositorios.ArticuloRepo;
import com.GestionGidisSoft.servicios.ArticuloServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticuloServicioImpl implements ArticuloServicio {

    @Autowired
    private ArticuloRepo articuloRepo;

    @Override
    public Articulo guardarArticulo(Articulo articulo) throws Exception {
        return articuloRepo.save(articulo);
    }

    @Override
    public Articulo actualizarArticulo(Articulo articulo) throws Exception {
        return null;
    }

    @Override
    public void eliminarArticulo(Long idArticulo, Long idAutor) {
        articuloRepo.deleteById(idArticulo);
    }

    @Override
    public Articulo buscarArticuloPorId(Long idArticulo) throws Exception {
        Articulo articulo = articuloRepo.buscarPorId(idArticulo);
        return articulo;
    }

    @Override
    public  Articulo buscarPorId(Long idArticulo) throws Exception {
        Articulo articulo = articuloRepo.buscarPorId(idArticulo);
        return articulo;
    }

    @Override
    public List<Articulo> listarArticulos() throws Exception {
        List<Articulo> listaArticulos = articuloRepo.findAll();
        return listaArticulos;
    }

    @Override
    public List<Articulo> findByUsuarioId(Long idAutor) throws Exception {
        List<Articulo> listaArticulos = articuloRepo.listarArticulosPorAutor(idAutor);
        return listaArticulos;
    }

    @Override
    public void agregarRegistroAutorArticulo(Long idArticulo, Long idAutor) throws Exception {
        articuloRepo.agregarAutorArticulo(idArticulo, idAutor);
    }

    @Override
    public void eliminarRegistroAutorArticulo(Long idArticulo, Long idAutor) throws Exception {
        articuloRepo.eliminarAutorArticulo(idArticulo, idAutor);
    }

    @Override
    public Boolean agregarCoautorArticulo(Long idArticulo, Long idAutor, Long idCoautor) throws Exception {
        if(articuloRepo.insertarCoautor(idArticulo, idAutor, idCoautor) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean eliminarCoautorArticulo(Long idArticulo, Long idAutor, Long idCoautor) throws Exception {
        if(articuloRepo.eliminarCoautor(idArticulo, idCoautor) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Articulo> findByDoi(String doi) {
        Optional<Articulo> articulo = articuloRepo.buscarPorIdentificadorDoi(doi);
        return articulo;
    }
}
