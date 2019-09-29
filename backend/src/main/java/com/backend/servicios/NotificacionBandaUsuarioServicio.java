package com.backend.servicios;

import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.recursos.NotificacionesBandaUsuarioOrdenador;
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
import java.util.Collections;
import java.util.Date;

@Service
@Transactional
public class NotificacionBandaUsuarioServicio {

    @Autowired
    NotificacionBandaUsuarioRepositorio notificacionBandaUsuarioRepositorio;
    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    BandaServicio bandaServicio;
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
        // busco todas las mias
        try {
        ArrayList<NotificacionBandaUsuario> notificaciones = new ArrayList<NotificacionBandaUsuario>();
        //notificaciones = this.notificacionBandaUsuarioRepositorio.findAllByNombreDestinoAndTipoDestinoOrderByFechaNotificacionDesc(nombre,1).get();
        notificaciones = this.notificacionBandaUsuarioRepositorio.findAllByNombreDestinoOrderByFechaNotificacionDesc(nombre).get();
        // ahora voy a ver si soy admin de alguna banda y me las traigo también

            Usuario usuario = this.usuarioServicio.obtenerPorNombre(nombre);

            Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);

            boolean result = this.bandaServicio.verificarSiSoyAdminDeBanda(artista);

            if (result) {
                ArrayList<Banda> bandaLider = this.bandaServicio.obtenerBandasDeLasQueSoyAdmin(artista);
                if (bandaLider.size() > 0) {
                    for (Banda banda : bandaLider) {
                        notificaciones.addAll(this.notificacionBandaUsuarioRepositorio.findAllByNombreDestinoAndTipoDestinoOrderByFechaNotificacionDesc(banda.getNombre(), 2).get());

                    }
                    notificaciones.sort(new NotificacionesBandaUsuarioOrdenador());
                }
            }


           return notificaciones;

        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }



    }


    public void eliminar(String id) {
        this.notificacionBandaUsuarioRepositorio.deleteById(Long.parseLong(id));
    }

    public void actualizar(String id, String msg) {
        NotificacionBandaUsuario notificacionBandaUsuario = this.notificacionBandaUsuarioRepositorio.findById(Long.parseLong(id)).get();
        if (notificacionBandaUsuario.getTipoDeOperacion().equals("msg")){
            notificacionBandaUsuario.setEstado("msgModerado");
            this.notificacionBandaUsuarioRepositorio.save(notificacionBandaUsuario);

            NotificacionBandaUsuario notificacionRespuesta = new NotificacionBandaUsuario();
            notificacionRespuesta.setEstado("nueva");
            notificacionRespuesta.setFechaNotificacion(new Date());
            notificacionRespuesta.setNombreDestino(notificacionBandaUsuario.getNombreOrigen());
            notificacionRespuesta.setNombreOrigen(notificacionBandaUsuario.getNombreDestino());
            notificacionRespuesta.setTipoDeOperacion("msg");
            notificacionRespuesta.setTipoDestino(notificacionBandaUsuario.getTipoDestino());
            notificacionRespuesta.setTipoOrigen(notificacionBandaUsuario.getTipoOrigen());
            notificacionRespuesta.setMensaje(msg);
            this.notificacionBandaUsuarioRepositorio.save(notificacionRespuesta);

        }

    }


    public void descartar(String id, String nombreOrigen, String nombreDestino) {
        NotificacionBandaUsuario notificacionBandaUsuario = this.notificacionBandaUsuarioRepositorio.findById(Long.parseLong(id)).get();
        notificacionBandaUsuario.setEstado("procesado");
        this.notificacionBandaUsuarioRepositorio.save(notificacionBandaUsuario);
        NotificacionBandaUsuario avisoASolicitante = new NotificacionBandaUsuario();
        avisoASolicitante.setEstado("nueva");
        avisoASolicitante.setMensaje(" Se rechazó su solcitud de acceso a banda");
        avisoASolicitante.setTipoOrigen(2);
        avisoASolicitante.setTipoDestino(1);
        avisoASolicitante.setTipoDeOperacion("info");
        avisoASolicitante.setNombreOrigen(nombreDestino);
        avisoASolicitante.setNombreDestino(nombreOrigen);
        avisoASolicitante.setFechaNotificacion(new Date());
        this.notificacionBandaUsuarioRepositorio.save(avisoASolicitante);
    }
}
