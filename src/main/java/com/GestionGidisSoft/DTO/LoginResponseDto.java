package com.GestionGidisSoft.DTO;

public class LoginResponseDto {

    private boolean accedio = false;

    private boolean salio = false;

    public LoginResponseDto() {
    }

    public void setAccedio(boolean accedio) {
        this.accedio = accedio;
    }
    public boolean getAccedio() {
        return accedio;
    }

    public boolean getSalio() {
        return salio;
    }

    public void setSalio(boolean salio) {
        this.salio = salio;
    }
}