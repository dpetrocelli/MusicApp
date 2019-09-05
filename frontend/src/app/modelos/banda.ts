import { Puntuacion } from './puntuacion';
import { Artista } from './artista';
export class Banda {
  id?: number;
  nombre : String;
  generoMusical : String;
  artistaLider : Artista;
  puntuacionesRecibidas : Puntuacion[];
  
      
}
