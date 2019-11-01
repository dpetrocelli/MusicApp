package com.backend.wrappers;

import com.backend.entidades.Artista;
import com.backend.recursos.LoginDatos;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ArtistaRequest {
    @JsonProperty("login")
    private LoginDatos loginDatos;
    @JsonProperty("artista")
    private Artista artista;

    public ArtistaRequest() {

    };

    public ArtistaRequest(Artista artista, LoginDatos loginDatos) {
        this.loginDatos = loginDatos;
        this.artista = artista;
    }


    public LoginDatos getLoginDatos() {
        return loginDatos;
    }

    public Artista getArtista() {
        return artista;
    }
}
