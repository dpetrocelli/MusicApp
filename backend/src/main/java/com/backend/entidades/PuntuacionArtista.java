package com.backend.entidades;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "puntuacionArtista")
public class PuntuacionArtista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String comentario;

    double puntuacion;

    @ManyToOne (optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "fkArtistaPuntuador", nullable = true, updatable = true)
    private Artista artistaPuntuador;

    @ManyToOne (optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "fkArtistaPuntuado", nullable = true, updatable = true)
    private Artista artistaPuntuado;

    Date fechaPuntuacion;

    public PuntuacionArtista() {
    }

    public PuntuacionArtista(String comentario, double puntuacion, Artista artistaPuntuador, Artista artistaPuntuado) {
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        this.artistaPuntuador = artistaPuntuador;
        this.artistaPuntuado = artistaPuntuado;
    }

    public Date getFechaPuntuacion() {
        return fechaPuntuacion;
    }

    public void setFechaPuntuacion(Date fechaPuntuacion) {
        this.fechaPuntuacion = fechaPuntuacion;
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

    public Artista getArtistaPuntuador() {
        return artistaPuntuador;
    }

    public void setArtistaPuntuador(Artista artistaPuntuador) {
        this.artistaPuntuador = artistaPuntuador;
    }

    public Artista getArtistaPuntuado() {
        return artistaPuntuado;
    }

    public void setArtistaPuntuado(Artista artistaPuntuado) {
        this.artistaPuntuado = artistaPuntuado;
    }
}

