package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.DemasTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemasTrabajoRepo extends JpaRepository<DemasTrabajo, Long> {
}
