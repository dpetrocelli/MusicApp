package com.backend.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "biografiabanda")

public class BiografiaBanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "banda_id", referencedColumnName = "id")
    private Banda banda;

    private String videoBasico;
    private String listaYoutube;

    private String biografiaBasica;
    private String facebook;
    private String spotify;


    private String pathImagenPerfil;
    private String pathImagenPortada;
   public BiografiaBanda(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Banda getBanda() {
        return banda;
    }

    public void setBanda(Banda banda) {
        this.banda = banda;
    }

    public String getBiografiaBasica() {
        return biografiaBasica;
    }

    public void setBiografiaBasica(String biografiaBasica) {
        this.biografiaBasica = biografiaBasica;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getSpotify() {
        return spotify;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }

    public String getPathImagenPerfil() {
        return pathImagenPerfil;
    }

    public void setPathImagenPerfil(String pathImagenPerfil) {
        this.pathImagenPerfil = pathImagenPerfil;
    }

    public String getPathImagenPortada() {
        return pathImagenPortada;
    }

    public void setPathImagenPortada(String pathImagenPortada) {
        this.pathImagenPortada = pathImagenPortada;
    }

    public String getVideoBasico() {
        return videoBasico;
    }

    public void setVideoBasico(String videoBasico) {
        this.videoBasico = videoBasico;
    }

    public String getListaYoutube() {
        return listaYoutube;
    }

    public void setListaYoutube(String listaYoutube) {
        this.listaYoutube = listaYoutube;
    }
}
