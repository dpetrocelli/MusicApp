package com.backend.recursos;
import com.backend.entidades.Promocion;
import com.mercadopago.*;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public String publicar (String titulo, String descripcion, String moneda, float precio, float ganancia, Date vigencia){
        try{
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
            this.preference.setPayer(payer);
            this.preference.setExpirationDateTo(vigencia);
            this.preference.appendItem(this.item);
            log.info("adjuntamos a la preferencia el item");
            this.preference.setMarketplaceFee((float) ganancia);
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
