import { Banda } from './banda';
import { Artista } from './artista';

export class PuntuacionArtista {
  id?: number;
  comentario: String;
  puntuacion: number;
  artistaPuntuador: number;
  artistaPuntuado: number;
  artistaLoaded: Artista;

  
}
