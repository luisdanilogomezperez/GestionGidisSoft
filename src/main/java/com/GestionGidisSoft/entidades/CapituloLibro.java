package com.GestionGidisSoft.entidades;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "capitulolibro")
public class CapituloLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcapitulo")
    private Long idCapitulo;

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
    @Column(name = "paginafinal")
    private String paginaFinal;
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

    public CapituloLibro() {
    }

    public String getPaginaFinal() {
        return paginaFinal;
    }

    public void setPaginaFinal(String paginaFinal) {
        this.paginaFinal = paginaFinal;
    }

    public Long getIdCapitulo() {
        return idCapitulo;
    }

    public void setIdCapitulo(Long idCapitulo) {
        this.idCapitulo = idCapitulo;
    }

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
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

    public String getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(String numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getSerieLibro() {
        return serieLibro;
    }

    public void setSerieLibro(String serieLibro) {
        this.serieLibro = serieLibro;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getLugarPublicacion() {
        return lugarPublicacion;
    }

    public void setLugarPublicacion(String lugarPublicacion) {
        this.lugarPublicacion = lugarPublicacion;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public List<Usuario> getCoautores() {
        return coautores;
    }

    public void setCoautores(List<Usuario> coautores) {
        this.coautores = coautores;
    }

    public String getAreaConocimiento() {
        return areaConocimiento;
    }

    public void setAreaConocimiento(String areaConocimiento) {
        this.areaConocimiento = areaConocimiento;
    }

    public String getCertificadoInstitucionAvala() {
        return certificadoInstitucionAvala;
    }

    public void setCertificadoInstitucionAvala(String certificadoInstitucionAvala) {
        this.certificadoInstitucionAvala = certificadoInstitucionAvala;
    }

    public String getCertificadoCreditos() {
        return certificadoCreditos;
    }

    public void setCertificadoCreditos(String certificadoCreditos) {
        this.certificadoCreditos = certificadoCreditos;
    }
}
