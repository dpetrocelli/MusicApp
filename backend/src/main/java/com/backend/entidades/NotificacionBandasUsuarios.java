package com.backend.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "NotificacionBandasUsuarios")

public class NotificacionBandasUsuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    Long idDestino;
    int tipoDestino;
    String tipoDeOperacion;
    String mensaje;
    Date fechaNotificacion;
    Long idOrigen;
    int tipoOrigen;

    public NotificacionBandasUsuarios() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Long idDestino) {
        this.idDestino = idDestino;
    }

    public int getTipoDestino() {
        return tipoDestino;
    }

    public void setTipoDestino(int tipoDestino) {
        this.tipoDestino = tipoDestino;
    }

    public String getTipoDeOperacion() {
        return tipoDeOperacion;
    }

    public void setTipoDeOperacion(String tipoDeOperacion) {
        this.tipoDeOperacion = tipoDeOperacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public Long getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(Long idOrigen) {
        this.idOrigen = idOrigen;
    }

    public int getTipoOrigen() {
        return tipoOrigen;
    }

    public void setTipoOrigen(int tipoOrigen) {
        this.tipoOrigen = tipoOrigen;
    }
}
