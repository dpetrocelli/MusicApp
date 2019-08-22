package com.backend.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "marketplace")
public class MarketPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String appID;

    @Column(unique = true)
    private String clientSecret;

    private Double ganancia;

    private Long tiempoSesion;


    public MarketPlace() {
    }

    public MarketPlace(@NotBlank String appID, String clientSecret, Double ganancia, Long tiempoSesion) {
        this.appID = appID;
        this.clientSecret = clientSecret;
        this.ganancia = ganancia;
        this.tiempoSesion = tiempoSesion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Double getGanancia() {
        return ganancia;
    }

    public void setGanancia(Double ganancia) {
        this.ganancia = ganancia;
    }

    public Long getTiempoSesion() {
        return tiempoSesion;
    }

    public void setTiempoSesion(Long tiempoSesion) {
        this.tiempoSesion = tiempoSesion;
    }
}
