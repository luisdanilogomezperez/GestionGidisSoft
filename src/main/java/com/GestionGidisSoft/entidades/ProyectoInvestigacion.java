package com.GestionGidisSoft.entidades;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "proyectoinvestigacion")
public class ProyectoInvestigacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproyectoinvestigacion")
    private Long idProyectoInvestigacion;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "ambito")
    private String ambito;

    @Column(name = "tipoproyecto")
    private String tipoProyecto;

    @Column(name = "anioinicio")
    private String anioInicio;

    @Column(name = "aniofin")
    private String anioFin;

    @Column(name = "mesinicio")
    private String mesInicio;

    @Column(name = "mesfin")
    private String mesFin;

    @Column(name = "fuentefinanciacion")
    private String fuenteFinanciacion;

    @Column(name = "resumen")
    private String resumen;

    @Column(name = "esfinanciado")
    private String financiado;

    @Column(name = "tipoparticipacion")
    private String tipoParticipacion;

    @Column(name = "rolintitucion")
    private String rolIntitucion;
    @Column(name = "essolidario")
    private String solidario;

    @Column(name = "tipofinanciacion")
    private String tipoFinanciacion;

    @Column(name = "nombreinstitucion")
    private String nombreInstitucion;

    @Column(name = "numerocodigoactoadministrativo")
    private String numeroCodigoActoAdministrativo;

    @Column(name = "codigoproyectoinstitucion")
    private String codigoProyectoInstitucion;

    @Column(name = "valorcontrapartida")
    private Long valorContrapartida;

    @Column(name = "valorproyectosincontrapartida")
    private Long valorProyectoSinContrapartida;

    @Column(name = "fechaactoadministrativo")
    private Date fechaActoAdministrativo;

    @Column(name = "numeropaginas")
    private String numeroPaginas;

    @Column(name = "produccionesvinculadas")
    private String jsonProducciones;

    public String getJsonProducciones() {
        return jsonProducciones;
    }

    public void setJsonProducciones(String jsonProducciones) {
        this.jsonProducciones = jsonProducciones;
    }

    public String getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(String numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getTipoFinanciacion() {
        return tipoFinanciacion;
    }

    public void setTipoFinanciacion(String tipoFinanciacion) {
        this.tipoFinanciacion = tipoFinanciacion;
    }

    public String getCodigoProyectoInstitucion() {
        return codigoProyectoInstitucion;
    }

    public void setCodigoProyectoInstitucion(String codigoProyectoInstitucion) {
        this.codigoProyectoInstitucion = codigoProyectoInstitucion;
    }

    public String getRolIntitucion() {
        return rolIntitucion;
    }

    public void setRolIntitucion(String rolIntitucion) {
        this.rolIntitucion = rolIntitucion;
    }

    public String getNumeroCodigoActoAdministrativo() {
        return numeroCodigoActoAdministrativo;
    }

    public void setNumeroCodigoActoAdministrativo(String numeroCodigoActoAdministrativo) {
        this.numeroCodigoActoAdministrativo = numeroCodigoActoAdministrativo;
    }

    public Long getValorContrapartida() {
        return valorContrapartida;
    }

    public void setValorContrapartida(Long valorContrapartida) {
        this.valorContrapartida = valorContrapartida;
    }

    public Long getValorProyectoSinContrapartida() {
        return valorProyectoSinContrapartida;
    }

    public void setValorProyectoSinContrapartida(Long valorProyectoSinContrapartida) {
        this.valorProyectoSinContrapartida = valorProyectoSinContrapartida;
    }

    public Date getFechaActoAdministrativo() {
        return fechaActoAdministrativo;
    }

    public void setFechaActoAdministrativo(Date fechaActoAdministrativo) {
        this.fechaActoAdministrativo = fechaActoAdministrativo;
    }

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

    public String getFinanciado() {
        return financiado;
    }

    public void setFinanciado(String financiado) {
        this.financiado = financiado;
    }

    public String getTipoParticipacion() {
        return tipoParticipacion;
    }

    public void setTipoParticipacion(String tipoParticipacion) {
        this.tipoParticipacion = tipoParticipacion;
    }

    public String getSolidario() {
        return solidario;
    }

    public void setSolidario(String solidario) {
        this.solidario = solidario;
    }
}
