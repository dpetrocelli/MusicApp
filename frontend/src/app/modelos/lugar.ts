import { Usuario } from './usuario';
import { Zona } from './zona';
import { Comercio } from './comercio';

export class Lugar {
  id?: number;
  nombre: String;
  foto : String;
  descripcion : String;
  telefono : String;
  direccion : String;
  zona : Zona;
  comercio: Comercio;
  
}


