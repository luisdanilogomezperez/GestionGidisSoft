package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.ProyectoInvestigacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoInvestigacionRepo extends JpaRepository<ProyectoInvestigacion, Long> {
}
