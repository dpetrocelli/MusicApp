package com.backend;

import com.backend.entidades.Ganancia;
import com.backend.entidades.MarketPlace;
import com.backend.entidades.Rol;
import com.backend.repositorios.GananciaRepositorio;
import com.backend.repositorios.MarketPlaceRepositorio;
import com.backend.repositorios.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.boot.CommandLineRunner;
/**
 * Database initializer that populates the database with some
 * initial bank accounts using a JPA repository.
 *
 * This component is started only when app.db-init property is set to true
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Inicializador implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Inicializador.class);
    @Autowired
    private RolRepositorio rolRepositorio;
    @Autowired
    private MarketPlaceRepositorio marketPlaceRepositorio;

    @Autowired
    private GananciaRepositorio gananciaRepositorio;


    @Override
    public void run(String...args) {
        if (rolRepositorio.count()==0){
            log.info("HAY QUE METER DATOS");
            Rol rol = new Rol("comercio", "listadeaccesocomercio", "todas");
            rolRepositorio.save(rol);
            rol = new Rol("artista", "listadeaccesocomercio", "todas");
            rolRepositorio.save(rol);

        }else{
            log.info("YA HAY DATOS");
        }

        if (marketPlaceRepositorio.count()==0){
            // INSERTO LA CONF (Temporal) - MERCADOLIBRE
            MarketPlace mp = new MarketPlace("5801276810386206", "3KLt51OEOUQHfzG9p2QlKnrkRa1VZ98Y");
            marketPlaceRepositorio.save(mp);
        }

        if (gananciaRepositorio.count()==0){
            Ganancia ganancia = new Ganancia(2.69);
            gananciaRepositorio.save(ganancia);
        }

    }
}

