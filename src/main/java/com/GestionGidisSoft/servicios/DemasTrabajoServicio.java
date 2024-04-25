package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.entidades.DemasTrabajo;

import java.util.List;

public interface DemasTrabajoServicio {
    void crearDemasTrabajo(DemasTrabajo demasTrabajo);

    List<DemasTrabajo> mostrarDemasTrabajos();

    void editarDemasTrabajo(Long id, DemasTrabajo demasTrabajo);

    void eliminarDemasTrabajo(Long id);

    DemasTrabajo buscarDemasTrabajoPorId(Long id);


    void eliminarRegistrosUsuarioDemasTrabajo(Long idDemasTrabajo, Long idUsuario);

    List<DemasTrabajo> findByUsuarioId(Long idAutor)  throws Exception;


    void actualizarTablaIntermedia(Long idDemasTrabajo, Long idusuario);

    void guardarDemasTrabajo(DemasTrabajo demasTrabajo);
}
