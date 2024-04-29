package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.entidades.ProyectoInvestigacion;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ProyectoInvestigacionServicio {

    ProyectoInvestigacion guardarProyectoInvestigacion(ProyectoInvestigacion proyectoInvestigacion) throws Exception;

    String actualizarProyectoInvestigacion(ProyectoInvestigacion proyectoInvestigacion) throws Exception;

    String cargarDocumento(ProyectoInvestigacion proyectoInvestigacion) throws Exception;

    void eliminarProyectoInvestigacion(Long idProyectoInvestigacion) throws Exception;

    ProyectoInvestigacion buscarPorId(Long idProyectoInvestigacion) throws Exception;

    List<ProyectoInvestigacion> listarProyectoInvestigacion()  throws Exception;

    List<ProyectoInvestigacion> findByUsuarioId(Long idAutor)  throws Exception;

    void agregarRegistroAutorProyectoInvestigacion(Long idProyectoInvestigacion, Long idAutor) throws Exception;

    void eliminarRegistroAutoresProyectoInvestigacion(Long idProyectoInvestigacion, Long idAutor) throws Exception;

    Boolean agregarRegistroCoautorProyectoInvestigacion(Long idProyectoInvestigacion, Long idAutor, Long idCoautor) throws Exception;

    void eliminarRegistroCoautoresProyectoInvestigacion(Long idProyectoInvestigacion, Long idAutor) throws Exception;

    void eliminarRegistroCoautorProyectoInvestigacion(Long idProyectoInvestigacion, Long idCoautor) throws Exception;

    String agregarProduccionAProyectoInvestigacion(Long idProyecto, Long idAutor, Long idProduccion);

    String eliminarProduccionAProyectoInvestigacion(Long idProyecto, Long idAutor, Long idProduccion);

}
