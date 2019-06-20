package com.backend.entidad;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "instrumento")
public class Instrumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String nombreInstrumento;

    @NotNull
    private String tipoInstrumento;

    public Instrumento() {
    }

    public Instrumento(String nombreInstrumento, String tipoInstrumento) {
        this.nombreInstrumento = nombreInstrumento;
        this.tipoInstrumento = tipoInstrumento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreInstrumento() {
        return nombreInstrumento;
    }

    public void setNombreProducto(String nombreInstrumento) {
        this.nombreInstrumento = nombreInstrumento;
    }

    public String getTipoInstrumento() {
        return tipoInstrumento;
    }

    public void setTipoInstrumento(String tipoInstrumento) {
        this.tipoInstrumento = tipoInstrumento;
    }
}
