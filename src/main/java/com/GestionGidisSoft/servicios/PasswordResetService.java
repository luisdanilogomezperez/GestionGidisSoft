package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.entidades.PasswordResetToken;

public interface PasswordResetService {
    public PasswordResetToken guardar(PasswordResetToken passwordResetToken);

    public Object consultar(String codigo);

    public void eliminarCodigo(Long idCodigo);
}
