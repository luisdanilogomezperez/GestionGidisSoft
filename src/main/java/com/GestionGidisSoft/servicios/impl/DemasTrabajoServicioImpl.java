package com.GestionGidisSoft.servicios.impl;

import com.GestionGidisSoft.entidades.DemasTrabajo;
import com.GestionGidisSoft.repositorios.DemasTrabajoRepo;
import com.GestionGidisSoft.servicios.DemasTrabajoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DemasTrabajoServicioImpl implements DemasTrabajoServicio {

    @Autowired
    DemasTrabajoRepo demasTrabajoRepo;

    @Override
    public void crearDemasTrabajo(DemasTrabajo demasTrabajo) {
        demasTrabajoRepo.save(demasTrabajo);
    }

    @Override
    public List<DemasTrabajo> mostrarDemasTrabajos() {
        return demasTrabajoRepo.findAll();
    }

    @Override
    public void editarDemasTrabajo(Long id, DemasTrabajo demasTrabajo) {
        demasTrabajoRepo.editarDemasTrabajo(id, demasTrabajo);
    }

    @Override
    public void eliminarDemasTrabajo(Long id) {
        if(buscarDemasTrabajoPorId(id) == null){
            throw new RuntimeException(String.format("La demasTrabajo con el id %s no existe", id));
        }
        demasTrabajoRepo.deleteById(id);
    }

    @Override
    public List<DemasTrabajo> findByUsuarioId(Long idAutor) {
        List<DemasTrabajo> listaDemasTrabajos = demasTrabajoRepo.listarDemasTrabajosPorAutor(idAutor);
        return listaDemasTrabajos;
    }

    @Override
    public void actualizarTablaIntermedia(Long idDemasTrabajo, Long idusuario) {
        demasTrabajoRepo.actualizarTablaIntermedia(idusuario, idDemasTrabajo);
    }

    @Override
    public void guardarDemasTrabajo(DemasTrabajo demasTrabajo) {
        demasTrabajoRepo.save(demasTrabajo);
    }

    @Override
    public void eliminarRegistrosUsuarioDemasTrabajo(Long idDemasTrabajo, Long idUsuario) {
        demasTrabajoRepo.eliminarRegistrosUsuarioDemasTrabajo(idDemasTrabajo, idUsuario);
    }


    @Override
    public DemasTrabajo buscarDemasTrabajoPorId(Long id) {
        return demasTrabajoRepo.findById(id).orElse(null);
    }
}
