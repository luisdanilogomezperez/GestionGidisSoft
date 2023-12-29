package com.GestionGidisSoft.entidades;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @Column(name = "idrol")
    private Long idrol;

    @Column(name = "nombre")
    private String nombre;

    public long getRolId() {
        return idrol;
    }

    public void setRolId(Long rolId) {
        this.idrol = rolId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
