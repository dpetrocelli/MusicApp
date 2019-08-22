package com.backend.servicios;


import com.backend.entidades.Comercio;
import com.backend.entidades.MarketPlace;
import com.backend.entidades.Notificacion;
import com.backend.repositorios.ComercioRepositorio;
import com.backend.repositorios.MarketPlaceRepositorio;

import com.backend.repositorios.NotificacionRepositorio;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class MarketPlaceServicio {
    @Autowired
    ComercioRepositorio comercioRepositorio;
    @Autowired
    MarketPlaceRepositorio marketPlaceRepositorio;
    @Autowired
    NotificacionRepositorio notificacion;

    @Autowired
    UsuarioServicio usuarioServicio;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public MarketPlace obtener() {
        log.info(" TOME LOS DATOS DE TOKEN MARKETPLACE POR DEFECTO");
        List<MarketPlace> marketPlace = marketPlaceRepositorio.findAll();
        if (marketPlace.size() > 0) {
            return marketPlace.get(0);
        } else {
            return null;
        }
    }

    public void guardar(MarketPlace marketPlace) {
        marketPlaceRepositorio.save(marketPlace);
        log.info("Guarde información de marketplace: " + marketPlace.getAppID());
    }

    public void borrar(MarketPlace mp) {

        mp.setClientSecret(null);
        mp.setAppID(null);
        //marketPlaceRepositorio.deleteAll();
        marketPlaceRepositorio.save(mp);
        log.info(" Vacié información de configuración básica");
    }

    public String armarurl(String id) {
        log.info(" Comercio inició vinculación con MPAGO");
        log.info(" OBJ: armado de URL y redirección del FEND hacia MPAGO");
        try {
            Comercio c = comercioRepositorio.findByUsuario(this.usuarioServicio.obtener(Long.parseLong(id))).get();
            log.info("Armarurl() para: " + c.getUsuario().getUsername());
            if (c.getCode() == null) {
                String appid = String.valueOf((this.obtener().getAppID()));
                log.info("obtuve app id" + appid);
                // PROD URL
                // String urlServicio = "http://localhost:8080/api/marketplace/vueltamp/"+id;
                // DESA URL
                String urlServicio = "http://localhost:8081/api/marketplace/vueltamp/" + id;
                String url = new String("https://auth.mercadopago.com.ar/authorization?client_id=" + appid + "&response_type=code&platform_id=mp&redirect_uri=" + urlServicio);
                log.warn(" URL HACIA MPG: " + url);
                return url;
            } else return null;
        } catch (Exception e) {
            return null;
        }

    }

    public void vincular(String code, String id) {
        MarketPlace mp = this.obtener();
        try {
            log.info(" Para obtener Access Token que permita a MarketPlace (MusicAPP) vender/cobrar en nombre del comercio");
            log.info(" Necesito armar (con el code que llegó, armar una URL con el método POST desde BACKEND");

            log.info(" Comenzamos petición POST ");
            URL url = new URL("https://api.mercadopago.com/oauth/token");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);


            String urlRegreso = "http://localhost:8081/api/marketplace/vueltamp/" + id;
            //AccessTokenMP atmp = new AccessTokenMP(mp.getAppID(), mp.getClientSecret(), "authorization_code", code, urlRegreso);
            JSONObject obj = new JSONObject();

            obj.put("client_id", mp.getAppID());
            obj.put("client_secret", mp.getClientSecret());
            obj.put("grant_type", "authorization_code");
            obj.put("code", code);
            obj.put("redirect_uri", urlRegreso);

            log.info(" Para eso definimos la url a donde enviar");
            log.info("https://api.mercadopago.com/oauth/token");
            log.info(" Y le pasamos cabeceras: POST - Content Type ");
            log.info(" Y creamos un objeto JSON con los datos del MarketPlace + Vendedor para obtenerlo");
            log.info(" client_id (Market) - client_secret (Market) - code (Comercio de paso anterior");
            log.info(" siendo el objeto" + obj.toString());


            log.info(" Enviamos la petición : ");

            try (OutputStream os = con.getOutputStream()) {

                byte[] input = obj.toString().getBytes("utf-8");
                log.info(" Más allá que arme el objeto JSON, lo tengo que pasar a String y luego enviarlo como bytes en el body de POST ");
                os.write(input, 0, input.length);
                os.flush();
                log.info(" Request Enviado: ");

                String stringResponse = null;
                log.info(" Leyendo Response de MercadoPago: ");
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    stringResponse = response.toString();
                }
                log.info(" Finalicé de leer el objeto JSON de Mercadopago, ahora voy a buscar obtener ACC TOKEN");
                JSONObject jsonObject = new JSONObject(stringResponse);
                String accessToken = (String) jsonObject.get("access_token");
                log.info(" Token Obtenido ");
                int expDate = (int) jsonObject.get("expires_in");
                long expDateLong = expDate;
                expDateLong = expDateLong * 1000;
                log.info(" Tomo tiempo de MercadoPago (6 meses) y se lo sumo a la fecha de hoy : " + expDateLong);

                Date date = new Date();
                //log.info(" DATE TODAY: "+date);
                long timeMilli = date.getTime();

                timeMilli = timeMilli + expDateLong;
                Date currentDate = new Date(timeMilli);

                log.info("ACC TOK: " + accessToken);
                log.info(" CURR DAT: " + currentDate);

                Comercio c = comercioRepositorio.findByUsuario(this.usuarioServicio.obtener(Long.parseLong(id))).get();
                if (c != null) {
                    log.info("ENCONTRE COMERCIO" + c.getUsuario().getUsername());
                } else {
                    log.info("No lo pude encontrar");

                }


                log.info("Registrando Datos (ACC TOKEN) en el COMERCIO ");
                c.setUsuario(this.usuarioServicio.obtener(Long.parseLong(id)));
                c.setCode(code);
                c.setAccessToken(accessToken);
                c.setFechaExpiracion(currentDate);
                comercioRepositorio.save(c);
                log.info("GUARDE ");
            }
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }

    }

    public Boolean registrarNotificacion(Long id, String topic) {

        try {
            log.info("Registrando Nueva Notificacion: id:" + id.toString() + ", topic:" + topic);

            notificacion.save(new Notificacion(id, topic));
            //luego de registrar la notificacion hay que buscar el recurso en MP por el ID y luego registrar el pago

        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            log.error("Ocurrio un error al intentar guardar la nofiticacion: id:"
                    + id.toString()
                    + ", topic:"
                    + topic + " - "
                    + e.getStackTrace() );

            return false;
        }
        log.info("Nueva Notificacion registrada: id:" + id.toString() + ", topic:" + topic);
        return true;
    }

}
