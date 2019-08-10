package com.backend.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "ganancia")
public class Ganancia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double ganancia;


    public Ganancia() {
    }

    public Ganancia(Double ganancia) {
        this.ganancia = ganancia;
    }


}
