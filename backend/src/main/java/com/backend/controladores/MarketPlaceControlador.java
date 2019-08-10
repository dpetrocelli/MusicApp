package com.backend.controladores;
import com.backend.dto.Mensaje;
import com.backend.entidades.Comercio;
import com.backend.entidades.MarketPlace;
import com.backend.recursos.AccessTokenMP;
import com.backend.repositorios.ComercioRepositorio;
import com.backend.servicios.MarketPlaceServicio;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/api/marketplace")
@CrossOrigin(origins = "*")
public class MarketPlaceControlador {
    ArrayList<Long> enEspera = new ArrayList<Long>();
    @Autowired
    MarketPlaceServicio marketPlaceServicio;
    @Autowired
    ComercioRepositorio comercioDatosRepositorio;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/obtener")
    public ResponseEntity<MarketPlace> obtenerConfiguracion(){
        MarketPlace marketPlace = marketPlaceServicio.obtener();
        if (marketPlace == null){
            return new ResponseEntity(new Mensaje(" No existe ningún APP_ID y CLIENT_SECRET configurado"), HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<MarketPlace>(marketPlace, HttpStatus.OK);
        }

    }


    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevaConfiguracion (@RequestBody MarketPlace marketPlace){
        if (marketPlaceServicio.obtener() == null){
            marketPlaceServicio.guardar(marketPlace);
            return  new ResponseEntity(new Mensaje("MarketPlace - APP_ID y CLIENT_SECRET resguardados"), HttpStatus.CREATED);
        }else{
            return  new ResponseEntity(new Mensaje("Ya existe una configuración de MarketPlace"), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping ("/actualizar")
    public ResponseEntity<?> actualizarConfiguracion (@RequestBody MarketPlace marketPlace){
        if (marketPlace.getAppID().isBlank() || (marketPlace.getClientSecret().isBlank())){
            return  new ResponseEntity(new Mensaje("Los campos de configuración no pueden estar vacíos"), HttpStatus.BAD_REQUEST);
        }else{
            marketPlaceServicio.borrar();
            marketPlaceServicio.guardar(marketPlace);
                return  new ResponseEntity(new Mensaje("MarketPlace - APP_ID y CLIENT_SECRET resguardados"), HttpStatus.CREATED);

        }
    }
    @DeleteMapping("/borrar")
    public ResponseEntity <?> borrarConfiguracion (){
        if (marketPlaceServicio.obtener() == null){
            return  new ResponseEntity(new Mensaje("No existe configuración de MarketPlace"), HttpStatus.BAD_REQUEST);
        }else{
            marketPlaceServicio.borrar();
            return  new ResponseEntity(new Mensaje("MarketPlace - APP_ID y CLIENT_SECRET eliminados "), HttpStatus.CREATED);
        }
    }

    @GetMapping("/armarurlvinculacion/{id}")
    public ResponseEntity<?> armarurl( @PathVariable String id) {
    	System.out.println("Entrando ArmarURLVinculacion id="+id);

        if (!(comercioDatosRepositorio.existsByIdComercio(Long.parseLong(id)))){
            log.info("No hay registros, guardo");
            Comercio cd = new Comercio(Long.parseLong(id), "", "", null);
            comercioDatosRepositorio.save(cd);
            log.info("GUARDE EL ID DE COMERCIO DE DATOS");

            String appid = String.valueOf((this.marketPlaceServicio.obtener().getAppID()));
            log.info("obtuve app id"+appid);
            // PROD URL
            // String urlServicio = "http://localhost:8080/api/marketplace/vueltamp/"+id;
            // DESA URL
            String urlServicio = "http://localhost:8081/api/marketplace/vueltamp/"+id;
            String url = new String("https://auth.mercadopago.com.ar/authorization?client_id="+appid+"&response_type=code&platform_id=mp&redirect_uri="+urlServicio);
            log.info("arme url: "+url);
            return  new ResponseEntity<Mensaje>(new Mensaje(url), HttpStatus.OK);
        }else {
            log.info("ESE USUARIO YA ESTA VINCULADO");
            return  new ResponseEntity<Mensaje>(new Mensaje ("USUARIO YA EXISTIA"), HttpStatus.BAD_REQUEST);
        }


    }

    @RequestMapping(value="/vueltamp/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> vueltamp(@RequestParam("code") String code, @PathVariable String id) throws MalformedURLException {


        System.out.println("CODE:" +code.toString());
        System.err.println("ID COMERCIO PIJUDO:" +id.toString());

        // UNA VEZ QUE ME LLEGO EL CODE TENGO QUE HACER QUE ?
        System.out.println("HOLA");
        try {


            MarketPlace mp = marketPlaceServicio.obtener();

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

               Comercio cd = comercioDatosRepositorio.findByIdComercio(Long.parseLong(id)).get();
               comercioDatosRepositorio.delete(cd);
               cd.setCode(code);
               cd.setAccessToken(accessToken);
               cd.setFechaExpiracion(currentDate);
               comercioDatosRepositorio.save(cd);


            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            return  new ResponseEntity<Mensaje>(new Mensaje("ESTA TODO OK"), HttpStatus.OK);


        }catch (Exception e){
            return  new ResponseEntity<Mensaje>(new Mensaje ("ERROR SE MURIO"), HttpStatus.BAD_REQUEST);
        }







    }

    @RequestMapping(value="/vueltaaccesstoken/{id}", method = RequestMethod.GET)
    public String vueltaaccesstoken (@PathVariable String id) throws MalformedURLException {
        System.out.println ("VOLVIMOS MAGIA");

        return "OK";
    }
}
