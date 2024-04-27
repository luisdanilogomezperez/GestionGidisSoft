package com.GestionGidisSoft.servicios.impl;

import com.GestionGidisSoft.entidades.ProyectoInvestigacion;
import com.GestionGidisSoft.repositorios.ProyectoInvestigacionRepo;
import com.GestionGidisSoft.servicios.ProyectoInvestigacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public class ProyectoInvestigacionServicioImpl implements ProyectoInvestigacionServicio {

    @Autowired
    private ProyectoInvestigacionRepo investigacionRepo;

    @Override
    public ProyectoInvestigacion guardarProyectoInvestigacion(ProyectoInvestigacion proyectoInvestigacion) throws Exception {
        try {
            return investigacionRepo.save(proyectoInvestigacion);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error::: " + e.getMessage());
            return new ProyectoInvestigacion();
        }
    }

    @Override
    public String actualizarProyectoInvestigacion(ProyectoInvestigacion proyectoInvestigacion) throws Exception {
        try {

        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public String cargarDocumento(ProyectoInvestigacion proyectoInvestigacion) throws Exception {
        return null;
    }

    @Override
    public void eliminarProyectoInvestigacion(Long idProyectoInvestigacion) {
        investigacionRepo.deleteById(idProyectoInvestigacion);
    }

    @Override
    public ProyectoInvestigacion buscarPorId(Long idProyectoInvestigacion) throws Exception {
        try {
            ProyectoInvestigacion proyectoInvestigacion = investigacionRepo.buscarPorId(idProyectoInvestigacion);
            return proyectoInvestigacion;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error::: " + e.getMessage());
            return new ProyectoInvestigacion();
        }
    }

    @Override
    public List<ProyectoInvestigacion> listarProyectoInvestigacion() throws Exception {
        return null;
    }

    @Override
    public List<ProyectoInvestigacion> findByUsuarioId(Long idAutor) throws Exception {
        try {
            List<ProyectoInvestigacion> listaProyectos = investigacionRepo.proyectoInvestigacionPorUsuario(idAutor);
            return listaProyectos;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error::: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void agregarRegistroAutorProyectoInvestigacion(Long idProyectoInvestigacion, Long idAutor) throws Exception {
        try {
            investigacionRepo.insertarUsusarioProyectoInvestigacion(idProyectoInvestigacion, idAutor);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error::: " + e.getMessage());
        }
    }

    @Override
    public void eliminarRegistroAutoresProyectoInvestigacion(Long idProyectoInvestigacion, Long idAutor) throws Exception {
        try {
            investigacionRepo.eliminarRegistrosAutorProyectoInvestigacion(idProyectoInvestigacion, idAutor);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error::: " + e.getMessage());
        }
    }

    @Override
    public Boolean agregarRegistroCoautorProyectoInvestigacion(Long idProyectoInvestigacion, Long idAutor, Long idCoautor) throws Exception {
        try {
            if(investigacionRepo.insertarCoautor(idProyectoInvestigacion, idAutor, idCoautor) == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error::: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void eliminarRegistroCoautoresProyectoInvestigacion(Long idProyectoInvestigacion, Long idAutor) throws Exception{
        try {
            investigacionRepo.eliminarCoautoresProyectoInvestigacion(idProyectoInvestigacion, idAutor);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error::: " + e.getMessage());
        }
    }

    @Override
    public void eliminarRegistroCoautorProyectoInvestigacion(Long idProyectoInvestigacion, Long idCoautor) throws Exception{
        try {
            investigacionRepo.eliminarCoautor(idProyectoInvestigacion, idCoautor);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error::: " + e.getMessage());
        }
    }
}
