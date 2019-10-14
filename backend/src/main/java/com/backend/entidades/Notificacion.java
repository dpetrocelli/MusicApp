package com.backend.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "notificacion")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String payload;
    private Boolean registrado = false;

    public Notificacion() {
    }

    public Notificacion(String payload) {
        this.payload = payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public void setRegistrado(Boolean registrado) {
        this.registrado = registrado;
    }

    public Boolean getRegistrado() {
        return registrado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
