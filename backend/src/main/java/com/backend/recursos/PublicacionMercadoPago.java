package com.backend.recursos;
import com.backend.entidades.Promocion;
import com.backend.singleton.ConfiguradorSingleton;
import com.mercadopago.*;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import com.mercadopago.resources.datastructures.preference.PaymentMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PublicacionMercadoPago {
    Preference preference;
    String accessToken;
    Item item;
    Payer payer;

    @Autowired
    ConfiguradorSingleton configuradorSingleton;


    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    public PublicacionMercadoPago(String accessToken) {

        this.accessToken = accessToken;
        try{
            MercadoPago.SDK.setAccessToken(this.accessToken);
            log.info("SDK MERCADOPAGO SETEADO CORRECTAMENTE");
        }catch (Exception e){
            log.info("ERROR CON SDK MERCADOPAGO");
        }
    }



    public String getMarketPlaceDefinido (){
        return this.preference.getMarketplace();
    }

    public Preference obtenerPreferencia (){
        return this.preference;
    }

    public String editar (String preferenceId, Promocion promocion){
        try{

            this.preference = Preference.findById(preferenceId);

            log.info("Entramos a EDITAR una Promocion en MP ");
            log.info("DATOS PUB"+promocion.getTitulo()+"/"+promocion.getDescripcion()+"/"+promocion.getTipomoneda()+"/"+promocion.getImporte());
            //this.item = (this.preference.getItems()).get(0);
            //String titulo, String descripcion, String moneda, float precio, float ganancia, Date vigencia){
            this.item = new Item();this.item.setId("1234");this.item.setCurrencyId("ARS");
            this.item.setQuantity(1);
            this.item.setTitle(promocion.getTitulo());
            this.item.setDescription(promocion.getDescripcion());
            this.item.setUnitPrice((float) promocion.getImporte());
            Payer payer = new Payer();

            this.preference.setPayer(payer);

            this.preference.setExpirationDateTo(promocion.getVigencia());
            // borrar el item actual
            ArrayList<Item> listItems = new ArrayList<Item>();
            listItems.add(this.item);
            this.preference.setItems(listItems);




            // Guardar y postear la preferencia
            this.preference.update();

            return this.preference.getInitPoint();

        }catch (Exception e){
            log.info ("ESE Pref ID no anda "+preferenceId);
            return null;
        }



    }
    public String publicar (String baseurl, String title, String titulo, String descripcion, String moneda, float precio, float ganancia, Date vigencia){
        try{
            String VueltaPagoURL = "/api/marketplace/";
            this.preference = new Preference();
            log.info("Entramos a publicar una Promocion en MP ");
            log.info("DATOS PUB"+titulo+"/"+descripcion+"/"+moneda+"/"+precio+"/"+ganancia);
            this.item = new Item();
            this.item.setId("1234");
            this.item.setTitle(titulo);
            this.item.setDescription(descripcion);
            this.item.setCurrencyId("ARS");
            this.item.setQuantity(1);
            this.item.setUnitPrice((float) precio);

            log.info("seteamos todas las propiedades");

            Payer payer = new Payer();
            log.info("Creamos payer vacio");

            PaymentMethods paymentMethods = new PaymentMethods();
            paymentMethods.setInstallments(1);
            paymentMethods.setInstallments(3);
            paymentMethods.setInstallments(6);
            paymentMethods.setInstallments(12);
            paymentMethods.setDefaultInstallments(1);

            this.preference.setPaymentMethods(paymentMethods);
            this.preference.setPayer(payer);

            // Para la vuelta de MP cuando paga (ID de promoción)
            this.preference.setAdditionalInfo(title);

            // Backs urls para el tema de registrar la venta

               /*


            BackUrls backUrls = new BackUrls(baseurl+VueltaPagoURL+"pago_exitoso", baseurl+VueltaPagoURL+"pago_erroneo", baseurl+VueltaPagoURL+"pago_pendiente");
            this.preference.setBackUrls(backUrls);


                */
            this.preference.setNotificationUrl(baseurl+VueltaPagoURL+"notificacion");

            log.info("url de notificacion de paog: " + baseurl+VueltaPagoURL+"notificacion");

            this.preference.setExpirationDateTo(vigencia);
            this.preference.appendItem(this.item);
            log.info("adjuntamos a la preferencia el item");
            this.preference.setMarketplaceFee((float) (precio*(ganancia/100)));
            log.info("seteamos la ganancia");
            // Guardar y postear la preferencia
            this.preference.save();
            log.info("guardamos la preferencia en MPago");
            log.info("EL ID De la preferencia obtenida es: "+this.preference.getId());
            return this.preference.getId();

        }catch (Exception e){
            log.info(" ERROR CON MPAGO PuB");
            return null;
        }




    }

    public String borrar(String idPublicacionMP, Promocion promocion) {
        try{

            this.preference = Preference.findById(idPublicacionMP);

            log.info("Entramos a ELIMINAR una Promocion en MP ");

            Date expirado = new Date(System.currentTimeMillis()-24*60*60*1000);

            this.preference.setExpirationDateTo(expirado);
            // borrar el item actual

           // Guardar y postear la preferencia
            this.preference.update();

            log.info ( " INIT_ POINT ELIM: "+this.preference.getInitPoint());
            return this.preference.getInitPoint();

        }catch (Exception e){
            log.info ("ESE Pref ID no anda "+idPublicacionMP);
            return null;
        }
    }
}
