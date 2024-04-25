package com.GestionGidisSoft.DTO;

import lombok.*;

import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EventoDTO {

        private Long idEventoDTO;

        private String nombreEvento;

        private String fechaInicio;

        private String fechaFin;

        private String participacion;

        private String descripcion;

        private String lugar;

        private String institucion;


}
