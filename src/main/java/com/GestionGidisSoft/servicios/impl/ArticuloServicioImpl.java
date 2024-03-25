package com.GestionGidisSoft.servicios.impl;

import com.GestionGidisSoft.entidades.Articulo;
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
    public String actualizarArticulo(Articulo articulo) throws Exception {
        try {
            if (articuloRepo.actualizarArticulo(articulo.getIdArticulo(), articulo.getTitulo(), articulo.getAnio(),
                    articulo.getMes(), articulo.getMedioDivulgacion(), articulo.getPaginaInicial(),
                    articulo.getPaginaFinal(), articulo.getNombreRevista(), articulo.getTipoArticulo(), articulo.getVolumen(),
                    articulo.getFasciculoRevista(), articulo.getSerieRevista(), articulo.getLugarPublicacion(),
                    articulo.getIdentificadorDigitalDoi(), articulo.getIdioma(), articulo.getUrlSitioWeb()) == 1) {
                return "Artículo actualizado exitosamente";
            } else {
                return "Hubo un error al actualizar el Artículo";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(":: Error :: " + e.getMessage());
            return "Hubo un error inesperado";
        }
    }

    @Override
    public String cargarDocumento(Articulo articulo) throws Exception {
        try {
            if (articuloRepo.cargarDocumento(articulo.getIdArticulo(), articulo.getDocumentoEvidencia()) == 1) {
                return "Documento del Artículo cargado exitosamente";
            } else {
                return "Hubo un error al cargar el documento del Artículo";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(":: Error :: " + e.getMessage());
            return "Hubo un error inesperado";
        }
    }

    @Override
    public void eliminarArticulo(Long idArticulo) {
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
    public void eliminarRegistroAutoresArticulo(Long idArticulo, Long idAutor) throws Exception {
        articuloRepo.eliminarAutorArticulo(idArticulo, idAutor);
    }

    @Override
    public Boolean agregarRegistroCoautorArticulo(Long idArticulo, Long idAutor, Long idCoautor) throws Exception {
        if(articuloRepo.insertarCoautor(idArticulo, idAutor, idCoautor) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void eliminarRegistroCoautoresArticulo(Long idArticulo, Long idAutor) {
        articuloRepo.eliminarCoautoresArticulo(idArticulo, idAutor);
    }

    @Override
    public void eliminarRegistroCoautorArticulo(Long idArticulo, Long idCoautor){
        articuloRepo.eliminarCoautor(idArticulo, idCoautor);
    }

    @Override
    public Optional<Articulo> findByDoi(String doi) {
        Optional<Articulo> articulo = articuloRepo.buscarPorIdentificadorDoi(doi);
        return articulo;
    }
}
