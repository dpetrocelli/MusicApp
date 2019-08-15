package com.backend.recursos;

import com.backend.entidades.Rol;

import java.util.List;

public class LoginDatos {

    Long idUsuario;
    String nombreUsuario;
    String tokenUsuario;
    String roles;

    public LoginDatos(Long idUsuario, String nombreUsuario, String tokenUsuario, String roles) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.tokenUsuario = tokenUsuario;
        this.roles = roles;
    }

    public LoginDatos() {
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTokenUsuario() {
        return tokenUsuario;
    }

    public void setTokenUsuario(String tokenUsuario) {
        this.tokenUsuario = tokenUsuario;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
