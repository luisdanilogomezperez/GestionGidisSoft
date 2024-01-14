package com.GestionGidisSoft.repositorios;

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
public interface ArticuloRepo extends JpaRepository<Libro, Long> {

    @Query(value = " SELECT lib.* FROM libro lib "
            + " INNER JOIN usuariolibro usualib on (usualib.idlibro = lib.idlibro) "
            + " WHERE usualib.idusuario = :idUsuario", nativeQuery = true)
    List<Libro> librosPorUsuario(Long idUsuario);


    @Transactional
    @Modifying
    @Query(value = " INSERT INTO coautoreslibro (idautor, idcoautor, idlibro) " +
            "VALUES (:idAutor, :idCoautor, :idLibro )", nativeQuery = true)
    int insertarCoautor(Long idLibro, Long idAutor, Long idCoautor);

    @Query(value = " SELECT * FROM libro "
            + " WHERE idlibro = :idLibro", nativeQuery = true)
    Libro buscarPorId(Long idLibro);

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO usuariolibro (idusuario, idlibro) VALUES (:usuarioId, :libroId )", nativeQuery = true)
    void actualizarTablaIntermedia(Long libroId, Long usuarioId);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM usuariolibro WHERE idlibro = :idLibro AND idusuario = :idAutor", nativeQuery = true)
    void eliminarRegistrosAutorLibro(Long idLibro, Long idAutor);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM coautoreslibro "
            + " WHERE idlibro = :idLibro AND idcoautor = :idCoautor", nativeQuery = true)
    void eliminarCoautor(Long idLibro, Long idCoautor);

    @Transactional
    @Modifying
    @Query(value = " UPDATE libro lib " +
            " SET lib.titulo = :titulo, lib.anio = :anio, lib.mes = :mes, lib.disciplina = :disciplina, " +
            " lib.editorial = :editorial, lib.isbn = :isbn, lib.lugarpublicacion = :lugarPublicacion, " +
            " lib.mediodivulgacion = :medioDivulgacion, lib.tipoeditorial = :tipoEditorial, " +
            " lib.documentoevidencia = :documentoEvidencia, lib.certificadocreditos = :certificadoCreditos, " +
            " lib.certificadoinstitucionavala = :certificadoInstitucionAvala " +
            " WHERE lib.idlibro = :idLibro", nativeQuery = true)
    int actualizarLibro(@Param("idLibro") Long idLibro, @Param("titulo") String titulo,
                         @Param("anio") String anio, @Param("mes") String mes,
                         @Param("disciplina") String disciplina, @Param("editorial") String editorial,
                         @Param("isbn") String isbn, @Param("lugarPublicacion") String lugarPublicacion,
                         @Param("medioDivulgacion") String medioDivulgacion, @Param("tipoEditorial") String tipoEditorial,
                         @Param("documentoEvidencia") String documentoEvidencia, @Param("certificadoCreditos") String certificadoCreditos,
                         @Param("certificadoInstitucionAvala") String certificadoInstitucionAvala);


    Optional<Libro> findByIsbn(String isbn);
}

