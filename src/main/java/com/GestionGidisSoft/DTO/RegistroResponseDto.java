package com.GestionGidisSoft.DTO;

public class RegistroResponseDto {

    private boolean guardo;

    public RegistroResponseDto(boolean guardo) {
        this.guardo = guardo;
    }

    public boolean isGuardo() {
        return guardo;
    }

    public void setGuardo(boolean guardo) {
        this.guardo = guardo;
    }
}
