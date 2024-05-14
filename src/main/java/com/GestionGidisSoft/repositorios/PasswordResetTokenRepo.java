package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.PasswordResetToken;
import com.GestionGidisSoft.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByCodigoVerificacion(String codigoVerificacion);
}
