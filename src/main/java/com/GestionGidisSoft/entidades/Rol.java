package com.GestionGidisSoft.entidades;

import java.util.*;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @Getter
    @Setter
    private long rolId;
    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rol")
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();
}
