import { Usuario } from './usuario';
import { Lugar } from './lugar';

export class Comercio {
  id?: number;
  usuario: Usuario;
  code : String;
  accessToken : String;
  fechaExpiracion : Date;
  direccion : String;
  razonsocial : String;
  lugaresAsociados : Lugar[];
}


