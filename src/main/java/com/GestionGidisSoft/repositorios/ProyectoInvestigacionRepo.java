package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.ProyectoInvestigacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface ProyectoInvestigacionRepo extends JpaRepository<ProyectoInvestigacion, Long> {


    @Query(value = " SELECT proin.* FROM proyectoinvestigacion proin "
            + " INNER JOIN usuarioproyectoinvestigacion usuaproin on (usuaproin.idproyectoinvestigacion = proin.idproyectoinvestigacion) "
            + " WHERE usuaproin.idusuario = :idUsuario", nativeQuery = true)
    List<ProyectoInvestigacion> proyectoInvestigacionPorUsuario(Long idUsuario);


    @Transactional
    @Modifying
    @Query(value = " INSERT INTO coautoresproyectoinvestigacion (idautor, idcoautor, idproyectoinvestigacion) " +
            "VALUES (:idAutor, :idCoautor, :idProyectoInvestigacion )", nativeQuery = true)
    int insertarCoautor(Long idProyectoInvestigacion, Long idAutor, Long idCoautor);

    @Transactional
    @Modifying
    @Query(value = " DELETE FROM coautoresproyectoinvestigacion " +
            "  WHERE idproyectoinvestigacion = :idProyectoInvestigacion AND idusuario = :idAutor ", nativeQuery = true)
    int eliminarCoautoresProyectoInvestigacion(Long idProyectoInvestigacion, Long idAutor);
//
//    @Transactional
//    @Modifying
//    @Query(value = " INSERT INTO coautoresproyectoinvestigacion (idautor, idcoautor, idproyectoinvestigacion) " +
//            "VALUES (:idAutor, :idCoautor, :idProyectoInvestigacion )", nativeQuery = true)
//    int agregarProduccionAProyectoInvestigacion(Long idProyectoInvestigacion, Long idAutor, Long idProduccion);
//
//    @Transactional
//    @Modifying
//    @Query(value = " INSERT INTO coautoresproyectoinvestigacion (idautor, idcoautor, idproyectoinvestigacion) " +
//            "VALUES (:idAutor, :idCoautor, :idProyectoInvestigacion )", nativeQuery = true)
//    int eliminarProduccionAProyectoInvestigacion(Long idProyectoInvestigacion, Long idAutor, Long idProduccion);

    @Query(value = " SELECT * FROM proyectoinvestigacion "
            + " WHERE idproyectoinvestigacion = :idProyectoInvestigacion", nativeQuery = true)
    ProyectoInvestigacion buscarPorId(Long idProyectoInvestigacion);

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO usuarioproyectoinvestigacion (idusuario, idproyectoinvestigacion) VALUES (:idUsuario, :idProyectoInvestigacion )", nativeQuery = true)
    void insertarUsusarioProyectoInvestigacion(Long idProyectoInvestigacion, Long idUsuario);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM usuarioproyectoinvestigacion WHERE idproyectoinvestigacion = :idProyectoInvestigacion AND idusuario = :idAutor", nativeQuery = true)
    void eliminarRegistrosAutorProyectoInvestigacion(Long idProyectoInvestigacion, Long idAutor);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM coautoresproyectoinvestigacion "
            + " WHERE idproyectoinvestigacion = :idProyectoInvestigacion AND idcoautor = :idCoautor", nativeQuery = true)
    void eliminarCoautor(Long idProyectoInvestigacion, Long idCoautor);

    @Transactional
    @Modifying
    @Query(value = " UPDATE proyectoinvestigacion proin " +
            " SET proin.aniofin = :anioFin, " +
            " proin.anioinicio = :anioInicio, " +
            " proin.fuentefinanciacion = :fuenteFinanciacion, " +
            " proin.valorproyectosincontrapartida = :valorProyectoSinContrapartida, " +
            " proin.mesfin = :mesFin, " +
            " proin.mesinicio = :mesInicio, " +
            " proin.nombreinstitucion = :nombreInstitucion, " +
            " proin.resumen = :resumen, " +
            " proin.tipoproyecto = :tipoProyecto, " +
            " proin.titulo = :titulo, " +
            " proin.numerocodigoactoadministrativo = :numeroCodigoActoAdministrativo, " +
            " proin.valorcontrapartida = :valorContrapartida, " +
            " proin.fechaactoadministrativo = :fechaActoAdministrativo, " +
            " proin.essolidario = :solidario, " +
            " proin.esfinanciado = :financiado, " +
            " proin.tipoparticipacion = :tipoParticipacion, " +
            " proin.numeropaginas = :numeroPaginas, " +
            " proin.ambito = :ambito, " +
            " proin.codigoproyectoinstitucion = :codigoProyectoInstitucion, " +
            " proin.rolintitucion = :rolIntitucion, " +
            " proin.tipofinanciacion = :tipoFinanciacion " +
            " WHERE proin.idproyectoinvestigacion = :idProyectoInvestigacion", nativeQuery = true)
    int actualizarProyectoInvestigacion(@Param("idProyectoInvestigacion") Long idProyectoInvestigacion,
                                        @Param("anioFin") String anioFin, @Param("anioInicio") String anioInicio,
                                        @Param("fuenteFinanciacion") String fuenteFinanciacion,
                                        @Param("valorProyectoSinContrapartida") Long valorProyectoSinContrapartida,
                                        @Param("mesFin") String mesFin, @Param("mesInicio") String mesInicio,
                                        @Param("nombreInstitucion") String nombreInstitucion, @Param("resumen") String resumen,
                                        @Param("tipoProyecto") String tipoProyecto, @Param("titulo") String titulo,
                                        @Param("numeroCodigoActoAdministrativo") String numeroCodigoActoAdministrativo,
                                        @Param("valorContrapartida") Long valorContrapartida,
                                        @Param("fechaActoAdministrativo") Date fechaActoAdministrativo,
                                        @Param("solidario") String solidario, @Param("financiado") String financiado,
                                        @Param("numeroPaginas") String numeroPaginas, @Param("ambito") String ambito,
                                        @Param("codigoProyectoInstitucion") String codigoProyectoInstitucion,
                                        @Param("rolIntitucion") String rolIntitucion,
                                        @Param("tipoFinanciacion") String tipoFinanciacion,
                                        @Param("tipoParticipacion") String tipoParticipacion
                                        );

    @Transactional
    @Modifying
    @Query(value = " UPDATE proyectoinvestigacion proin  SET proin.produccionesvinculadas = :jsonProducciones" +
            " WHERE proin.idproyectoinvestigacion = :idProyectoInvestigacion", nativeQuery = true)
    int vincularProduccion(@Param("idProyectoInvestigacion") Long idProyectoInvestigacion, @Param("jsonProducciones") String jsonProducciones);

}
