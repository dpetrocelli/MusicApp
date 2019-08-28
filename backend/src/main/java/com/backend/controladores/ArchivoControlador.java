package com.backend.controladores;
import com.backend.dto.Mensaje;
import com.backend.entidades.Instrumento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/archivo/")
@CrossOrigin(origins = "*")
public class ArchivoControlador {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @GetMapping("descargar")
    public ResponseEntity<byte[]> getFoos(@RequestParam(required = true) String path) {
        try {

            log.info("path: " + path);
            InputStream is = new FileInputStream(path);
            BufferedImage img = ImageIO.read(is);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", bos);

            return new ResponseEntity(bos.toByteArray(), HttpStatus.OK);
        } catch (IOException e) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            return new ResponseEntity(bos.toByteArray(), HttpStatus.BAD_REQUEST);
        }


    }

}
