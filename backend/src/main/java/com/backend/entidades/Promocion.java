package com.backend.entidades;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Entity
@Table(name = "promocion")
public class Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long comercio;

    private String titulo;

    private String descripcion;

    private String tipomoneda;

    private double valorpromocion;

    private double importe;

    private Date vigencia;

    private String init_point_mercadopago;

    private String idPublicacionMP;

    public Promocion() {
    }

    public Promocion(Long comercio, String titulo, String descripcion, String tipomoneda, double valorpromocion, double importe, Date vigencia, String init_point_mercadopago, String idPublicacionMP) {
        this.comercio = comercio;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipomoneda = tipomoneda;
        this.valorpromocion = valorpromocion;
        this.importe = importe;
        this.vigencia = vigencia;
        this.init_point_mercadopago = init_point_mercadopago;
        this.idPublicacionMP = idPublicacionMP;
    }

    public String getIdPublicacionMP() {
        return idPublicacionMP;
    }

    public void setIdPublicacionMP(String idPublicacionMP) {
        this.idPublicacionMP = idPublicacionMP;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_comercio() {
        return comercio;
    }

    public void setId_comercio(Long id_comercio) {
        this.comercio = id_comercio;
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
