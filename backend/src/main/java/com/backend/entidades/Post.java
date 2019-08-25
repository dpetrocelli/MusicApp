package com.backend.entidades;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Elemento> elementos;

    @ManyToOne
    @JoinColumn(name = "fk_biografia", nullable = false, updatable = false)
    private Biografia biografia;


    public Post() {
    }

    public Post(String informacion, Date fechaCreacion, Date fechaEdicion, ArrayList<Elemento> elementos, Biografia biografia) {
        this.informacion = informacion;
        this.fechaCreacion = fechaCreacion;
        this.fechaEdicion = fechaEdicion;
        this.elementos = elementos;
        this.biografia = biografia;
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


    public void setElementos(ArrayList<Elemento> elementos) {
        this.elementos = elementos;
    }

    public Biografia getBiografia() {
        return biografia;
    }

    public void setBiografia(Biografia biografia) {
        this.biografia = biografia;
    }
}

