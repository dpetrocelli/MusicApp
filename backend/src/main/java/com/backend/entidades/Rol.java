package com.backend.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "rol")
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String nombre;

    @NotBlank
    private String descripcion;

    @NotBlank
    private String opcioneshabilitadas;

    public Rol (){

    }
    public Rol(@NotBlank String nombre, @NotBlank String descripcion, @NotBlank String opcioneshabilitadas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.opcioneshabilitadas = opcioneshabilitadas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOpcioneshabilitadas() {
        return opcioneshabilitadas;
    }

    public void setOpcioneshabilitadas(String opcioneshabilitadas) {
        this.opcioneshabilitadas = opcioneshabilitadas;
    }
}
