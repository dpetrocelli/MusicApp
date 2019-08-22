package com.backend.servicios;


import antlr.Token;
import com.backend.entidades.MarketPlace;
import com.backend.entidades.TokenUsuario;
import com.backend.entidades.Usuario;
import com.backend.repositorios.ComercioRepositorio;
import com.backend.repositorios.MarketPlaceRepositorio;
import com.backend.repositorios.TokenUsuarioRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class TokenUsuarioServicio {
    @Autowired
    TokenUsuarioRepositorio tokenUsuarioRepositorio;

    @Autowired
    MarketPlaceServicio marketPlaceServicio;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public TokenUsuario crearTokenUsuario(Usuario usuario) {
        log.info (" Crear Token Usuario "+usuario.getId());
        TokenUsuario tu = null;
        if (this.tokenUsuarioRepositorio.existsByIdUsuario(usuario.getId())) {
            //log.info(" EXISTE TOKEN ");
            tu = this.tokenUsuarioRepositorio.findByIdUsuario(usuario.getId()).get();
            //log.info(" ENTONCES LO RENUEVO");
            tu = this.renovarToken(tu);


        } else {
            //log.info(" NO EXISTE TOKEN LO CREO");
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Long time = new Date().getTime();
            passwordEncoder.encode(String.valueOf(time));

            time+=marketPlaceServicio.obtener().getTiempoSesion();
            tu = new TokenUsuario(usuario.getId(), passwordEncoder.encode(String.valueOf(new Date().getTime())), time);
        }
        return tu;
    }

    public TokenUsuario obtenerTokenUsuario (Long idUsuarioFE){
        return this.tokenUsuarioRepositorio.findByIdUsuario(idUsuarioFE).get();
    }
    public boolean validarToken(TokenUsuario tokenUsuario) {
        try {
            //log.info(" TOKEN USER (validar) "+tokenUsuario.getIdUsuario());
            //log.info(" ENTRE A VALIDAR ");
            Date date = new Date();
            long tiempoActual = date.getTime();
            long expiracionTokenUsuario = tokenUsuario.getExpiracion();
            //log.info(" VALIDO FECHAS");
            if (tiempoActual <= expiracionTokenUsuario + this.marketPlaceServicio.obtener().getTiempoSesion()) {
                tokenUsuario.setExpiracion(tiempoActual);
                //log.info(" RENOVE LAS FECHAS DEL TOKEN ");
                tokenUsuarioRepositorio.save(tokenUsuario);
                return true;
            }else{
                //log.info(" SE VENCIERON LAS CREDENCIALES ");
                return false;
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }

    }

    public TokenUsuario renovarToken (TokenUsuario tokenUsuario) {
        try{
            Date date = new Date();
            long tiempoActual = date.getTime();
            tokenUsuario.setExpiracion(tiempoActual);
            tokenUsuarioRepositorio.save(tokenUsuario);
            return tokenUsuario;
        }catch (Exception e){
            log.info(e.getMessage());
            return null;
        }

    }

    public boolean guardarTokenUsuario (TokenUsuario tokenUsuario){
        this.tokenUsuarioRepositorio.save(tokenUsuario);
        return true;
    }
}
