package com.GestionGidisSoft.entidades;

import javax.persistence.*;

@Entity
@Table(name = "demastrabajos")
public class DemasTrabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDemasTrabajos;

    private String anio;

    private String mes;

    private String idioma;

    private String medioDivulgacion;

    private String lugarPublicacion;

    private String finalidad;

    public DemasTrabajo(){}

    public Long getIdDemasTrabajos() {
        return idDemasTrabajos;
    }

    public void setIdDemasTrabajos(Long idDemasTrabajos) {
        this.idDemasTrabajos = idDemasTrabajos;
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
}
