import { Artista } from './artista';
import { Post } from './post';

export class Biografia {
  id?: number;
  artista: Artista; 
  biografiaBasica: string;
  posts : Post[];
  pathImagenPerfil : string;
  pathImagenPortada : string;
  
}
