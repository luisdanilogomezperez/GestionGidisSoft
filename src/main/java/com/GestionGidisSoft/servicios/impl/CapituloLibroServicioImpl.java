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
    public boolean insertarCoautor(Long idCapitulo, Long idAutor, Long idCoautor) {
        if(capituloLibroRepo.insertarCoautorCapitulo(idCapitulo, idAutor, idCoautor) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void eliminarRegistrosAutorCapitulo(Long idCapitulo, Long idAutor) {
        capituloLibroRepo.eliminarRegistrosAutorCapitulo(idAutor, idCapitulo);
    }

    @Override
    public void eliminarRegistrosAutorCapituloLibro(Long idCapituloLibro, Long idAutor) {
        capituloLibroRepo.eliminarRegistrosAutorCapitulo(idAutor,idCapituloLibro);
    }

    //elimina un registro individual de la tabla coautores de la lista de coautores
    @Override
    public void eliminarRelacionCoautores(Long idCapitulo, Long idCoautor) {
        capituloLibroRepo.eliminarCoautorCapitulo(idCapitulo, idCoautor);
    }

    @Override
    public String actualizarCapituloLibro(CapituloLibro capituloLibro) throws Exception {
        if(capituloLibroRepo.actualizarCapituloLibro(capituloLibro.getIdCapitulo(), capituloLibro.getTitulo(),
                capituloLibro.getSerieLibro(), capituloLibro.getAnio(), capituloLibro.getMes(), capituloLibro.getPaginaInicial(),
                capituloLibro.getPaginaFinal(), capituloLibro.getDisciplina(), capituloLibro.getEdicion(), capituloLibro.getAreaConocimiento(),
                capituloLibro.getLugarPublicacion(), capituloLibro.getMedioDivulgacion(), capituloLibro.getNumeroPaginas(),
                capituloLibro.getDocumentoEvidencia(), capituloLibro.getCertificadoCreditos(), capituloLibro.getCertificadoInstitucionAvala(),
                capituloLibro.getIdLibro()) == 1) {
            return "Capitulo de libro actualizado exitosamente";
        } else {
            return "Hubo un error al actualizar el capitulo del libro";
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
