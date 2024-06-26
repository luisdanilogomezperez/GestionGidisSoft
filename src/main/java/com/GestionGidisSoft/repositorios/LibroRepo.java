package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.Libro;
import com.GestionGidisSoft.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface LibroRepo extends JpaRepository<Libro, Long> {

    @Query(value = " SELECT lib.* FROM libro lib "
            + " INNER JOIN usuariolibro usualib on (usualib.idlibro = lib.idlibro) "
            + " WHERE usualib.idusuario = :idUsuario", nativeQuery = true)
    List<Libro> librosPorUsuario(Long idUsuario);


    @Transactional
    @Modifying
    @Query(value = " INSERT INTO coautoreslibro (idautor, idcoautor, idlibro) " +
            "VALUES (:idAutor, :idCoautor, :idLibro )", nativeQuery = true)
    int insertarCoautor(Long idLibro, Long idAutor, Long idCoautor);

    @Transactional
    @Modifying
    @Query(value = " DELETE FROM coautoreslibro " +
            "  WHERE idlibro = :idLibro AND idusuario = :idAutor ", nativeQuery = true)
    int eliminarCoautoresLibro(Long idLibro, Long idAutor);

    @Query(value = " SELECT * FROM libro "
            + " WHERE idlibro = :idLibro", nativeQuery = true)
    Libro buscarPorId(Long idLibro);

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO usuariolibro (idusuario, idlibro) VALUES (:usuarioId, :idLibro )", nativeQuery = true)
    void actualizarTablaIntermedia(Long idLibro, Long usuarioId);


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
            " lib.mediodivulgacion = :medioDivulgacion, lib.tipoeditorial = :tipoEditorial " +
            " WHERE lib.idlibro = :idLibro", nativeQuery = true)
    int actualizarLibro(@Param("idLibro") Long idLibro, @Param("titulo") String titulo,
                         @Param("anio") String anio, @Param("mes") String mes,
                         @Param("disciplina") String disciplina, @Param("editorial") String editorial,
                         @Param("isbn") String isbn, @Param("lugarPublicacion") String lugarPublicacion,
                         @Param("medioDivulgacion") String medioDivulgacion, @Param("tipoEditorial") String tipoEditorial);

    @Transactional
    @Modifying
    @Query(value = " UPDATE libro lib SET lib.documentoevidencia = :documentoEvidencia" +
            " lib.certificadoinstitucionavala = :certificadoInstitucionAvala, lib.certificadocreditos = :certificadoCreditos, " +
            " WHERE lib.idlibro = :idLibro", nativeQuery = true)
    int cargarDocumentos(@Param("idLibro") Long idLibro, @Param("documentoEvidencia") String documentoEvidencia,
                         @Param("certificadoInstitucionAvala") String certificadoInstitucionAvala,
                         @Param("certificadoCreditos") String certificadoCreditos);

    Optional<Libro> findByIsbn(String isbn);

}

