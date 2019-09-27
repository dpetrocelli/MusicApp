package com.backend.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "NotificacionBandaUsuario")

public class NotificacionBandaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String nombreDestino;
    int tipoDestino;
    String tipoDeOperacion;
    // los tipos de operacion harcodeados por ahora son

    /*
    Usuario:    -> msg
                -> aceptar
                -> rechazar
                -> puedo_unirme_a_tu_banda

    Banda:      -> msg
                -> aceptar
                -> rechazar
                -> queres_sumarte_a_mi_banda

     */
    String mensaje;
    Date fechaNotificacion;
    String nombreOrigen;
    int tipoOrigen;
    /*
        tipo 1 -> artista
        tipo 2 -> banda
     */

    public NotificacionBandaUsuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public void setNombreDestino(String nombreDestino) {
        this.nombreDestino = nombreDestino;
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

    public String getNombreOrigen() {
        return nombreOrigen;
    }

    public void setNombreOrigen(String nombreOrigen) {
        this.nombreOrigen = nombreOrigen;
    }

    public int getTipoOrigen() {
        return tipoOrigen;
    }

    public void setTipoOrigen(int tipoOrigen) {
        this.tipoOrigen = tipoOrigen;
    }
}
