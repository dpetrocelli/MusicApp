package com.backend.servicios;

import com.backend.entidades.Pago;
import com.backend.repositorios.PagoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PagoServicio {

    @Autowired
    PagoRepositorio pagoRepositorio;

    public void guardar(Pago pago){
        pagoRepositorio.save(pago);
    }
}
