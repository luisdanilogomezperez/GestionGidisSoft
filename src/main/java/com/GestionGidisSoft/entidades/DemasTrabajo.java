package com.GestionGidisSoft.entidades;

import javax.persistence.*;


@Entity
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

    public Long getIdDemasTrabajo() {
        return idDemasTrabajo;
    }

    public void setIdDemasTrabajo(Long idDemasTrabajo) {
        this.idDemasTrabajo = idDemasTrabajo;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getMedioDivulgacion() {
        return medioDivulgacion;
    }

    public void setMedioDivulgacion(String medioDivulgacion) {
        this.medioDivulgacion = medioDivulgacion;
    }

    public String getLugarPublicacion() {
        return lugarPublicacion;
    }

    public void setLugarPublicacion(String lugarPublicacion) {
        this.lugarPublicacion = lugarPublicacion;
    }

    public String getFinalidad() {
        return finalidad;
    }

    public void setFinalidad(String finalidad) {
        this.finalidad = finalidad;
    }

    public DemasTrabajo(String nombreProducto, String anio, String mes, String idioma, String medioDivulgacion, String lugarPublicacion, String finalidad) {
        this.nombreProducto = nombreProducto;
        this.anio = anio;
        this.mes = mes;
        this.idioma = idioma;
        this.medioDivulgacion = medioDivulgacion;
        this.lugarPublicacion = lugarPublicacion;
        this.finalidad = finalidad;
    }

    public DemasTrabajo(){
    }
}
