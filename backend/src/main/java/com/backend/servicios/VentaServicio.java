package com.backend.servicios;

import com.backend.entidades.Venta;
import com.backend.repositorios.VentaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class VentaServicio {

    @Autowired
    VentaRepositorio ventaRepositorio;

    public void guardar(Venta venta) {
        ventaRepositorio.save(venta);
    }

    public Optional<Venta> obtenerPorId(long id) {
        return ventaRepositorio.findById(id);
    }
}
