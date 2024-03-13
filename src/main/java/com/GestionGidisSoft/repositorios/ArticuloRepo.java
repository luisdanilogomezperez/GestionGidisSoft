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
/*
    @Transactional
    @Modifying
    @Query(value = " UPDATE articulo art " +
            " SET art.titulo = :titulo, art.anio = :anio, art.mes = :mes, lib.disciplina = :disciplina, " +
            " lib.editorial = :editorial, lib.isbn = :isbn, lib.lugarpublicacion = :lugarPublicacion, " +
            " lib.mediodivulgacion = :medioDivulgacion, lib.tipoeditorial = :tipoEditorial, " +
            " lib.documentoevidencia = :documentoEvidencia, lib.certificadocreditos = :certificadoCreditos, " +
            " lib.certificadoinstitucionavala = :certificadoInstitucionAvala " +
            " WHERE lib.idlibro = :idLibro", nativeQuery = true)
    int actualizararticulo(@Param("idLibro") Long idLibro, @Param("titulo") String titulo,
                         @Param("anio") String anio, @Param("mes") String mes,
                         @Param("disciplina") String disciplina, @Param("editorial") String editorial,
                         @Param("isbn") String isbn, @Param("lugarPublicacion") String lugarPublicacion,
                         @Param("medioDivulgacion") String medioDivulgacion, @Param("tipoEditorial") String tipoEditorial,
                         @Param("documentoEvidencia") String documentoEvidencia, @Param("certificadoCreditos") String certificadoCreditos,
                         @Param("certificadoInstitucionAvala") String certificadoInstitucionAvala);

*/
}

