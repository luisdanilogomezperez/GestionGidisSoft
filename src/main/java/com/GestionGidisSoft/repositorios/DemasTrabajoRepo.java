package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.CapituloLibro;
import com.GestionGidisSoft.entidades.DemasTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface DemasTrabajoRepo extends JpaRepository<DemasTrabajo, Long> {

    @Modifying
    @Query("UPDATE DemasTrabajo d SET " +
            "d.nombreProducto = COALESCE(:#{#demasTrabajo.nombreProducto}, d.nombreProducto), " +
            "d.medioDivulgacion = COALESCE(:#{#demasTrabajo.medioDivulgacion}, d.medioDivulgacion), " +
            "d.idioma = COALESCE(:#{#demasTrabajo.idioma}, d.idioma), " +
            "d.lugarPublicacion = COALESCE(:#{#demasTrabajo.lugarPublicacion}, d.lugarPublicacion), " +
            "d.finalidad = COALESCE(:#{#demasTrabajo.finalidad}, d.finalidad), " +
            "d.anio = COALESCE(:#{#demasTrabajo.anio}, d.anio), " +
            "d.mes = COALESCE(:#{#demasTrabajo.mes}, d.mes) " +
            "WHERE d.idDemasTrabajo = :#{#id}"
    )
    void editarDemasTrabajo(@Param("id") Long id,@Param("demasTrabajo") DemasTrabajo demasTrabajo);

    @Query(value = " SELECT dem.* FROM demastrabajos dem "
            + " INNER JOIN usuariodemastrabajos usudem on (usudem.idusuariodemastrabajos = dem.iddemastrabajo) "
            + " WHERE usudem.idusuario = :idUsuario", nativeQuery = true)
    List<DemasTrabajo> listarDemasTrabajosPorAutor(Long idUsuario);

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO usuariodemastrabajos (idusuario, iddemastrabajos) VALUES (:idUsuario, :idDemasTrabajo )", nativeQuery = true)
    void actualizarTablaIntermedia(Long idUsuario, Long idDemasTrabajo);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM usuariodemastrabajos WHERE iddemastrabajos = :idDemasTrabajo AND idusuario = :idUsuario", nativeQuery = true)
    void eliminarRegistrosUsuarioDemasTrabajo(Long idDemasTrabajo, Long idUsuario);

}
