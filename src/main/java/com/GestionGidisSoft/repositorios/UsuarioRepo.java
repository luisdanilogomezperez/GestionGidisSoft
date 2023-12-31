package com.GestionGidisSoft.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = " SELECT u.* FROM usuario u WHERE u.idusuario NOT IN ( "
            + " SELECT u2.idusuario FROM usuario u2 "
            + "  INNER JOIN coautoreslibro coli ON (coli.idcoautor = u2.idusuario) " +
            " WHERE coli.idautor = :idAutor AND coli.idlibro = :idLibro) AND u.idusuario NOT IN ( " +
            " SELECT u2.idusuario FROM usuario u2 " +
            " INNER JOIN usuariolibro usua ON (usua.idusuario = u2.idusuario) " +
            "  WHERE usua.idusuario = :idAutor AND usua.idlibro = :idLibro)", nativeQuery = true)
    List<Usuario> listarCoautoresLibros(Long idLibro, Long idAutor);

    @Query(value = "SELECT COUNT(usua.idusuario) FROM usuario usua", nativeQuery = true)
    int existenUsuarios();

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO usuariorol (idusuario, idrol) VALUES (:idUsuario, :idRol)", nativeQuery = true)
    void actualizarTablaIntermedia(Long idUsuario, Long idRol);

}