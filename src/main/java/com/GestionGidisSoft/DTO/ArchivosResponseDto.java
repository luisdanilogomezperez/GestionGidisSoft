package com.GestionGidisSoft.DTO;

public class ArchivosResponseDto {

    private String mensaje;

    public ArchivosResponseDto(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
