package com.backend.entidades;

import javax.persistence.*;

@Entity
@Table(name = "puntuacionBanda")
public class PuntuacionBanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String comentario;

    double puntuacion;

    @ManyToOne (optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "fkArtistaPuntuador", nullable = true, updatable = true)
    private Artista artistaPuntuador;

    @ManyToOne (optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "fkBandaPuntuada", nullable = true, updatable = true)
    private Banda bandaPuntuado;



    public PuntuacionBanda() {
    }


    public PuntuacionBanda(String comentario, double puntuacion, Artista artistaPuntuador, Banda bandaPuntuado) {
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        this.artistaPuntuador = artistaPuntuador;
        this.bandaPuntuado = bandaPuntuado;
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

    public Banda getBandaPuntuado() {
        return bandaPuntuado;
    }

    public void setBandaPuntuado(Banda bandaPuntuado) {
        this.bandaPuntuado = bandaPuntuado;
    }
}

