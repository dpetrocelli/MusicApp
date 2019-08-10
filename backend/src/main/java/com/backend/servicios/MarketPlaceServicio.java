package com.backend.servicios;


import com.backend.entidades.Comercio;
import com.backend.entidades.MarketPlace;
import com.backend.repositorios.ComercioRepositorio;
import com.backend.repositorios.MarketPlaceRepositorio;
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

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public MarketPlace obtener() {
        List<MarketPlace> marketPlace = marketPlaceRepositorio.findAll();
        if (marketPlace.size() > 0) {
            return marketPlace.get(0);
        } else {
            return null;
        }
    }

    public void guardar(MarketPlace marketPlace) {
        marketPlaceRepositorio.save(marketPlace);
    }

    public void borrar() {
        marketPlaceRepositorio.deleteAll();
    }

    public String armarurl(String id) {
        if (!(comercioRepositorio.existsByIdComercio(Long.parseLong(id)))) {
            log.info("No hay registros, guardo");
            Comercio cd = new Comercio(Long.parseLong(id), "", "", null);
            comercioRepositorio.save(cd);
            log.info("GUARDE EL ID DE COMERCIO DE DATOS");

            String appid = String.valueOf((this.obtener().getAppID()));
            log.info("obtuve app id" + appid);
            // PROD URL
            // String urlServicio = "http://localhost:8080/api/marketplace/vueltamp/"+id;
            // DESA URL
            String urlServicio = "http://localhost:8081/api/marketplace/vueltamp/" + id;
            String url = new String("https://auth.mercadopago.com.ar/authorization?client_id=" + appid + "&response_type=code&platform_id=mp&redirect_uri=" + urlServicio);
            return url;
        } else {
            return null;
        }
    }

    public void vincular(String code, String id) {
        MarketPlace mp = this.obtener();
        try {
            System.out.println("VAMOS A PEDIR URL POR POST DE JAVA");
            URL url = new URL("https://api.mercadopago.com/oauth/token");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
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

            log.info(" EL OBJ ES: "+obj.toString());

            log.info(" VAMOS PARA EL CURL : ");

            try(OutputStream os = con.getOutputStream()) {
                log.info(" en el try send: ");
                byte[] input = obj.toString().getBytes("utf-8");
                log.info(" arme le byte: ");
                os.write(input, 0, input.length);
                log.info(" escribi: ");

                os.flush();
                log.info(" escribitodo: ");
                String stringResponse = null;
                try(BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    stringResponse = response.toString();
                }
                JSONObject jsonObject = new JSONObject(stringResponse);
                String accessToken = (String) jsonObject.get("access_token");
                log.info(" LLEGAMOS AL TOKEN");
                int expDate = (int) jsonObject.get("expires_in");
                long expDateLong = expDate;
                expDateLong = expDateLong*1000;
                log.info(" LONG MILIS: "+expDateLong);

                Date date = new Date();
                log.info(" DATE TODAY: "+date);
                long timeMilli = date.getTime();

                timeMilli = timeMilli + expDateLong;
                Date currentDate = new Date(timeMilli);

                log.info("ACC TOK: "+accessToken);
                log.info(" CURR DAT: "+currentDate);

                Comercio c = comercioRepositorio.findByIdComercio(Long.parseLong(id)).get();
                comercioRepositorio.delete(c);
                Comercio cd = new Comercio();
                cd.setIdComercio(Long.parseLong(id));
                cd.setCode(code);
                cd.setAccessToken(accessToken);
                cd.setFechaExpiracion(currentDate);
                comercioRepositorio.save(cd);
            }
        } catch (Exception e){
                System.err.println(e.getMessage());
       }

    }
}
