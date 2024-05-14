package com.GestionGidisSoft.servicios.impl;

import com.GestionGidisSoft.entidades.PasswordResetToken;
import com.GestionGidisSoft.repositorios.PasswordResetTokenRepo;
import com.GestionGidisSoft.servicios.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    PasswordResetTokenRepo resetTokenRepo;


    @Override
    public PasswordResetToken guardar(PasswordResetToken passwordResetToken) {
        return resetTokenRepo.save(passwordResetToken);
    }

    @Override
    public Object consultar(String codigoVerificacion) {
        PasswordResetToken passwordResetToken = resetTokenRepo.findByCodigoVerificacion(codigoVerificacion);
        Object result = null;
        LocalDateTime now = LocalDateTime.now();
        if ((passwordResetToken == null) || (now.isAfter(passwordResetToken.getFechaexpiracion()))) {
            result = "El código ingresado no es valido";
        } else {
            result = passwordResetToken;
        }
        return result;
    }

    @Override
    public void eliminarCodigo(Long idCodigo) {

    }
}
