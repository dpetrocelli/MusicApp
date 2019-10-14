package com.backend.recursos;

import com.backend.entidades.Notificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistroDePago_Async extends Thread {

    private Notificacion notificacion;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void RegistroDePago(Notificacion notificacion){
        this.notificacion = notificacion;
    }

    public void run()
    {
        log.info("Iniciando registro de nuevo Pago: Notificacion payload:"
                + notificacion.getPayload());

        log.info("Finalizando registro de nuevo Pago: Notificacion payload:"
                + notificacion.getPayload());

    }
}
