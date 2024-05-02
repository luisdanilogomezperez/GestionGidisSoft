package com.GestionGidisSoft.entidades;

import javax.persistence.*;

@Entity
@Table(name = "proyectodirigido")
public class ProyectoDirigido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproyectodirigido")
    private Long idProyectoDirigido;

    private String nombre;

    @Column(name = "tipoproducto")
    private String tipoProducto;

    @Column(name = "anioinicio")
    private String anioInicio;

    @Column(name = "aniofin")
    private String anioFin;

    @Column(name = "mesinicio")
    private String mesInicio;

    @Column(name = "mesfin")
    private String mesFin;

    @Column(name = "tipoorientacion")
    private String tipoOrientacion;

    @Column(name = "numeropaginas")
    private String numeroPaginas;

    @Column(name = "nombreinstitucion")
    private String nombreInstitucion;

    @Column(name = "programaacademico")
    private String programaAcademico;


    @Column(name = "valoraciontesis")
    private String valoracionTesis;

    public ProyectoDirigido() {
    }

    public Long getIdProyectoDirigido() {
        return idProyectoDirigido;
    }

    public void setIdProyectoDirigido(Long idProyectoDirigido) {
        this.idProyectoDirigido = idProyectoDirigido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProdecto) {
        this.tipoProducto = tipoProdecto;
    }

    public String getAnioInicio() {
        return anioInicio;
    }

    public void setAnioInicio(String anioInicio) {
        this.anioInicio = anioInicio;
    }

    public String getAnioFin() {
        return anioFin;
    }

    public void setAnioFin(String anioFin) {
        this.anioFin = anioFin;
    }

    public String getMesInicio() {
        return mesInicio;
    }

    public void setMesInicio(String mesInicio) {
        this.mesInicio = mesInicio;
    }

    public String getMesFin() {
        return mesFin;
    }

    public void setMesFin(String mesFin) {
        this.mesFin = mesFin;
    }

    public String getTipoOrientacion() {
        return tipoOrientacion;
    }

    public void setTipoOrientacion(String tipoOrientacion) {
        this.tipoOrientacion = tipoOrientacion;
    }

    public String getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(String numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    public String getProgramaAcademico() {
        return programaAcademico;
    }

    public void setProgramaAcademico(String programaAcademico) {
        this.programaAcademico = programaAcademico;
    }

    public String getValoracionTesis() {
        return valoracionTesis;
    }

    public void setValoracionTesis(String valoracionTesis) {
        this.valoracionTesis = valoracionTesis;
    }
}
