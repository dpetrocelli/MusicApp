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
import java.io.*;
import java.net.URL;

@RestController
@RequestMapping("/api/archivo/")
@CrossOrigin(origins = "*")
public class ArchivoControlador {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @GetMapping("descargar")
    public ResponseEntity<byte[]> getFoos(@RequestParam(required = true) String path) {
        try {
        	log.info("/api/archivo/descargar -> path:"+path);
        	if(path.equals("null")) {
        	    // Si nulo es porque no tiene imagen
                File defImage = new File("src/images/default/");

                // si es primera vez y no tengo la imagen por defecto la descargo y creo el directorio
                if (!defImage.exists()) {
                    if (defImage.mkdirs()) {
                        System.out.println("Directorio creado"+defImage.getAbsolutePath());

                        this.descargarImagenDefecto(defImage);
                        log.info(" Descargue img");
                    } else {
                        System.out.println("Error al crear directorio");
                    }
                }


                path = defImage.getAbsolutePath()+"/default.jpg";

        	}

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

    private void descargarImagenDefecto(File file) {
        try{
            URL url = new URL("http://datamining.dc.uba.ar/datamining/docentes/unknown.png");
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1!=(n=in.read(buf)))
            {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();
            FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile()+"/default.jpg");
            fos.write(response);
            fos.close();

        }catch (Exception e){
            log.info(" ERROR "+e.getMessage());
        }

    }

}
