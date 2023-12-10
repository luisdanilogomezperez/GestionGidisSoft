package com.GestionGidisSoft.entidades;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "libro", uniqueConstraints = @UniqueConstraint(columnNames = "ISBN"))
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long libroId;

    private String titulo;

    private String anio;

    @Transient
    private List<Usuario> coautores;
    private String mes;

    public List<Usuario> getCoautores() {
        return coautores;
    }

    public void setCoautores(List<Usuario> coautores) {
        this.coautores = coautores;
    }

    private String medioDivulgacion;

    private String documentoEvidencia;
    private String disciplina;

    private String isbn;

    private String lugarPublicacion;

    private String editorial;

    private String tipoEditorial;

    private String certificadoCreditos;

    private String certificadoInstitucionAvala;

    public Libro() {
    }

    public Libro(String titulo, String anio, String mes, String medioDivulgacion, String documentoEvidencia,
                 String disciplina, String isbn, String lugarPublicacion, String editorial,
                 String tipoEditorial, String certificadoCreditos, String certificadoInstitucionAvala) {

        this.titulo = titulo;
        this.anio = anio;
        this.mes = mes;
        this.medioDivulgacion = medioDivulgacion;
        this.documentoEvidencia = documentoEvidencia;
        this.disciplina = disciplina;
        this.isbn = isbn;
        this.lugarPublicacion = lugarPublicacion;
        this.editorial = editorial;
        this.tipoEditorial = tipoEditorial;
        this.certificadoCreditos = certificadoCreditos;
        this.certificadoInstitucionAvala = certificadoInstitucionAvala;
    }

    public Long getLibroId() {
        return libroId;
    }

    public void setLibroId(Long libroId) {
        this.libroId = libroId;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLugarPublicacion() {
        return lugarPublicacion;
    }

    public void setLugarPublicacion(String lugarPublicacion) {
        this.lugarPublicacion = lugarPublicacion;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getTipoEditorial() {
        return tipoEditorial;
    }

    public void setTipoEditorial(String tipoEditorial) {
        this.tipoEditorial = tipoEditorial;
    }

    public String getCertificadoCreditos() {
        return certificadoCreditos;
    }

    public void setCertificadoCreditos(String certificadoCreditos) {
        this.certificadoCreditos = certificadoCreditos;
    }

    public String getCertificadoInstitucionAvala() {
        return certificadoInstitucionAvala;
    }

    public void setCertificadoInstitucionAvala(String certificadoInstitucionAvala) {
        this.certificadoInstitucionAvala = certificadoInstitucionAvala;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }
}
