package com.backend.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;

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


    private Long artistaPuntuador;
    private Long artistaPuntuado;

/*    @ManyToOne (optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "fkArtistaPuntuado", nullable = true, updatable = true)
    private Artista artistaPuntuado;

    @ManyToOne (optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "fkArtistaPuntuador", nullable = true, updatable = true)
    private Artista artistaPuntuador;
*/
    Date fechaPuntuacion;

    public PuntuacionArtista() {
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

    /*
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
    */

    public Long getArtistaPuntuador() {
        return artistaPuntuador;
    }

    public void setArtistaPuntuador(Long artistaPuntuador) {
        this.artistaPuntuador = artistaPuntuador;
    }

    public Long getArtistaPuntuado() {
        return artistaPuntuado;
    }

    public void setArtistaPuntuado(Long artistaPuntuado) {
        this.artistaPuntuado = artistaPuntuado;
    }
}

