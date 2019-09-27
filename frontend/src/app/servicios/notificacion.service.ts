import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../modelos/usuario';
import { LoginDatos } from '../modelos/logindatos';
import { Mensaje } from '../modelos/mensaje';

const cabecera = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class NotificacionService {
  // DESARROLLO URL
  baseURL = 'http://localhost:8081/api/notificacionesBandaUsuario/';

  // PRODUCCION URL
  //baseURL = 'http://ec2-3-93-69-45.compute-1.amazonaws.com:9000/api/banda/';


  private isUserLoggedIn = false;
  usuarioFrontEnd: Usuario = new Usuario();
  loginDatos: LoginDatos = new LoginDatos();
  valueByGet : String;

  constructor(private httpClient: HttpClient) { }

  public obtener (ld : LoginDatos) : Observable <any> {
    
      return this.httpClient.post<any>(this.baseURL + 'obtener', ld, cabecera);
  }
  
  
  
}

