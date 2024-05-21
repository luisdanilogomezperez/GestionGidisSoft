package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.Articulo;
import com.GestionGidisSoft.entidades.CapituloLibro;
import com.GestionGidisSoft.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CapituloLibroRepo extends JpaRepository<CapituloLibro, Long> {

    @Query(value = " SELECT cali.* FROM capitulolibro cali "
            + " INNER JOIN usuariocapitulolibro usucap on (usucap.idcapitulo = cali.idcapitulo) "
            + " WHERE usucap.idautor = :idUsuario", nativeQuery = true)
    List<CapituloLibro> capituloslibroPorUsuario(Long idUsuario);

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO usuariocapitulolibro (idautor, idcapitulo) VALUES (:idAutor, :idCapituloLibro )", nativeQuery = true)
    void agregarRegistroAutorCapitulo(Long idCapituloLibro, Long idAutor);


    @Query(value = " SELECT * FROM capitulolibro "
            + " WHERE idcapitulo = :idCapituloLibro", nativeQuery = true)
    CapituloLibro buscarPorId(Long idCapituloLibro);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM usuariocapitulolibro WHERE idautor = :idAutor AND idcapitulo = :idCapituloLibro", nativeQuery = true)
    void eliminarRegistrosAutorCapitulo(Long idCapituloLibro, Long idAutor);

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO coautorescapitulolibro (idautor, idcoautor, idcapitulolibro) " +
            "VALUES (:idAutor, :idCoautor, :idCapituloLibro )", nativeQuery = true)
    int insertarCoautorCapitulo(Long idAutor, Long idCoautor, Long idCapituloLibro);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM coautorescapitulolibro "
            + " WHERE idcapitulolibro = :idCapituloLibro AND idcoautor = :idCoautor", nativeQuery = true)
    void eliminarCoautorCapitulo(Long idCapituloLibro, Long idCoautor);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM coautorescapitulolibro "
            + " WHERE idcapitulolibro = :idCapituloLibro AND idautor = :idAutor", nativeQuery = true)
    int eliminarCoautoresLibro(Long idCapituloLibro, Long idAutor);

    @Transactional
    @Modifying
    @Query(value = " UPDATE capitulolibro cali " +
            " SET cali.titulo = :titulo, cali.anio = :anio, cali.mes = :mes, cali.disciplina = :disciplina, cali.edicion = :edicion, " +
            " cali.areaconocimiento = :areaConocimiento, cali.lugarpublicacion = :lugarPublicacion, cali.paginafinal = :paginaFinal, " +
            " cali.mediodivulgacion = :medioDivulgacion, cali.numeropaginas = :numeroPaginas, cali.paginainicial = :paginaInicial, " +
            " cali.idlibro = :idLibro, cali.serielibro = :serieLibro WHERE cali.idcapitulo = :idCapituloLibro", nativeQuery = true)
    int actualizarCapituloLibro(@Param("idCapituloLibro") Long idCapituloLibro, @Param("titulo") String titulo, @Param("serieLibro") String serieLibro,
                        @Param("anio") String anio, @Param("mes") String mes, @Param("paginaInicial") String paginaInicial,
                        @Param("paginaFinal") String paginaFinal, @Param("disciplina") String disciplina, @Param("edicion") String edicion,
                        @Param("areaConocimiento") String areaConocimiento, @Param("lugarPublicacion") String lugarPublicacion,
                        @Param("medioDivulgacion") String medioDivulgacion, @Param("numeroPaginas") String numeroPaginas,
                        @Param("idLibro") Long idLibro);

    @Transactional
    @Modifying
    @Query(value = " UPDATE capitulolibro cali SET cali.documentoevidencia = :documentoEvidencia" +
            " cali.certificadoinstitucionavala = :certificadoInstitucionAvala, cali.certificadocreditos = :certificadoCreditos, " +
            " WHERE cali.idcapitulo = :idCapituloLibro", nativeQuery = true)
    int cargarDocumentos(@Param("idCapituloLibro") Long idCapituloLibro, @Param("documentoEvidencia") String documentoEvidencia,
                         @Param("certificadoInstitucionAvala") String certificadoInstitucionAvala,
                         @Param("certificadoCreditos") String certificadoCreditos);

}
