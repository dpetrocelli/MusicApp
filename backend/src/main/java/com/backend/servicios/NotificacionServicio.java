package com.backend.servicios;

import com.backend.entidades.Notificacion;
import com.backend.repositorios.NotificacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificacionServicio {

    @Autowired
    NotificacionRepositorio notificacionRepositorio;

    public void guardar(Notificacion notificacion){
        notificacionRepositorio.save(notificacion);
    }

}
