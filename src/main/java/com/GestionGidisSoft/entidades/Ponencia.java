package com.GestionGidisSoft.entidades;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "ponencias")
public class Ponencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ponencia")
    private Long idPonencia;

    private String titulo;

    private String autor;

    private Long idAutor;

    private String ambito;

    private String institucion;

    private String lugar;

    private String anio;

    private String mes;

}
