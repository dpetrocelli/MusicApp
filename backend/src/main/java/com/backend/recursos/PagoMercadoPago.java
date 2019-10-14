package com.backend.recursos;

import com.backend.entidades.Notificacion;
import com.backend.entidades.Pago;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.Payer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PagoMercadoPago {

    private String accessToken = null;
    private Payment payment;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public PagoMercadoPago(){}

    public PagoMercadoPago(String accessToken) {

            this.accessToken = "APP_USR-2692174750312512-072417-a20187e235c70277c2dba06afaf040c9-53403839";
            try{
                MercadoPago.SDK.setAccessToken(this.accessToken);
                Payment pago = new Payment();
                log.info("SDK MERCADOPAGO SETEADO CORRECTAMENTE");
            }catch (Exception e){
                log.info("ERROR CON SDK MERCADOPAGO");
            }
        }

}
