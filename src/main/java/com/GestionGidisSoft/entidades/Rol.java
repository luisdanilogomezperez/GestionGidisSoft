package com.GestionGidisSoft.entidades;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    private long id;

    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rol")
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();

    public long getRolId() {
        return id;
    }

    public void setRolId(long rolId) {
        this.id = rolId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<UsuarioRol> getUsuarioRoles() {
        return usuarioRoles;
    }

    public void setUsuarioRoles(Set<UsuarioRol> usuarioRoles) {
        this.usuarioRoles = usuarioRoles;
    }
}
