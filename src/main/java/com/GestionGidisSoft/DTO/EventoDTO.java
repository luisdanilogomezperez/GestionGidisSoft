package com.GestionGidisSoft.DTO;

public class EventoDTO {

        private Long idEventoDTO;

        private String nombreEvento;

        private String fechaInicio;

        private String fechaFin;

        private String participacion;

        private String descripcion;

        private String lugar;

        private String institucion;

        public Long getIdEventoDTO() {
                return idEventoDTO;
        }

        public void setIdEventoDTO(Long idEventoDTO) {
                this.idEventoDTO = idEventoDTO;
        }

        public String getNombreEvento() {
                return nombreEvento;
        }

        public void setNombreEvento(String nombreEvento) {
                this.nombreEvento = nombreEvento;
        }

        public String getFechaInicio() {
                return fechaInicio;
        }

        public void setFechaInicio(String fechaInicio) {
                this.fechaInicio = fechaInicio;
        }

        public String getFechaFin() {
                return fechaFin;
        }

        public void setFechaFin(String fechaFin) {
                this.fechaFin = fechaFin;
        }

        public String getParticipacion() {
                return participacion;
        }

        public void setParticipacion(String participacion) {
                this.participacion = participacion;
        }

        public String getDescripcion() {
                return descripcion;
        }

        public void setDescripcion(String descripcion) {
                this.descripcion = descripcion;
        }

        public String getLugar() {
                return lugar;
        }

        public void setLugar(String lugar) {
                this.lugar = lugar;
        }

        public String getInstitucion() {
                return institucion;
        }

        public void setInstitucion(String institucion) {
                this.institucion = institucion;
        }

        public EventoDTO(Long idEventoDTO, String nombreEvento, String fechaInicio, String fechaFin, String participacion, String descripcion, String lugar, String institucion) {
                this.idEventoDTO = idEventoDTO;
                this.nombreEvento = nombreEvento;
                this.fechaInicio = fechaInicio;
                this.fechaFin = fechaFin;
                this.participacion = participacion;
                this.descripcion = descripcion;
                this.lugar = lugar;
                this.institucion = institucion;
        }

        public EventoDTO() {
        }
}
