import { Usuario } from './usuario';
import { Zona } from './zona';

export class Lugar {
  id?: number;
  nombre: String;
  foto : String;
  descripcion : String;
  telefono : String;
  direccion : String;
  zona : Zona;
  
}


