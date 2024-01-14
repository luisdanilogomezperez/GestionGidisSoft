package com.GestionGidisSoft.entidades;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "articulo")
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArticulo;

    @Column(name = "idlibro")
    private Long idLibro;
    @Transient
    private String tituloLibro;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "anio")
    private String anio;
    @Column(name = "mes")
    private String mes;
    @Column(name = "mediodivulgacion")
    private String medioDivulgacion;
    @Column(name = "documentoevidencia")
    private String documentoEvidencia;
    @Column(name = "paginainicial")
    private String paginaInicial;
    @Column(name = "pagininafinal")
    private String pagininaFinal;
    @Column(name = "numeropaginas")
    private String numeroPaginas;
    @Column(name = "serielibro")
    private String serieLibro;
    @Column(name = "edicion")
    private String edicion;
    @Column(name = "lugarpublicacion")
    private String lugarPublicacion;
    @Column(name = "disciplina")
    private String disciplina;
    @Transient
    List<Usuario> coautores;
    @Column(name = "areaconocimiento")
    private String areaConocimiento;
    @Column(name = "certificadoinstitucionavala")
    private String certificadoInstitucionAvala;
    @Column(name = "certificadocreditos")
    private String certificadoCreditos;
}
