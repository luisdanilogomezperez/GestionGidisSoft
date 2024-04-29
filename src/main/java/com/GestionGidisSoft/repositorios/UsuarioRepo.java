package com.GestionGidisSoft.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.GestionGidisSoft.entidades.Usuario;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    boolean existsUsuarioByEmailAndClave(String email, String clave);

    Usuario findByEmail(String email);

    @Query(value = "SELECT * FROM usuario"
            + " WHERE idusuario = :usuarioId", nativeQuery = true)
    Usuario findByusuarioId(Long usuarioId);

    @Query(value = "SELECT usuc.* FROM usuario usuc "
            + " INNER JOIN coautoreslibro coli ON (coli.idcoautor = usuc.idusuario) "
            + " WHERE coli.idlibro = :idLibro AND coli.idautor = :idAutor", nativeQuery = true)
    List<Usuario> listarCoautores(Long idLibro, Long idAutor);

    @Query(value = "SELECT usu.* FROM usuario usu "
            + " WHERE usu.idusuario != :idUsuarioAdmin ", nativeQuery = true)
    List<Usuario> listarUsuarios(Long idUsuarioAdmin);

    @Query(value = " SELECT u.* FROM usuario u WHERE u.idusuario NOT IN ( "
            + " SELECT u2.idusuario FROM usuario u2 "
            + "  INNER JOIN coautoreslibro coli ON (coli.idcoautor = u2.idusuario) " +
            " WHERE coli.idautor = :idAutor AND coli.idlibro = :idLibro) AND u.idusuario NOT IN ( " +
            " SELECT u2.idusuario FROM usuario u2 " +
            " INNER JOIN usuariolibro usua ON (usua.idusuario = u2.idusuario) " +
            "  WHERE usua.idusuario = :idAutor AND usua.idlibro = :idLibro)", nativeQuery = true)
    List<Usuario> listarNoCoautoresLibros(Long idLibro, Long idAutor);

    @Query(value = "SELECT usuc.* FROM usuario usuc "
            + " INNER JOIN coautorescapitulolibro cocap ON (cocap.idcoautor = usuc.idusuario) "
            + " WHERE cocap.idcapitulolibro = :idCapituloLibro AND cocap.idautor = :idAutor", nativeQuery = true)
    List<Usuario> listarCoautoresCapituloLibro(Long idCapituloLibro, Long idAutor);

    @Query(value = " SELECT u.* FROM usuario u WHERE u.idusuario NOT IN ( "
            + " SELECT u2.idusuario FROM usuario u2 "
            + " INNER JOIN coautorescapitulolibro cocap ON (cocap.idcoautor = u2.idusuario) " +
            " WHERE cocap.idautor = :idAutor AND cocap.idcapitulolibro = :idCapituloLibro) AND u.idusuario NOT IN ( " +
            " SELECT u2.idusuario FROM usuario u2 " +
            " INNER JOIN usuariocapitulolibro usua ON (usua.idautor = u2.idusuario) " +
            "  WHERE usua.idautor = :idAutor AND usua.idcapitulo = :idCapituloLibro)", nativeQuery = true)
    List<Usuario> listarNoCoautoresCapituloLibros(Long idCapituloLibro, Long idAutor);

    @Query(value = "SELECT COUNT(usua.idusuario) FROM usuario usua", nativeQuery = true)
    int existenUsuarios();

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO usuariorol (idusuario, idrol) VALUES (:idUsuario, :idRol)", nativeQuery = true)
    void actualizarTablaIntermedia(Long idUsuario, Long idRol);

    @Query(value = "SELECT usuc.* FROM usuario usuc "
            + " INNER JOIN coautoresarticulo coart ON (coart.idcoautor = usuc.idusuario) "
            + " WHERE coart.idarticulo = :idArticulo AND coart.idautor = :idAutor", nativeQuery = true)
    List<Usuario> listarCoautoresArticulos(Long idArticulo, Long idAutor);

    @Query(value = " SELECT u.* FROM usuario u WHERE u.idusuario NOT IN ( "
            + " SELECT u2.idusuario FROM usuario u2 "
            + " INNER JOIN coautoresarticulo coart ON (coart.idcoautor = u2.idusuario) " +
            " WHERE coart.idautor = :idAutor AND coart.idarticulo = :idArticulo) AND u.idusuario NOT IN ( " +
            " SELECT u2.idusuario FROM usuario u2 " +
            " INNER JOIN usuarioarticulo usua ON (usua.idautor = u2.idusuario) " +
            "  WHERE usua.idautor = :idAutor AND usua.idarticulo = :idArticulo)", nativeQuery = true)
    List<Usuario> listarNoCoautoresArticulos(Long idArticulo, Long idAutor);

    @Transactional
    @Modifying
    @Query(value = " UPDATE usuario " +
            " SET enable = :enable WHERE idusuario = :idUsuario", nativeQuery = true)
    int actualizarEstado(@Param("idUsuario") Long idUsuario, @Param("enable") boolean enable);

    @Query(value = " SELECT u.* FROM usuario u WHERE u.idusuario NOT IN ( "
            + " SELECT u2.idusuario FROM usuario u2 "
            + " INNER JOIN coautoresproyectoinvestigacion coaproin ON (coaproin.idcoautor = u2.idusuario) " +
            " WHERE coaproin.idautor = :idAutor AND coaproin.idproyectoinvestigacion = :idProyectoInvestigacion) AND u.idusuario NOT IN ( " +
            " SELECT u2.idusuario FROM usuario u2 " +
            " INNER JOIN usuarioproyectoinvestigacion usua ON (usua.idusuario = u2.idusuario) " +
            "  WHERE usua.idusuario = :idAutor AND usua.idproyectoinvestigacion = :idProyectoInvestigacion)", nativeQuery = true)
    List<Usuario> listarNoCoautoresProyectoInvestigacion(Long idProyectoInvestigacion, Long idAutor);

    @Query(value = "SELECT usuc.* FROM usuario usuc "
            + " INNER JOIN coautoresproyectoinvestigacion coaproin ON (coaproin.idcoautor = usuc.idusuario) "
            + " WHERE coaproin.idproyectoinvestigacion = :idProyectoInvestigacion AND coaproin.idautor = :idAutor", nativeQuery = true)
    List<Usuario> listarCoautoresProyectoInvestigacion(Long idProyectoInvestigacion, Long idAutor);

}