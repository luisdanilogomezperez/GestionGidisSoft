package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.entidades.CapituloLibro;

import java.util.List;
import java.util.Optional;

public interface CapituloLibroServicio {

    public CapituloLibro guardarCapituloLibro(CapituloLibro capituloLibro) throws Exception;

    public void agregarRegistroAutorCapitulo(Long idCapituloLibro, Long idUsuario);

    public boolean insertarCoautor(Long idCapituloLibro, Long autorId, Long coautorId);

    public void eliminarRegistrosAutorCapitulo(Long idCapituloLibro, Long usuarioId);

    void eliminarRegistrosAutorCapituloLibro(Long idCapituloLibro, Long idAutor);

    void eliminarRelacionCoautores(Long idCapituloLibro, Long idcoautor);


    public String actualizarCapituloLibro(CapituloLibro libro) throws Exception;

    public List<CapituloLibro> buscarCapitulosLibros();

    public CapituloLibro buscarPorId(long idCapituloLibro);

    public void eliminar(long idCapituloLibro);

    public List<CapituloLibro> findByUsuarioId(Long id);
}
