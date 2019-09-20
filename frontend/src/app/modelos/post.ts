import { Elemento } from "./elemento";
import { Biografia } from './biografia';

export class Post {
  id?: number;
  informacion: String ;
  fechaCreacion: Date;
  fechaEdicion: Date;
  elementos: Elemento[];
  biografia: Biografia;
  username : string; 
  nickname : string; 
  
  
}
