package com.GestionGidisSoft.servicios.impl;

import com.GestionGidisSoft.entidades.CapituloLibro;
import com.GestionGidisSoft.entidades.Libro;
import com.GestionGidisSoft.repositorios.CapituloLibroRepo;
import com.GestionGidisSoft.repositorios.LibroRepo;
import com.GestionGidisSoft.servicios.CapituloLibroServicio;
import com.GestionGidisSoft.servicios.LibroServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CapituloLibroServicioImpl implements CapituloLibroServicio {

    @Autowired
    CapituloLibroRepo capituloLibroRepo;

    @Override
    public CapituloLibro guardarCapituloLibro(CapituloLibro capituloLibro) throws Exception {
        return capituloLibroRepo.save(capituloLibro);
    }

    @Override
    public void agregarRegistroAutorCapitulo(Long idCapitulo, Long idUsuario) {
        capituloLibroRepo.agregarRegistroAutorCapitulo(idCapitulo, idUsuario);
    }

    @Override
    public boolean insertarCoautor(Long idAutor, Long idCoautor, Long idCapitulo) {
        if(capituloLibroRepo.insertarCoautorCapitulo(idAutor, idCoautor, idCapitulo) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void eliminarRegistrosAutorCapitulo(Long idCapitulo, Long idAutor) {
        capituloLibroRepo.eliminarRegistrosAutorCapitulo(idCapitulo, idAutor);
    }

    @Override
    public void eliminarRegistrosCoautoresCapituloLibro(Long idCapitulo, Long idAutor) {
        capituloLibroRepo.eliminarCoautoresLibro(idCapitulo, idAutor);
    }

    //elimina un registro individual de la tabla coautores de la lista de coautores
    @Override
    public void eliminarRelacionCoautores(Long idCapitulo, Long idCoautor) {
        capituloLibroRepo.eliminarCoautorCapitulo(idCapitulo, idCoautor);
    }

    @Override
    public String actualizarCapituloLibro(CapituloLibro capituloLibro) throws Exception {
        try {
            if(capituloLibroRepo.actualizarCapituloLibro(capituloLibro.getIdCapitulo(), capituloLibro.getTitulo(),
                    capituloLibro.getSerieLibro(), capituloLibro.getAnio(), capituloLibro.getMes(), capituloLibro.getPaginaInicial(),
                    capituloLibro.getPaginaFinal(), capituloLibro.getDisciplina(), capituloLibro.getEdicion(), capituloLibro.getAreaConocimiento(),
                    capituloLibro.getLugarPublicacion(), capituloLibro.getMedioDivulgacion(), capituloLibro.getNumeroPaginas(),
                    capituloLibro.getIdLibro()) == 1) {
                return "Capitulo de libro actualizado exitosamente";
            } else {
                return "Hubo un error al actualizar el capitulo del libro";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(":: Error :: " + e.getMessage());
            return "Hubo un error al actualizar el Artículo";
        }

    }

    @Override
    public String cargarDocumentos(CapituloLibro capituloLibro) throws Exception {
        try {
            if(capituloLibroRepo.cargarDocumentos(capituloLibro.getIdLibro(), capituloLibro.getDocumentoEvidencia(),
                    capituloLibro.getCertificadoInstitucionAvala(), capituloLibro.getCertificadoCreditos()) == 1) {
                return "Documentos del Capitulo de libro cargados exitosamente";
            } else {
                return "Hubo un error al cargar los documentos del Capitulo del libro";
            }
        }  catch (Exception e) {
            e.printStackTrace();
            System.out.println(":: Error :: " + e.getMessage());
            return "Hubo un error inesperado";
        }
    }

    @Override
    public List<CapituloLibro> buscarCapitulosLibros() {
        List<CapituloLibro> capituloLibros = capituloLibroRepo.findAll();
        return capituloLibros;
    }

    @Override
    public CapituloLibro buscarPorId(long idCapitulo) {
        CapituloLibro libro = capituloLibroRepo.buscarPorId(idCapitulo);
        return libro;
    }

    @Override
    public void eliminar(long idCapitulo) {
        capituloLibroRepo.deleteById(idCapitulo);
    }

    @Override
    public List<CapituloLibro> findByUsuarioId(Long idUsuario) {
        List<CapituloLibro> capituloLibros = capituloLibroRepo.capituloslibroPorUsuario(idUsuario);
        return capituloLibros;
    }

}
