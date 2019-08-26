package com.backend.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "elemento")
public class Elemento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String tipoRecurso;
    // youtube, spotify, img
    String rutaAcceso;
    Date fechaCreacion;
    Date fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "fk_post", nullable = false, updatable = false)
    private Post post;

    public Elemento() {
    }

    public Elemento(String tipoRecurso, String rutaAcceso, Date fechaCreacion, Date fechaModificacion, Post post) {
        this.tipoRecurso = tipoRecurso;
        this.rutaAcceso = rutaAcceso;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public String getRutaAcceso() {
        return rutaAcceso;
    }

    public void setRutaAcceso(String rutaAcceso) {
        this.rutaAcceso = rutaAcceso;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}

