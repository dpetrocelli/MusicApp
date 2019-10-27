package com.backend.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "banda")
public class Banda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artista_id", referencedColumnName = "id")
    private Artista artistaLider;

    @NotBlank
    @Column(unique = true)
    private String nombre;

    @ManyToOne (optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_generoMusical", nullable = false, updatable = false)
    private GeneroMusical generoMusical;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "puntuacion")
    private List<PuntuacionArtista> puntuacionesRecibidas;


    public Banda() {

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Artista getArtistaLider() {
        return artistaLider;
    }

    public void setArtistaLider(Artista artistaLider) {
        this.artistaLider = artistaLider;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public GeneroMusical getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(GeneroMusical generoMusical) {
        this.generoMusical = generoMusical;
    }

    public List<PuntuacionArtista> getPuntuacionesRecibidas() {
        return puntuacionesRecibidas;
    }

    public void setPuntuacionesRecibidas(List<PuntuacionArtista> puntuacionesRecibidas) {
        this.puntuacionesRecibidas = puntuacionesRecibidas;
    }
}
