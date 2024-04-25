package com.GestionGidisSoft.entidades;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "demastrabajos")
public class DemasTrabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddemastrabajo")
    private Long idDemasTrabajo;

    @Column(name = "nombre_producto")
    private String nombreProducto;

    private String anio;

    private String mes;

    private String idioma;

    private String medioDivulgacion;

    private String lugarPublicacion;

    private String finalidad;


}
