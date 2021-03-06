package com.backend.servicios;

import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.recursos.PublicacionMercadoPago;
import com.backend.repositorios.PromocionRepositorio;
import com.backend.repositorios.PuntuacionRepositorio;
import com.backend.repositorios.PuntuacionRepositorioBanda;
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
    PuntuacionRepositorioBanda puntuacionRepositorioBanda;

    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    ArtistaServicio artistaServicio;
    @Autowired
    ComercioServicio comercioServicio;
    @Autowired
    MarketPlaceServicio marketPlaceServicio;
    @Autowired
    BandaServicio bandaServicio;


    public boolean validarSiYaPuntuee(LoginDatos ld, String art) {
        Usuario usuarioPuntuador = this.usuarioServicio.obtener(ld.getIdUsuario());
        Artista artistaPuntuador = this.artistaServicio.obtenerPorUsuario(usuarioPuntuador);
        Usuario usuario = this.usuarioServicio.obtenerPorNombre(art);
        Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
        boolean found = false;
        try {
            List<PuntuacionArtista> listaPuntos = this.puntuacionRepositorio.findAllByArtistaPuntuado(artista.getId());


            for (PuntuacionArtista puntuacion : listaPuntos) {
                if (puntuacion.getArtistaPuntuador() == artistaPuntuador.getId()) {
                    found = true;
                    break;
                }
            }
        } catch (Exception e) {

        }


        return found;
    }

    public boolean guardarPuntuacionArtista(LoginDatos ld, String art, String comentario, String puntuacion) {
        Usuario usuarioPuntuador = this.usuarioServicio.obtener(ld.getIdUsuario());
        Artista artistaPuntuador = this.artistaServicio.obtenerPorUsuario(usuarioPuntuador);

        PuntuacionArtista puntuacionArtista = new PuntuacionArtista();
        Usuario usuario = this.usuarioServicio.obtenerPorNombre(art);
        Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);

        puntuacionArtista.setComentario(comentario);
        puntuacionArtista.setPuntuacion(Double.parseDouble(puntuacion));
        puntuacionArtista.setArtistaPuntuador(artistaPuntuador.getId());
        puntuacionArtista.setArtistaPuntuado(artista.getId());
        puntuacionArtista.setFechaPuntuacion(new Date());
        this.puntuacionRepositorio.save(puntuacionArtista);
        return true;
    }

    public boolean guardarPuntuacionBanda(LoginDatos ld, String nombreBanda, String comentario, String puntuacion) {
        Usuario usuarioPuntuador = this.usuarioServicio.obtener(ld.getIdUsuario());
        Artista artistaPuntuador = this.artistaServicio.obtenerPorUsuario(usuarioPuntuador);

        PuntuacionBanda puntuacionBanda = new PuntuacionBanda();
        Banda banda = this.bandaServicio.obtenerBandaPorNombre(nombreBanda);

        puntuacionBanda.setComentario(comentario);
        puntuacionBanda.setPuntuacion(Double.parseDouble(puntuacion));
        puntuacionBanda.setArtistaPuntuador(artistaPuntuador.getId());
        puntuacionBanda.setBandaPuntuada(banda.getId());
        puntuacionBanda.setFechaPuntuacion(new Date());
        this.puntuacionRepositorioBanda.save(puntuacionBanda);
        return true;
    }

    public List<PuntuacionArtista> obtenerPuntuacionArtistaByLoginDatos(LoginDatos ld) {
        Usuario usuarioPuntuado = this.usuarioServicio.obtener(ld.getIdUsuario());
        Artista artistaPuntuado = this.artistaServicio.obtenerPorUsuario(usuarioPuntuado);

        return this.puntuacionRepositorio.findAllByArtistaPuntuado(artistaPuntuado.getId());


    }

    public List<PuntuacionBanda> obtenerPuntuacionBandaByLoginDatos(LoginDatos ld) {
        Usuario usuarioPuntuado = this.usuarioServicio.obtener(ld.getIdUsuario());
        Artista artistaPuntuado = this.artistaServicio.obtenerPorUsuario(usuarioPuntuado);
        Banda banda = this.bandaServicio.obtenerBandasDeLasQueSoyAdmin(artistaPuntuado).get(0);

        return this.puntuacionRepositorioBanda.findAllByBandaPuntuada(banda.getId());


    }

    public List<PuntuacionArtista> obtenerPuntuacionArtistaByArtista(Artista art) {
        return this.puntuacionRepositorio.findAllByArtistaPuntuado(art.getId());
    }

    public List<PuntuacionBanda> obtenerPuntuacionBandaByBanda(Banda banda) {
        return this.puntuacionRepositorioBanda.findAllByBandaPuntuada(banda.getId());

    }

    public boolean validarSiYaPuntueeBanda(LoginDatos ld, String nombreBanda) {
        Usuario usuarioPuntuador = this.usuarioServicio.obtener(ld.getIdUsuario());
        Artista artistaPuntuador = this.artistaServicio.obtenerPorUsuario(usuarioPuntuador);

        Banda banda = this.bandaServicio.obtenerBandaPorNombre(nombreBanda);

        boolean found = false;
        try {
            List<PuntuacionBanda> listaPuntos = this.puntuacionRepositorioBanda.findAllByBandaPuntuada(banda.getId());

            for (PuntuacionBanda puntuacion : listaPuntos) {
                if (puntuacion.getArtistaPuntuador() == artistaPuntuador.getId()) {
                    found = true;
                    break;
                }
            }
        } catch (Exception e) {

        }
        return found;
    }

}
