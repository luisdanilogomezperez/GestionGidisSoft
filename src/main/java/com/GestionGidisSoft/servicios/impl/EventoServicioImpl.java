package com.GestionGidisSoft.servicios.impl;

import com.GestionGidisSoft.entidades.Evento;
import com.GestionGidisSoft.repositorios.EventoRepo;
import com.GestionGidisSoft.servicios.EventoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventoServicioImpl implements EventoServicio {


    private final EventoRepo eventoRepo;

    @Override
    public void crearEvento(Evento evento) {
        eventoRepo.save(evento);
    }

    @Override
    public List<Evento> mostrarEventos() {
        return eventoRepo.findAll();
    }

    @Override
    public void editarEvento(Long id, Evento evento) {
        eventoRepo.editarEvento(id, evento);
    }

    @Override
    public void eliminarEvento(Long id) {
        if(buscarEventoPorId(id) == null){
            throw new RuntimeException(String.format("El evento con el id %s no existe", id));
        }
        eventoRepo.deleteById(id);
    }

    @Override
    public List<Evento> findByUsuarioId(Long idAutor) {
        List<Evento> listaEventos = eventoRepo.listarEventosPorAutor(idAutor);
        return listaEventos;
    }

    @Override
    public void actualizarTablaIntermedia(Long idEvento, Long idusuario) {
        eventoRepo.actualizarTablaIntermedia(idusuario, idEvento);
    }

    @Override
    public void guardarEvento(Evento evento) {
        eventoRepo.save(evento);
    }

    @Override
    public void eliminarRegistrosUsuarioEvento(Long idEvento, Long idUsuario) {
        eventoRepo.eliminarRegistrosUsuarioEvento(idEvento, idUsuario);
    }


    @Override
    public Evento buscarEventoPorId(Long id) {
        return eventoRepo.findById(id).orElse(null);
    }
}
