package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.CapituloLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapituloLibroRepo extends JpaRepository<CapituloLibro, Long> {
}
