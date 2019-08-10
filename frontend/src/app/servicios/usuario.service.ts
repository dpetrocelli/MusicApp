import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../modelos/usuario';

const cabecera = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private isUserLoggedIn = false;
  
  usuarioFrontEnd: Usuario = new Usuario();
  valueByGet : String;
  // DESARROLLO URL
  baseURL = 'http://localhost:8081/api/usuario/';
  // PROD URL
  //baseURL = 'http://localhost:8080/api/usuario/';

  constructor(private httpClient: HttpClient) { }


  setUserLoggedIn(user:Usuario) {
    this.isUserLoggedIn = true;
    this.usuarioFrontEnd = user;
    localStorage.setItem('currentUser', JSON.stringify(user));
  }

  getUserLoggedIn() {
  	return JSON.parse(localStorage.getItem('currentUser'));
  }


 
  public ingresar(usuarioForm: Usuario): Observable<any> {
  //public ingresar(usuario: Usuario): Observable<any> {
    
    this.usuarioFrontEnd.username = usuarioForm.username;
    this.usuarioFrontEnd.pwd = usuarioForm.pwd;
    console.log (JSON.stringify(this.usuarioFrontEnd));
    return this.httpClient.post<any>(this.baseURL + 'ingresar', this.usuarioFrontEnd, cabecera);
  }

  public registrar(usuarioForm: Usuario, isArtista: boolean): Observable<any> {
    
    this.usuarioFrontEnd.username = usuarioForm.username;
    this.usuarioFrontEnd.pwd = usuarioForm.pwd;
    this.usuarioFrontEnd.email = usuarioForm.email;
    (isArtista)? this.valueByGet = "1" : this.valueByGet = "2";
    
    return this.httpClient.post<any>(this.baseURL + `registrar/${this.valueByGet}`, this.usuarioFrontEnd, cabecera);
  }
}

