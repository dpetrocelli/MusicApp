package com.backend.recursos;

import com.backend.dto.PagoMercadoPago;
import com.backend.entidades.Pago;
import com.backend.entidades.Venta;
import com.backend.servicios.PagoServicio;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.MerchantOrder;
import com.mercadopago.resources.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class OrdenDeVentaMercadoPago {

    private Payment payment;
    private MerchantOrder merchantOrder;

    @Autowired
    PagoServicio pagoServicio;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public OrdenDeVentaMercadoPago() {
    }

    public OrdenDeVentaMercadoPago(String accessToken) {

        try {
            MercadoPago.SDK.cleanConfiguration();
            MercadoPago.SDK.setAccessToken(accessToken);
            log.info("SDK MERCADOPAGO SETEADO CORRECTAMENTE");
        } catch (Exception e) {
            log.info("ERROR CON SDK MERCADOPAGO");
        }
    }

    public OrdenDeVentaMercadoPago(String clientId, String clientSecret) {

        try {
            MercadoPago.SDK.cleanConfiguration();
            MercadoPago.SDK.setClientId(clientId);
            MercadoPago.SDK.setClientSecret(clientSecret);
            log.info("SDK MERCADOPAGO SETEADO CORRECTAMENTE");
        } catch (Exception e) {
            log.info("ERROR CON SDK MERCADOPAGO");
        }
    }

    public Pago obtenerPago(PagoMercadoPago pagoMercadoPago) {
        Pago pago = null;

        try {
            String token=MercadoPago.SDK.getAccessToken();
            payment = Payment.findById(pagoMercadoPago.getId());
            log.info("Pago obtenido desde MP");

            if (payment.getOrder() != null) {

                merchantOrder = MerchantOrder.findById(Long.toString(payment.getOrder().getId()));

                pago = new Pago(
                        Long.parseLong("0")
                        , Long.parseLong(payment.getId())
                        , payment.getDateCreated()
                        , TipoMovimiento.DINERO_MP
                        , payment.getStatus().name()
                        , payment.getTransactionAmount()
                );
/*
                pago = new Pago(
                        Long.parseLong(merchantOrder.getId())
                        , Long.parseLong(payment.getId())
                        , payment.getDateCreated()
                        , TipoMovimiento.DINERO_MP
                        , payment.getStatus().name()
                        , payment.getTransactionAmount()
                );
*/
            }

        } catch (MPException e) {
            e.printStackTrace();
            log.info(e.toString());
        }

        return pago;
    }

    public Venta obtenerVenta(Pago pago) {
        Venta venta = null;

        try {
            merchantOrder = MerchantOrder.findById(Long.toString(pago.getIdVenta()));
            log.info("Venta obtenida desde MP");

                venta = new Venta(
                        pago.getIdVenta()
                        ,merchantOrder.getDateCreated()
                        ,merchantOrder.getStatus()
                        ,0.01
                        ,0.01
                );

        } catch (MPException e) {
            e.printStackTrace();
            log.info(e.toString());
        }

        return venta;
    }
}
