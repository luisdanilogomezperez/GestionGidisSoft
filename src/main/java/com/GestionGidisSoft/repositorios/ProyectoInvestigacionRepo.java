package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.ProyectoInvestigacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

//    @Transactional
//    @Modifying
//    @Query(value = " UPDATE libro lib " +
//            " SET lib.titulo = :titulo, lib.anio = :anio, lib.mes = :mes, lib.disciplina = :disciplina, " +
//            " lib.editorial = :editorial, lib.isbn = :isbn, lib.lugarpublicacion = :lugarPublicacion, " +
//            " lib.mediodivulgacion = :medioDivulgacion, lib.tipoeditorial = :tipoEditorial " +
//            " WHERE lib.idlibro = :idLibro", nativeQuery = true)
//    int actualizarProyectoInvestigacion(@Param("idProyectoInvestigacion") Long idProyectoInvestigacion, @Param("titulo") String titulo,
//                        @Param("anio") String anio, @Param("mes") String mes,
//                        @Param("disciplina") String disciplina, @Param("editorial") String editorial,
//                        @Param("isbn") String isbn, @Param("lugarPublicacion") String lugarPublicacion,
//                        @Param("medioDivulgacion") String medioDivulgacion, @Param("tipoEditorial") String tipoEditorial);

//    @Transactional
//    @Modifying
//    @Query(value = " UPDATE libro lib SET lib.documentoevidencia = :documentoEvidencia" +
//            " lib.certificadoinstitucionavala = :certificadoInstitucionAvala, lib.certificadocreditos = :certificadoCreditos, " +
//            " WHERE lib.idlibro = :idLibro", nativeQuery = true)
//    int cargarDocumentos(@Param("idLibro") Long idLibro, @Param("documentoEvidencia") String documentoEvidencia,
//                         @Param("certificadoInstitucionAvala") String certificadoInstitucionAvala,
//                         @Param("certificadoCreditos") String certificadoCreditos);
//
}
