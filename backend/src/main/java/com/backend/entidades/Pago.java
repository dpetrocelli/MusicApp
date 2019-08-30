package com.backend.entidades;

import com.backend.recursos.TipoMovimiento;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    private Long id;

    /*
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "venta_id", referencedColumnName = "id")
    */
    //private Venta venta;
    private Long idVenta;

    private Date fechaPago;

    private TipoMovimiento tipoMovimiento;

    private String estado;

    private double importe;

    public Pago() {
    }

    /*
    public Pago(Venta venta, Long id, Date fechaPago, TipoMovimiento tipoMovimiento, String estado, double importe) {
        this.id = id;
        this.venta = venta;
        this.fechaPago = fechaPago;
        this.tipoMovimiento = tipoMovimiento;
        this.estado = estado;
        this.importe = importe;
    }
    */

    public Pago(Long idVenta, Long id, Date fechaPago, TipoMovimiento tipoMovimiento, String estado, double importe) {
        this.id = id;
        this.idVenta = idVenta;
        this.fechaPago = fechaPago;
        this.tipoMovimiento = tipoMovimiento;
        this.estado = estado;
        this.importe = importe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*
    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
    */

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public Long getIdVenta() {
        return idVenta;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
}
