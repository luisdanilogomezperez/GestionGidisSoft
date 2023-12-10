package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.Ponencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PonenciaRepo extends JpaRepository<Ponencia,Long> {
}
