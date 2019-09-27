package com.backend.servicios;

import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.recursos.PublicacionMercadoPago;
import com.backend.repositorios.NotificacionBandaUsuarioRepositorio;
import com.backend.repositorios.PromocionRepositorio;
import com.backend.singleton.ConfiguradorSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

@Service
@Transactional
public class NotificacionBandaUsuarioServicio {

    @Autowired
    NotificacionBandaUsuarioRepositorio notificacionBandaUsuarioRepositorio;
    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    ArtistaServicio artistaServicio;


    @Autowired
    MarketPlaceServicio marketPlaceServicio;


    public boolean validarTokenUsuario (LoginDatos ld ){
        return this.usuarioServicio.validarTokenUsuario(ld);
    }


    public Artista obtenerArtista(LoginDatos ld) {

        Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
        return this.artistaServicio.obtenerPorUsuario(u);

    }

    public Artista obtenerArtistaPorId(Long idOrigen){
        return this.artistaServicio.obtener(idOrigen);
    }

    public ArrayList<NotificacionBandaUsuario> obtenerNotificaciones (String nombre){
        Long l = new Long(1);
        return this.notificacionBandaUsuarioRepositorio.findAllByNombreDestinoAndTipoDestino(nombre,1).get();

    }


}
