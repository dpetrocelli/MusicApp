package com.backend.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "biografia")

public class Biografia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artista_id", referencedColumnName = "id")
    private Artista artista;


    @Column(unique = true)
    private String biografiaBasica;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "biografia")
    private List<Post> posts;


   public Biografia(){

    }

    public Biografia(Artista artista, String biografiaBasica, ArrayList<Post> posts) {
        this.artista = artista;
        this.biografiaBasica = biografiaBasica;
        this.posts = posts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public String getBiografiaBasica() {
        return biografiaBasica;
    }

    public void setBiografiaBasica(String biografiaBasica) {
        this.biografiaBasica = biografiaBasica;
    }


    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
