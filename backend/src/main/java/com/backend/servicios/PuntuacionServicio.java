package com.backend.servicios;

import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.recursos.PublicacionMercadoPago;
import com.backend.repositorios.PromocionRepositorio;
import com.backend.repositorios.PuntuacionRepositorio;
import com.backend.singleton.ConfiguradorSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PuntuacionServicio {

    @Autowired
    PuntuacionRepositorio puntuacionRepositorio;
    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    ArtistaServicio artistaServicio;
    @Autowired
    ComercioServicio comercioServicio;
    @Autowired
    MarketPlaceServicio marketPlaceServicio;



    public boolean guardarPuntuacionArtista(LoginDatos ld, String art, String comentario, String puntuacion){
        Usuario usuarioPuntuador = this.usuarioServicio.obtener(ld.getIdUsuario());
        Artista artistaPuntuador = this.artistaServicio.obtenerPorUsuario(usuarioPuntuador);

        PuntuacionArtista puntuacionArtista = new PuntuacionArtista();
        Usuario usuario = this.usuarioServicio.obtenerPorNombre(art);
        Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);

        puntuacionArtista.setComentario(comentario);
        puntuacionArtista.setPuntuacion(Double.parseDouble(puntuacion));
        puntuacionArtista.setArtistaPuntuador(artistaPuntuador);
        puntuacionArtista.setArtistaPuntuado(artista);
        puntuacionArtista.setFechaPuntuacion(new Date());
        this.puntuacionRepositorio.save(puntuacionArtista);
        return true;
    }

    public List<PuntuacionArtista> obtenerPuntuacionArtista(LoginDatos ld) {
        Usuario usuarioPuntuado = this.usuarioServicio.obtener(ld.getIdUsuario());
        Artista artistaPuntuado = this.artistaServicio.obtenerPorUsuario(usuarioPuntuado);

        return this.puntuacionRepositorio.findAllByArtistaPuntuado(artistaPuntuado);


    }
}