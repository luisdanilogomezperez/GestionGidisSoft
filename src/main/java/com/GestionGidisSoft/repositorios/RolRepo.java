package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.GestionGidisSoft.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RolRepo extends JpaRepository<Rol, Long> {
    @Query(value = "SELECT urol.* " +
            " FROM rol urol " +
            " INNER JOIN usuariorol usur ON (urol.idrol = usur.idrol)" +
            " INNER JOIN usuario usua ON (usua.idusuario = usur.idusuario)" +
            " WHERE usua.idusuario = :usuarioId", nativeQuery = true)
    Rol findByusuarioId(Long usuarioId);

    @Query(value = "SELECT COUNT(urol.idrol) FROM rol urol WHERE urol.idrol = :idrol", nativeQuery = true)
    int existenRoles(Long idrol);
}
