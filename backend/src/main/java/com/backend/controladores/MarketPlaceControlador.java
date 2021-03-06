package com.backend.controladores;
import com.backend.dto.Mensaje;
import com.backend.entidades.MarketPlace;
import com.backend.servicios.MarketPlaceServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Enumeration;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/marketplace/")
public class MarketPlaceControlador {
    ArrayList<Long> enEspera = new ArrayList<Long>();
    @Autowired
    MarketPlaceServicio marketPlaceServicio;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("obtener")
    public ResponseEntity<MarketPlace> obtenerConfiguracion() {
        MarketPlace marketPlace = marketPlaceServicio.obtener();
        if (marketPlace == null) {
            return new ResponseEntity(new Mensaje(" No existe ningún APP_ID y CLIENT_SECRET configurado"), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<MarketPlace>(marketPlace, HttpStatus.OK);
        }

    }


    @PostMapping("nuevo")
    public ResponseEntity<?> nuevaConfiguracion(@RequestBody MarketPlace marketPlace) {
        try{
            if (marketPlaceServicio.obtener() == null) {
                marketPlaceServicio.guardar(marketPlace);
                return new ResponseEntity(new Mensaje("MarketPlace - APP_ID y CLIENT_SECRET resguardados"), HttpStatus.CREATED);
            } else {
                // Actualizar el que ya esta
                MarketPlace mp = this.marketPlaceServicio.obtener();
                mp.setAppID(marketPlace.getAppID());
                mp.setClientSecret(marketPlace.getClientSecret());
                this.marketPlaceServicio.guardar(mp);
                return new ResponseEntity(new Mensaje("MarketPlace - APP_ID y CLIENT_SECRET resguardados"), HttpStatus.CREATED);
                // return new ResponseEntity(new Mensaje("Ya existe una configuración de MarketPlace"), HttpStatus.BAD_REQUEST);
            }
        }catch ( Exception e){
            return new ResponseEntity(new Mensaje("Error Except "), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("actualizar")
    public ResponseEntity<?> actualizarConfiguracion(@RequestBody MarketPlace marketPlace) {
        if (marketPlace.getAppID().isBlank() || (marketPlace.getClientSecret().isBlank())) {
            return new ResponseEntity(new Mensaje("Los campos de configuración no pueden estar vacíos"), HttpStatus.BAD_REQUEST);
        } else {
            MarketPlace mPlace = marketPlaceServicio.obtener();
            mPlace.setAppID(marketPlace.getAppID());
            mPlace.setClientSecret(marketPlace.getClientSecret());
            marketPlaceServicio.guardar(mPlace);
            return new ResponseEntity(new Mensaje("MarketPlace - APP_ID y CLIENT_SECRET resguardados"), HttpStatus.CREATED);

        }
    }

    @DeleteMapping("borrar")
    public ResponseEntity<?> borrarConfiguracion() {
        if (marketPlaceServicio.obtener() == null) {
            return new ResponseEntity(new Mensaje("No existe configuración de MarketPlace"), HttpStatus.BAD_REQUEST);
        } else {
            MarketPlace mp = this.marketPlaceServicio.obtener();
            marketPlaceServicio.borrar(mp);
            return new ResponseEntity(new Mensaje("MarketPlace - APP_ID y CLIENT_SECRET eliminados "), HttpStatus.CREATED);
        }
    }

    @GetMapping("armarurlvinculacion/{id}")
    public ResponseEntity<?> armarurl(@PathVariable String id) {
        log.info("Entrando ArmarURLVinculacion id=" + id);
        String url = this.marketPlaceServicio.armarurl(id);
        if (url != null) {
            log.info("arme url: "+url);
            return new ResponseEntity<Mensaje>(new Mensaje(url), HttpStatus.OK);
        } else {
            log.info("ESE USUARIO YA ESTA VINCULADO en MusicAPP");
            return new ResponseEntity<Mensaje>(new Mensaje("USUARIO YA EXISTIA"), HttpStatus.BAD_REQUEST);
        }


    }

    @RequestMapping(value = "vueltamp/{id}", method = RequestMethod.GET)
    public void vueltamp(@RequestParam("code") String code, @PathVariable String id) throws MalformedURLException {
        log.info(" MPAGO devuelve código por URL publicada (paso armar url)");
        log.info("ID COMERCIO" + id + "CODE: " + code);


        try {
            this.marketPlaceServicio.vincular(code, id);
            log.info("Hice el proceso desde el Backend, al FEND lo redireccioné al home");

        } catch (Exception e) {

        }

    }

    @RequestMapping(value = "notificacion", method = RequestMethod.POST)
    public ResponseEntity<?>  notificacion(@RequestBody String payload) throws MalformedURLException {

        try {

            log.info("MPAGO envio una notificacion : " + payload);
            this.marketPlaceServicio.registrarNotificacion(payload);

            return new ResponseEntity<Mensaje>(new Mensaje("Notificacion Guardada"), HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Mensaje>(new Mensaje("Ocurrio un error"), HttpStatus.BAD_REQUEST);
        }
    }

}
