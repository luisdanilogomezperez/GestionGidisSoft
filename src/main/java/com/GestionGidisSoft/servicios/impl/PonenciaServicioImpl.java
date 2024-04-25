package com.GestionGidisSoft.servicios.impl;

import com.GestionGidisSoft.entidades.Ponencia;
import com.GestionGidisSoft.repositorios.PonenciaRepo;
import com.GestionGidisSoft.servicios.PonenciaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PonenciaServicioImpl implements PonenciaServicio {


    private final PonenciaRepo ponenciaRepo;

    @Override
    public void crearPonencia(Ponencia ponencia) {
        ponenciaRepo.save(ponencia);
    }

    @Override
    public List<Ponencia> mostrarPonencias() {
        return ponenciaRepo.findAll();
    }

    @Override
    public void editarPonencia(Long id, Ponencia ponencia) {
        ponenciaRepo.editarPonencia(id, ponencia);
    }

    @Override
    public void eliminarPonencia(Long id) {
        if(buscarPonenciaPorId(id) == null){
            throw new RuntimeException(String.format("La ponencia con el id %s no existe", id));
        }
        ponenciaRepo.deleteById(id);
    }

    @Override
    public List<Ponencia> findByUsuarioId(Long idAutor) {
        List<Ponencia> listaPonencias = ponenciaRepo.listarPonenciasPorAutor(idAutor);
        return listaPonencias;
    }

    @Override
    public void actualizarTablaIntermedia(Long idPonencia, Long idusuario) {
        ponenciaRepo.actualizarTablaIntermedia(idusuario, idPonencia);
    }

    @Override
    public void guardarPonencia(Ponencia ponencia) {
        ponenciaRepo.save(ponencia);
    }

    @Override
    public void eliminarRegistrosUsuarioPonencia(Long idPonencia, Long idUsuario) {
        ponenciaRepo.eliminarRegistrosUsuarioPonencia(idPonencia, idUsuario);
    }


    @Override
    public Ponencia buscarPonenciaPorId(Long id) {
        return ponenciaRepo.findById(id).orElse(null);
    }
}
