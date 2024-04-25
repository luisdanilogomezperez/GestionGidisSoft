package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.entidades.Evento;

import java.util.List;

public interface EventoServicio {
    void crearEvento(Evento evento);

    List<Evento> mostrarEventos();

    void editarEvento(Long id, Evento evento);

    void eliminarEvento(Long id);

    Evento buscarEventoPorId(Long id);


    void eliminarRegistrosUsuarioEvento(Long idEvento, Long idUsuario);

    List<Evento> findByUsuarioId(Long idAutor)  throws Exception;


    void actualizarTablaIntermedia(Long idEvento, Long idusuario);

    void guardarEvento(Evento evento);
}
