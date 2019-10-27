package com.backend.entidades;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "puntuacionBanda")
public class PuntuacionBanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String comentario;

    double puntuacion;

    private Long artistaPuntuador;
    private Long bandaPuntuada;

    Date fechaPuntuacion;



    public PuntuacionBanda() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Long getArtistaPuntuador() {
        return artistaPuntuador;
    }

    public void setArtistaPuntuador(Long artistaPuntuador) {
        this.artistaPuntuador = artistaPuntuador;
    }

    public Long getBandaPuntuada() {
        return bandaPuntuada;
    }

    public void setBandaPuntuada(Long bandaPuntuada) {
        this.bandaPuntuada = bandaPuntuada;
    }

    public Date getFechaPuntuacion() {
        return fechaPuntuacion;
    }

    public void setFechaPuntuacion(Date fechaPuntuacion) {
        this.fechaPuntuacion = fechaPuntuacion;
    }
}

