package com.backend.controladores;
import com.backend.dto.Mensaje;
import com.backend.entidades.Instrumento;
import org.apache.commons.io.FilenameUtils;
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
@CrossOrigin(origins = "*")
@RequestMapping("/api/archivo/")
public class ArchivoControlador {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // se define el path básico del sistema de archivos.
    // Carpeta SRC del proyecto bajo el path images
    // luego to do se hará en base al usuario en cuestión

    private static String BASE_PATH = "src/images/";

    @GetMapping("descargar")
    public ResponseEntity<byte[]> getFoos(@RequestParam(required = true) String path) {
        try {
        	/*
        	Validar si es nulo -> esto significa que lo que me está diciendo es que no tiene cargada
        	imagen de perfil aún.
        	 */
        	if(path.equals("null")) {
        	    // Si nulo es porque no tiene imagen
                // entonces la carpeta base es "default"
                File defImage = new File(BASE_PATH+"default/");

                /*
                    si es la primera vez que levanta el sistema
                    no tengo la imagen por defecto.  Por eso la descargo de internet
                     y creo el directorio
                 */
                if (!defImage.exists()) {
                    if (defImage.mkdirs()) {
                        System.out.println("Directorio creado"+defImage.getAbsolutePath());
                        this.descargarImagenDefecto(defImage);
                        log.info(" Descargue img basica");
                    } else {
                        System.out.println("Error al crear directorio");
                    }
                }

                // por lo tanto el path para obtener la imagen quedo /src/images/default/default.jpg
                path = defImage.getAbsolutePath()+"/default.jpg";

        	}
        	/*
        	 Ya sea path real (que viene por parámetro)
        	 o path que yo construí, leo la imagen y envio
        	 */
            String extension = FilenameUtils.getExtension(path);

            InputStream is = new FileInputStream(path);
            BufferedImage img = ImageIO.read(is);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(img, extension, bos);
            return new ResponseEntity(bos.toByteArray(), HttpStatus.OK);
        } catch (IOException e) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            return new ResponseEntity(bos.toByteArray(), HttpStatus.BAD_REQUEST);
        }


    }

    // método para descargar la imagen de internet y poner en default
    private void descargarImagenDefecto(File file) {
        try{
            URL url = new URL("https://icon-library.net/images/default-user-icon/default-user-icon-4.jpg");
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
