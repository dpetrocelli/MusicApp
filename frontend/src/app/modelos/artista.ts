import { Usuario } from './usuario';
import { Puntuacion } from './puntuacion';
import { Instrumento } from './instrumento';
import { Banda } from './banda';
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
  puntuacionesRealizadas : Puntuacion[];
  puntuacionesRecibidas : Puntuacion[];

      
}
