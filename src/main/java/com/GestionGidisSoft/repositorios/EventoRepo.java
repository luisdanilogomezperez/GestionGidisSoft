package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepo extends JpaRepository<Evento, Long> {
}
