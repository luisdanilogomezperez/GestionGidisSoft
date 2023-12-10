package com.GestionGidisSoft.entidades;

import javax.persistence.*;

@Entity
@Table(name = "proyectos")
public class ProyectoInvestigacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProyectoInvestigacion;

    private String titulo;

    private String tipoProyecto;

    private String anioInicio;

    private String anioFin;

    private String mesInicio;

    private String mesFin;

    private String fuenteFinanciacion;

    private String ambito;

    private String resumen;

    private String nombreInstitucion;
    private Long valorEnPesos;
    public ProyectoInvestigacion() {
    }

    public Long getIdProyectoInvestigacion() {
        return idProyectoInvestigacion;
    }

    public void setIdProyectoInvestigacion(Long idProyectoInvestigacion) {
        this.idProyectoInvestigacion = idProyectoInvestigacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
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

    public String getFuenteFinanciacion() {
        return fuenteFinanciacion;
    }

    public void setFuenteFinanciacion(String fuenteFinanciacion) {
        this.fuenteFinanciacion = fuenteFinanciacion;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    public Long getValorEnPesos() {
        return valorEnPesos;
    }

    public void setValorEnPesos(Long valorEnPesos) {
        this.valorEnPesos = valorEnPesos;
    }
}
