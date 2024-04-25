package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.entidades.Ponencia;

import java.util.List;

public interface PonenciaServicio {
    void crearPonencia(Ponencia ponencia);

    List<Ponencia> mostrarPonencias();

    void editarPonencia(Long id, Ponencia ponencia);

    void eliminarPonencia(Long id);

    Ponencia buscarPonenciaPorId(Long id);


    void eliminarRegistrosUsuarioPonencia(Long idPonencia, Long idUsuario);

    List<Ponencia> findByUsuarioId(Long idAutor)  throws Exception;


    void actualizarTablaIntermedia(Long idPonencia, Long idusuario);

    void guardarPonencia(Ponencia ponencia);
}
