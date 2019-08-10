package com.backend.entidades;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "promociones")
public class Promociones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descripcion;

    private String tipomoneda;

    private double valorpromocion;

    private double importe;

    private Date vigencia;

    private String init_point_mercadopago;

    public Promociones(){

    }
    public Promociones(String titulo, String descripcion, String tipomoneda, double valorpromocion, double importe, Date vigencia, String init_point_mercadopago) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipomoneda = tipomoneda;
        this.valorpromocion = valorpromocion;
        this.importe = importe;
        this.vigencia = vigencia;
        this.init_point_mercadopago = init_point_mercadopago;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipomoneda() {
        return tipomoneda;
    }

    public void setTipomoneda(String tipomoneda) {
        this.tipomoneda = tipomoneda;
    }

    public double getValorpromocion() {
        return valorpromocion;
    }

    public void setValorpromocion(double valorpromocion) {
        this.valorpromocion = valorpromocion;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Date getVigencia() {
        return vigencia;
    }

    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }

    public String getInit_point_mercadopago() {
        return init_point_mercadopago;
    }

    public void setInit_point_mercadopago(String init_point_mercadopago) {
        this.init_point_mercadopago = init_point_mercadopago;
    }
}
