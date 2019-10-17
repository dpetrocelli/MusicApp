package com.backend.servicios;

import com.backend.entidades.Instrumento;
import com.backend.entidades.Notificacion;
import com.backend.recursos.LoginDatos;
import com.backend.repositorios.NotificacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificacionServicio {

    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    NotificacionRepositorio notificacionRepositorio;

    public List<Notificacion> obtenerTodos() {
        List<Notificacion> lista = notificacionRepositorio.findAll();
        return lista;
    }

    public void guardar(Notificacion notificacion) {
        notificacionRepositorio.save(notificacion);
    }

    public boolean validarTokenUsuario(LoginDatos ld) {
        return this.usuarioServicio.validarTokenUsuario(ld);
    }
}
