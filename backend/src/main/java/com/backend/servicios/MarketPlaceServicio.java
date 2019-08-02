package com.backend.servicios;


import com.backend.entidades.MarketPlace;
import com.backend.repositorios.MarketPlaceRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class MarketPlaceServicio {

    @Autowired
    MarketPlaceRepositorio marketPlaceRepositorio;

    public MarketPlace obtener(){
        List<MarketPlace> marketPlace = marketPlaceRepositorio.findAll();
        if (marketPlace.size()>0){
            return marketPlace.get(0);
        }else{
            return null;
        }
    }

    public void guardar(MarketPlace marketPlace){
        marketPlaceRepositorio.save(marketPlace);
    }

    public void borrar() {
        marketPlaceRepositorio.deleteAll();
    }
}
