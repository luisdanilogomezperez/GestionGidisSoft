package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.ProyectoDirigido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ProyectoDirigidoRepo extends JpaRepository<ProyectoDirigido, Long> {

    @Modifying
    @Query("UPDATE ProyectoDirigido p SET " +
            "p.nombre = COALESCE(:#{#proyectoDirigido.nombre}, p.nombre), " +
            "p.tipoProducto = COALESCE(:#{#proyectoDirigido.tipoProducto}, p.tipoProducto), " +
            "p.anioInicio = COALESCE(:#{#proyectoDirigido.anioInicio}, p.anioInicio), " +
            "p.anioFin = COALESCE(:#{#proyectoDirigido.anioFin}, p.anioFin), " +
            "p.mesInicio = COALESCE(:#{#proyectoDirigido.mesInicio}, p.mesInicio), " +
            "p.mesFin = COALESCE(:#{#proyectoDirigido.mesFin}, p.mesFin), " +
            "p.tipoOrientacion = COALESCE(:#{#proyectoDirigido.tipoOrientacion}, p.tipoOrientacion), " +
            "p.numeroPaginas = COALESCE(:#{#proyectoDirigido.numeroPaginas}, p.numeroPaginas), " +
            "p.nombreInstitucion = COALESCE(:#{#proyectoDirigido.nombreInstitucion}, p.nombreInstitucion), " +
            "p.programaAcademico = COALESCE(:#{#proyectoDirigido.programaAcademico}, p.programaAcademico), " +
            "p.valoracionTesis = COALESCE(:#{#proyectoDirigido.valoracionTesis}, p.valoracionTesis) " +
            "WHERE p.idProyectoDirigido = :#{#id}"
    )
    void editarProyectoDirigido(@Param("id") Long id,@Param("proyectoDirigido") ProyectoDirigido proyectoDirigido);

    @Query(value = " SELECT pro.* FROM proyectodirigido pro "
            + " INNER JOIN usuarioproyectodirigido usupro on (usupro.idusuarioproyectodirigido = pro.idproyectodirigido) "
            + " WHERE usupro.idusuario = :idUsuario", nativeQuery = true)
    List<ProyectoDirigido> listarProyectoDirigidosPorAutor(Long idUsuario);

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO usuarioproyectodirigido (idusuario, idproyectodirigido) VALUES (:idUsuario, :idProyectoDirigido )", nativeQuery = true)
    void actualizarTablaIntermedia(Long idUsuario, Long idProyectoDirigido);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM usuarioproyectodirigido WHERE idproyectodirigido = :idProyectoDirigido AND idusuario = :idUsuario", nativeQuery = true)
    void eliminarRegistrosUsuarioProyectoDirigido(Long idProyectoDirigido, Long idUsuario);
}
