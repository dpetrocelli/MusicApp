package com.backend.controladores;
import com.backend.dto.Mensaje;
import com.backend.entidades.Instrumento;
import com.backend.entidades.MarketPlace;
import com.backend.servicios.InstrumentoServicio;
import com.backend.servicios.MarketPlaceServicio;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marketplace")
@CrossOrigin(origins = "*")
public class MarketPlaceControlador {

    @Autowired
    MarketPlaceServicio marketPlaceServicio;

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
}
