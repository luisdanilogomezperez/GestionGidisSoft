package com.GestionGidisSoft.entidades;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "capitulolibro")
public class CapituloLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCapitulo;

    private Long idLibro;

    private String tituloLibro;

    private String titulo;

    private String anio;

    private String mes;

    private String medioDivulgacion;

    private String documentoEvidencia;

    private String paginaInicial;

    private String pagininaFinal;

    private String numeroPaginas;

    private String serieLibro;

    private String edicion;

    private String lugarPublicacion;

    private String disciplina;

    List<Usuario> coautores;

    private String areaConocimiento;

    private String certificadoInstitucionAvala;

    private String certificadoCreditos;

    public CapituloLibro() {
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

    public String getPagininaFinal() {
        return pagininaFinal;
    }

    public void setPagininaFinal(String pagininaFinal) {
        this.pagininaFinal = pagininaFinal;
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
