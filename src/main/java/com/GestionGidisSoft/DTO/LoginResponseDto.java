package com.GestionGidisSoft.DTO;

import lombok.*;
@Builder
public class LoginResponseDto {

    private boolean accedio;

    public LoginResponseDto(boolean accedio) {
        this.accedio = accedio;
    }

    public boolean isAccedio() {
        return accedio;
    }

    public void setAccedio(boolean accedio) {
        this.accedio = accedio;
    }
}