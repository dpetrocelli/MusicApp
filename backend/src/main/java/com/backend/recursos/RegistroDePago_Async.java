package com.backend.recursos;

import com.backend.dto.PagoMercadoPago;
import com.backend.entidades.MarketPlace;
import com.backend.entidades.Notificacion;
import com.backend.entidades.Pago;
import com.backend.servicios.PagoServicio;
import com.google.gson.Gson;
import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPIPN;
import com.mercadopago.resources.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistroDePago_Async extends Thread {

    private MarketPlace marketPlace;
    private Notificacion notificacion;
    private PagoServicio pagoServicio;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public RegistroDePago_Async(Notificacion notificacion, MarketPlace marketPlace, PagoServicio pagoServicio){
        this.notificacion = notificacion;
        this.marketPlace = marketPlace;
        this.pagoServicio = pagoServicio;
    }

    public synchronized void run() {

        Gson gson = new Gson();
        PagoMercadoPago pagoMercadoPagoDTO = gson.fromJson(notificacion.getPayload(), PagoMercadoPago.class);
        log.info("Iniciando registro de nuevo Pago: Notificacion payload:");

        if (pagoMercadoPagoDTO.getTopic().toString().equalsIgnoreCase("payments")) {
            log.info("Iniciando registro de nuevo Pago: Notificacion payload:"
                    + notificacion.getPayload());

            //luego de registrar la notificacion hay que buscar el recurso en MP por el ID y luego registrar el pago
            OrdenDeVentaMercadoPago ordenDeVentaMercadoPago = new OrdenDeVentaMercadoPago(marketPlace.getAppID(), marketPlace.getClientSecret());

            log.info("obteniendo pago de MP");
            Pago pago = ordenDeVentaMercadoPago.obtenerPago(pagoMercadoPagoDTO);

            pagoServicio.guardar(pago);
            log.info("pago guardado");

            log.info("Finalizando registro de nuevo Pago");
        } else
            log.info("Topic no es un pago, no se realizo registro de pago: "
                    + pagoMercadoPagoDTO.getTopic());
    }
}
