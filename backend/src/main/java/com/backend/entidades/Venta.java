package com.backend.entidades;

import com.backend.recursos.TipoMovimiento;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "promocion_id", referencedColumnName = "id")
    private Promocion promocion;

    private Date fechaVenta;

    private String estado;

    private double gananciaMusicApp;

    private double gananciaComercio;

    public Venta() {
    }



}
