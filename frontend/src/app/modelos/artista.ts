import { Usuario } from './usuario';
import { PuntuacionArtista } from './puntuacion';
import { Instrumento } from './instrumento';
import { Banda } from './banda';
import { Zona } from './zona';
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
  generoMusical : String;
  instrumento : Instrumento[];
  puntuacionesRecibidas : PuntuacionArtista[];
  zona : Zona;
}
