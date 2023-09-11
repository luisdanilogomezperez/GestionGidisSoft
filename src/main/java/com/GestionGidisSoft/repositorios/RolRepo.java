package com.GestionGidisSoft.repositorios;

import org.springframework.stereotype.Repository;
import com.GestionGidisSoft.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RolRepo extends JpaRepository<Rol, Long> {

}
