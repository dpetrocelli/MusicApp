import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Instrumento } from '../modelos/instrumento';
import { PuntuacionArtista } from '../modelos/puntuacion';
import { LoginDatos } from '../modelos/logindatos';
import { environment } from '../../environments/environment';

const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class PuntuacionService {

  baseURL = environment.urlApiBackend + 'api/puntuacion/';
  
  constructor(private httpClient: HttpClient) { }

  public crear(ld : LoginDatos, usuarioPuntuado : string, comentario : string, puntuacion: string): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(ld));
    formdata.append('usuarioPuntuado', usuarioPuntuado);
    formdata.append('comentario', comentario);
    formdata.append('puntuacion', puntuacion);
    return this.httpClient.post<any>(this.baseURL + 'nuevo', formdata);
  }

  public obtenerPuntuacion(login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'obtenerPuntuacion', login, cabecera);
  }

  public verificarSiPuntuee(login : LoginDatos, nombre : string): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('nombre', nombre);
    return this.httpClient.post<any>(this.baseURL + 'verificarSiPuntuee',formdata);
  }

  public obtenerPuntuacionRedSocial(login : LoginDatos, nombre : string): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('nombre', nombre);
    return this.httpClient.post<any>(this.baseURL + 'RedSocialObtenerPuntuacion',formdata);
  }
  
  public obtenerPuntuacionBanda(login : LoginDatos, nombre : String): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('nombre', nombre.toString());
    return this.httpClient.post<any>(this.baseURL + 'ObtenerPuntuacionBanda',formdata);
  }
  
/*
  public editar(instrumento: Instrumento, id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseURL + `actualizar/${id}`, instrumento, cabecera);
  }

  public borrar(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.baseURL + `borrar/${id}`, cabecera);
  }*/
}
