package com.GestionGidisSoft.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "passwordresettoken")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcodigo")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "codigoverificacion")
    private String codigoVerificacion;

    @Column(name = "fechaexpiracion")
    private LocalDateTime fechaexpiracion;

    @Column(name = "idusuario")
    private Long idUsuario;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigoVerificacion() {
        return codigoVerificacion;
    }

    public void setCodigoVerificacion(String codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }

    public LocalDateTime getFechaexpiracion() {
        return fechaexpiracion;
    }

    public void setFechaexpiracion(LocalDateTime fechaexpiracion) {
        this.fechaexpiracion = fechaexpiracion;
    }
}
