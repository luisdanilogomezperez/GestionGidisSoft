package com.GestionGidisSoft.entidades;

import javax.persistence.*;

@Entity
@Table(name = "proyectodirigido")
public class ProyectoDirigido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProyectoDirigido;

    private String nombre;

    private String tipoProdecto;

    private String anioInicio;

    private String anioFin;

    private String mesInicio;

    private String mesFin;

    private String tipoOrientacion;

    private String numeroPaginas;

    private String nombreInstitucion;

    private String programaAcademico;

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

    public String getTipoProdecto() {
        return tipoProdecto;
    }

    public void setTipoProdecto(String tipoProdecto) {
        this.tipoProdecto = tipoProdecto;
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
