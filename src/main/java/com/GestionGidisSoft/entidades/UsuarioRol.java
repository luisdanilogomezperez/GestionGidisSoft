package com.GestionGidisSoft.entidades;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
public class UsuarioRol {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long usuarioRolId;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @Getter
    @Setter
    @ManyToOne
    private Rol rol;
}
