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
export class BandaService {
 

  private isUserLoggedIn = false;
  usuarioFrontEnd: Usuario = new Usuario();
  loginDatos: LoginDatos = new LoginDatos();
  valueByGet : String;
  // DESARROLLO URL
  baseURL = 'http://localhost:8081/api/banda/';
  

  constructor(private httpClient: HttpClient) { }


  public obtenerTodos (ld : LoginDatos) : Observable <any> {
      return this.httpClient.post<any>(this.baseURL + 'obtenerTodos', ld, cabecera);
  }
  
  
  
}
