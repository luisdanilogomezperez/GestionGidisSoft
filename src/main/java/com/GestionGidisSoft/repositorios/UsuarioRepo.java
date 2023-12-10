package com.GestionGidisSoft.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.GestionGidisSoft.entidades.Usuario;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Long> {

    public Usuario findByUsername(String username);

    boolean existsUsuarioByEmailAndClave(String email, String clave);

    public Usuario findByEmail(String enail);

    @Query(value = "SELECT * FROM usuario"
            + " WHERE id = :usuarioId", nativeQuery = true)
    public Usuario findByusuarioId(Long usuarioId);


}
