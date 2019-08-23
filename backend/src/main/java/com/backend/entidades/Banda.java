package com.backend.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "banda")
public class Banda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artista_id", referencedColumnName = "id")
    private Artista artistaLider;

    @NotBlank
    @Column(unique = true)
    private String nombre;

    @NotBlank
    private String generoMusical;

   public Banda(){

    }

    public Banda(Artista artistaLider, @NotBlank String nombre, @NotBlank String generoMusical) {
        this.artistaLider = artistaLider;
        this.nombre = nombre;
        this.generoMusical = generoMusical;
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

    public String getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }
}
