package com.backend.servicios;


import com.backend.entidades.MarketPlace;
import com.backend.repositorios.MarketPlaceRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class MarketPlaceServicio {

    @Autowired
    MarketPlaceRepositorio marketPlaceRepositorio;

    public MarketPlace obtener(){
        MarketPlace marketPlace = (MarketPlace) marketPlaceRepositorio.findAll();
        return marketPlace;
    }

    public void guardar(MarketPlace marketPlace){
        marketPlaceRepositorio.save(marketPlace);
    }

    public void borrar() {
        marketPlaceRepositorio.deleteAll();
    }
}
