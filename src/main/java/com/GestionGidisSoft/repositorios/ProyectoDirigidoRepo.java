package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.ProyectoDirigido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoDirigidoRepo extends JpaRepository<ProyectoDirigido, Long> {
}
