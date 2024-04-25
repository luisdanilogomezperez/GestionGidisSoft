package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface EventoRepo extends JpaRepository<Evento, Long> {

    @Modifying
    @Query("UPDATE Evento e SET " +
            "e.nombreEvento = COALESCE(:#{#evento.nombreEvento}, e.nombreEvento), " +
            "e.descripcion = COALESCE(:#{#evento.descripcion}, e.descripcion), " +
            "e.fechaInicio = COALESCE(:#{#evento.fechaInicio}, e.fechaInicio), " +
            "e.fechaFin = COALESCE(:#{#evento.fechaFin}, e.fechaFin), " +
            "e.participacion = COALESCE(:#{#evento.participacion}, e.participacion), " +
            "e.institucion = COALESCE(:#{#evento.institucion}, e.institucion), " +
            "e.lugar = COALESCE(:#{#evento.lugar}, e.lugar) " +
            "WHERE e.idEvento = :#{#id}"
    )
    void editarEvento(@Param("id") Long id,@Param("evento") Evento evento);

    @Query(value = " SELECT eve.* FROM evento eve "
            + " INNER JOIN usuarioevento usueve on (usueve.idevento = eve.idevento) "
            + " WHERE usueve.idusuario = :idUsuario", nativeQuery = true)
    List<Evento> listarEventosPorAutor(Long idUsuario);

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO usuarioevento (idusuario, idevento) VALUES (:idUsuario, :idEvento )", nativeQuery = true)
    void actualizarTablaIntermedia(Long idUsuario, Long idEvento);

    @Transactional
    @Modifying
        @Query(value = "DELETE FROM usuarioevento WHERE idevento = :idEvento AND idusuario = :idUsuario", nativeQuery = true)
    void eliminarRegistrosUsuarioEvento(Long idEvento, Long idUsuario);
}
