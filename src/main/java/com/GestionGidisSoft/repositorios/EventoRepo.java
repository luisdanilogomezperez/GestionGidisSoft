package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepo extends JpaRepository<Evento, Long> {
}
