package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.Articulo;
import com.GestionGidisSoft.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticuloRepo extends JpaRepository<Articulo, Long> {

    @Query(value = " SELECT art.* FROM articulo art "
            + " INNER JOIN usuarioarticulo usuart on (usuart.idarticulo = art.idarticulo) "
            + " WHERE usuart.idautor = :idUsuario", nativeQuery = true)
    List<Articulo> listarArticulosPorAutor(Long idUsuario);

    @Query(value = " SELECT * FROM articulo "
            + " WHERE idarticulo = :idArticulo", nativeQuery = true)
    Articulo buscarPorId(Long idArticulo);

    @Query(value = " SELECT * FROM articulo "
            + " WHERE identificadordigitaldoi = :doi", nativeQuery = true)
    Optional<Articulo> buscarPorIdentificadorDoi(String  doi);

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO usuarioarticulo (idautor, idarticulo) VALUES (:idAutor, :idArticulo )", nativeQuery = true)
    void agregarAutorArticulo(Long idArticulo, Long idAutor);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM usuarioarticulo WHERE idarticulo = :idArticulo AND idautor = :idAutor", nativeQuery = true)
    void eliminarAutorArticulo(Long idArticulo, Long idAutor);

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO coautoresarticulo (idautor, idcoautor, idarticulo) " +
            "VALUES (:idAutor, :idCoautor, :idArticulo )", nativeQuery = true)
    int insertarCoautor(Long idArticulo, Long idAutor, Long idCoautor);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM coautoresarticulo "
            + " WHERE idarticulo = :idArticulo AND idcoautor = :idCoautor", nativeQuery = true)
    int eliminarCoautor(Long idArticulo, Long idCoautor);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM coautoresarticulo "
            + " WHERE idarticulo = :idArticulo AND idautor = :idAutor", nativeQuery = true)
    int eliminarCoautoresArticulo(Long idArticulo, Long idAutor);

    @Transactional
    @Modifying
    @Query(value = " UPDATE articulo art " +
            " SET art.titulo = :titulo, art.anio = :anio, art.mes = :mes, art.mediodivulgacion = :medioDivulgacion, " +
            " art.paginainicial = :paginaInicial, art.paginafinal = :paginaFinal, art.nombrerevista = :nombreRevista, " +
            " art.tipoarticulo = :tipoArticulo, art.volumen = :volumen, art.fasciculorevista = :fasciculoRevista, " +
            " art.serierevista = :serieRevista, art.lugarpublicacion = :lugarPublicacion, art.idioma = :idioma," +
            " art.identificadordigitaldoi = :identificadorDigitalDOI, art.urlsitioweb = :ulrSitioWeb" +
            " WHERE art.idarticulo = :idArticulo", nativeQuery = true)
    int actualizarArticulo(@Param("idArticulo") Long idArticulo, @Param("titulo") String titulo,
                           @Param("anio") String anio, @Param("mes") String mes, @Param("medioDivulgacion") String medioDivulgacion,
                           @Param("paginaInicial") String paginaInicial,
                           @Param("paginaFinal") String paginaFinal, @Param("nombreRevista") String nombreRevista,
                           @Param("tipoArticulo") String tipoArticulo, @Param("volumen") String volumen,
                           @Param("fasciculoRevista") String fasciculoRevista, @Param("serieRevista") String serieRevista,
                           @Param("lugarPublicacion") String lugarPublicacion, @Param("identificadorDigitalDOI") String identificadorDigitalDOI,
                           @Param("idioma") String idioma, @Param("ulrSitioWeb") String ulrSitioWeb);

    @Transactional
    @Modifying
    @Query(value = " UPDATE articulo art SET art.documentoevidencia = :documentoEvidencia" +
            " WHERE art.idarticulo = :idArticulo", nativeQuery = true)
    int cargarDocumento(@Param("idArticulo") Long idArticulo, @Param("documentoEvidencia") String documentoEvidencia);

}

