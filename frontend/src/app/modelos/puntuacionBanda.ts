import { Banda } from './banda';
import { Artista } from './artista';

export class PuntuacionBanda {
  id?: number;
  comentario: String;
  puntuacion: number;
  artistaPuntuador: number;
  bandaPuntuada: number;
  artistaLoaded: Artista;

}
