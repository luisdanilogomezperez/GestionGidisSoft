package com.GestionGidisSoft.entidades;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Entity
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long usuarioId;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String clave;
    @Getter
    @Setter
    private String primerNombre;
    @Getter
    @Setter
    private String segundoNombre;
    @Getter
    @Setter
    private String primerApellido;
    @Getter
    @Setter
    private String segundoApellido;
    @Getter
    @Setter
    private String telefono;
    @Getter
    @Setter
    private String direccion;

    @Getter
    @Setter
    private String documento;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private boolean eable = true;
    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
    @JsonIgnore
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();
}
