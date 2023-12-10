package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.GestionGidisSoft.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RolRepo extends JpaRepository<Rol, Long> {
    @Query(value = "SELECT UROL.* " +
            " FROM ROL UROL " +
            " INNER JOIN USUARIO_ROL USUR ON (UROL.ID = USUR.ROL_ID)" +
            " INNER JOIN USUARIO USUA ON (USUA.ID = USUR.USUARIO_ID)" +
            " WHERE USUA.ID = :usuarioId", nativeQuery = true)
    public Rol findByusuarioId(Long usuarioId);
}
