package com.backend.entidades;

import com.backend.recursos.TipoMovimiento;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "venta")
public class Venta {

    @Id
    private Long id;

    /*
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
*/
/*
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "promocion_id", referencedColumnName = "id")
    private Promocion promocion;
*/

    private Date fechaVenta;

    private String estado;

    private double gananciaMusicApp;

    private double gananciaComercio;

    public Venta() {

    }

    public Venta(Long id, Date fechaVenta, String estado, Double gananciaComercio, Double gananciaMusicApp) {
        this.id = id;
        this.fechaVenta = fechaVenta;
        this.estado = estado;
        this.gananciaComercio = gananciaComercio;
        this.gananciaMusicApp = gananciaMusicApp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    /*
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
    }

    public Promocion getPromocion() {
        return promocion;
    }
    */

    public void setGananciaComercio(double gananciaComercio) {
        this.gananciaComercio = gananciaComercio;
    }

    public double getGananciaComercio() {
        return gananciaComercio;
    }

    public void setGananciaMusicApp(double gananciaMusicApp) {
        this.gananciaMusicApp = gananciaMusicApp;
    }

    public double getGananciaMusicApp() {
        return gananciaMusicApp;
    }
}
