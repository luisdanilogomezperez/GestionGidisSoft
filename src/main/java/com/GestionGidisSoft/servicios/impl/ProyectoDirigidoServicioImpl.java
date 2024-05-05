package com.GestionGidisSoft.servicios.impl;

import com.GestionGidisSoft.entidades.ProyectoDirigido;
import com.GestionGidisSoft.repositorios.ProyectoDirigidoRepo;
import com.GestionGidisSoft.servicios.ProyectoDirigidoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProyectoDirigidoServicioImpl implements ProyectoDirigidoServicio {

    @Autowired
    ProyectoDirigidoRepo proyectoDirigidoRepo;

    @Override
    public void crearProyectoDirigido(ProyectoDirigido proyectoDirigido) {
        proyectoDirigidoRepo.save(proyectoDirigido);
    }

    @Override
    public List<ProyectoDirigido> mostrarProyectoDirigidos() {
        return proyectoDirigidoRepo.findAll();
    }

    @Override
    public void editarProyectoDirigido(Long id, ProyectoDirigido proyectoDirigido) {
        proyectoDirigidoRepo.editarProyectoDirigido(id, proyectoDirigido);
    }

    @Override
    public void eliminarProyectoDirigido(Long id) {
        if(buscarProyectoDirigidoPorId(id) == null){
            throw new RuntimeException(String.format("El proyecto dirigido con el id %s no existe", id));
        }
        proyectoDirigidoRepo.deleteById(id);
    }

    @Override
    public List<ProyectoDirigido> findByUsuarioId(Long idAutor) {
        List<ProyectoDirigido> listaProyectoDirigidos = proyectoDirigidoRepo.listarProyectoDirigidosPorAutor(idAutor);
        return listaProyectoDirigidos;
    }

    @Override
    public void actualizarTablaIntermedia(Long idProyectoDirigido, Long idusuario) {
        proyectoDirigidoRepo.actualizarTablaIntermedia(idusuario, idProyectoDirigido);
    }

    @Override
    public void guardarProyectoDirigido(ProyectoDirigido proyectoDirigido) {
        proyectoDirigidoRepo.save(proyectoDirigido);
    }

    @Override
    public void eliminarRegistrosUsuarioProyectoDirigido(Long idProyectoDirigido, Long idUsuario) {
        proyectoDirigidoRepo.eliminarRegistrosUsuarioProyectoDirigido(idProyectoDirigido, idUsuario);
    }


    @Override
    public ProyectoDirigido buscarProyectoDirigidoPorId(Long id) {
        return proyectoDirigidoRepo.findById(id).orElse(null);
    }
}
