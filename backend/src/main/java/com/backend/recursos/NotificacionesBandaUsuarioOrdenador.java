package com.backend.recursos;

import com.backend.entidades.NotificacionBandaUsuario;

import java.util.Comparator;
public class NotificacionesBandaUsuarioOrdenador implements Comparator<NotificacionBandaUsuario>{
    @Override
    public int compare(NotificacionBandaUsuario o1, NotificacionBandaUsuario o2) {
        return o2.getFechaNotificacion().compareTo(o1.getFechaNotificacion());
    }
}


