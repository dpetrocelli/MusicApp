import { Usuario } from './usuario';
import { PuntuacionArtista } from './puntuacion';
import { Instrumento } from './instrumento';
import { Banda } from './banda';
import { Zona } from './zona';
import { GeneroMusical } from './generoMusical';
export class Artista {
  id?: number;
  usuario: Usuario;
  nombre : String;
  apellido : String;
  nickname : String;
  documentoIdentidad : number;
  fechaNacimiento : Date;
  genero : String;
  banda : Banda[];
  generoMusical : GeneroMusical;
  instrumento : Instrumento[];
  puntuacionesRecibidas : PuntuacionArtista[];
  puntuacionesRealizadas: PuntuacionArtista[];
  zona : Zona;
}

