package com.backend.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String informacion;
    private Date fechaCreacion;
    private Date fechaEdicion;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Elemento> elementos;

    @ManyToOne (optional = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "fk_biografia", nullable = false, updatable = false)
    private Biografia biografia;

    private String username;
    private String nickname;


    public Post() {
    }

    public Post(String informacion, Date fechaCreacion, Date fechaEdicion, ArrayList<Elemento> elementos, Biografia biografia) {
        this.informacion = informacion;
        this.fechaCreacion = fechaCreacion;
        this.fechaEdicion = fechaEdicion;
        this.elementos = elementos;
        this.biografia = biografia;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public List<Elemento> getElementos() {
        return elementos;
    }


    public void setElementos(List<Elemento> elementos) {
        this.elementos = elementos;
    }

    public void addElemento(Elemento elemento) {
        this.elementos.add(elemento);
    }

    public Biografia getBiografia() {
        return biografia;
    }

    public void setBiografia(Biografia biografia) {
        this.biografia = biografia;
    }
}

