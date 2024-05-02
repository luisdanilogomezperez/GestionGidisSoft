package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.entidades.ProyectoDirigido;

import java.util.List;

public interface ProyectoDirigidoServicio {
    void crearProyectoDirigido(ProyectoDirigido proyectoDirigido);

    List<ProyectoDirigido> mostrarProyectoDirigidos();

    void editarProyectoDirigido(Long id, ProyectoDirigido proyectoDirigido);

    void eliminarProyectoDirigido(Long id);

    ProyectoDirigido buscarProyectoDirigidoPorId(Long id);


    void eliminarRegistrosUsuarioProyectoDirigido(Long idProyectoDirigido, Long idUsuario);

    List<ProyectoDirigido> findByUsuarioId(Long idAutor)  throws Exception;


    void actualizarTablaIntermedia(Long idProyectoDirigido, Long idusuario);

    void guardarProyectoDirigido(ProyectoDirigido proyectoDirigido);
}
