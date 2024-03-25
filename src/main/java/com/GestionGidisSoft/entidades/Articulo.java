package com.GestionGidisSoft.entidades;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "articulo")
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idarticulo")
    private Long idArticulo;

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
    @Column(name = "paginafinal")
    private String paginaFinal;
    @Column(name = "nombrerevista")
    private String nombreRevista;
    @Column(name = "tipoarticulo")
    private String tipoArticulo;
    @Column(name = "volumen")
    private String volumen;
    @Column(name = "fasciculorevista")
    private String fasciculoRevista;
    @Transient
    List<Usuario> coautores;
    @Transient
    String paisPublicacion;
    @Transient
    String ciudadPublicacion;
    @Column(name = "serierevista")
    private String serieRevista;
    @Column(name = "lugarpublicacion")
    private String lugarPublicacion;
    @Column(name = "identificadordigitaldoi")
    private String identificadorDigitalDoi;
    @Column(name = "idioma")
    private String idioma;
    @Column(name = "urlsitioweb")
    private String urlSitioWeb;

    public String getPaisPublicacion() {
        return paisPublicacion;
    }

    public void setPaisPublicacion(String paisPublicacion) {
        this.paisPublicacion = paisPublicacion;
    }

    public String getCiudadPublicacion() {
        return ciudadPublicacion;
    }

    public void setCiudadPublicacion(String ciudadPublicacion) {
        this.ciudadPublicacion = ciudadPublicacion;
    }

    public String getLugarPublicacion() {
        return lugarPublicacion;
    }

    public void setLugarPublicacion(String lugarPublicacion) {
        this.lugarPublicacion = lugarPublicacion;
    }

    public String getPaginaFinal() {
        return paginaFinal;
    }

    public void setPaginaFinal(String paginaFinal) {
        this.paginaFinal = paginaFinal;
    }

    public String getUrlSitioWeb() {
        return urlSitioWeb;
    }

    public void setUrlSitioWeb(String urlSitioWeb) {
        this.urlSitioWeb = urlSitioWeb;
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public String getMedioDivulgacion() {
        return medioDivulgacion;
    }

    public void setMedioDivulgacion(String medioDivulgacion) {
        this.medioDivulgacion = medioDivulgacion;
    }

    public String getDocumentoEvidencia() {
        return documentoEvidencia;
    }

    public void setDocumentoEvidencia(String documentoEvidencia) {
        this.documentoEvidencia = documentoEvidencia;
    }

    public String getPaginaInicial() {
        return paginaInicial;
    }

    public void setPaginaInicial(String paginaInicial) {
        this.paginaInicial = paginaInicial;
    }

    public String getNombreRevista() {
        return nombreRevista;
    }

    public void setNombreRevista(String nombreRevista) {
        this.nombreRevista = nombreRevista;
    }

    public String getTipoArticulo() {
        return tipoArticulo;
    }

    public void setTipoArticulo(String tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
    }

    public String getVolumen() {
        return volumen;
    }

    public void setVolumen(String volumen) {
        this.volumen = volumen;
    }

    public String getFasciculoRevista() {
        return fasciculoRevista;
    }

    public void setFasciculoRevista(String fasciculoRevista) {
        this.fasciculoRevista = fasciculoRevista;
    }

    public List<Usuario> getCoautores() {
        return coautores;
    }

    public void setCoautores(List<Usuario> coautores) {
        this.coautores = coautores;
    }

    public String getSerieRevista() {
        return serieRevista;
    }

    public void setSerieRevista(String serieRevista) {
        this.serieRevista = serieRevista;
    }

    public String getIdentificadorDigitalDoi() {
        return identificadorDigitalDoi;
    }

    public void setIdentificadorDigitalDoi(String identificadorDigitalDoi) {
        this.identificadorDigitalDoi = identificadorDigitalDoi;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
}
