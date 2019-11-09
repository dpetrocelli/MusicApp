import { PuntuacionArtista } from './puntuacion';
import { Artista } from './artista';
import { GeneroMusical } from './generoMusical';
import { Zona } from './zona';
export class Banda {
  id?: number;
  artistaLider : Artista;
  nombre : String;
  generoMusical : GeneroMusical;
  puntuacionesRecibidas : PuntuacionArtista[];
  zona : Zona;
  promedio : number;
  videoBasico : String;
  listaYoutube : String;
}
