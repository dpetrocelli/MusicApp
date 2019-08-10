import { Rol } from './rol';

export class Usuario {
  id?: number;
  username: string;
  pwd: string;
  email: string;
  usertype: Set<Rol>;
}
