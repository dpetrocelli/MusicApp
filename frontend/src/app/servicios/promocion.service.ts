import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginDatos } from '../modelos/logindatos';
import { Promocion } from '../modelos/promocion';

const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class PromocionService {
  // DESARROLLO URL
  baseURL = 'http://localhost:8081/api/promocion/';

  // PRODUCCION URL
  //baseURL = 'http://ec2-3-93-69-45.compute-1.amazonaws.com:8080/api/instrumento/';
 
  constructor(private httpClient: HttpClient) { }

  public lista(login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'listar',login, cabecera);
  }

  public obtenerVigentes(login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'obtenerVigentes',login, cabecera);
  }

  public comprar(promocion: Promocion, login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'comprar', {promocion, login}, cabecera);
  }

  public detalle(id: number, ld :LoginDatos): Observable<Promocion> {
    return this.httpClient.post<Promocion>(this.baseURL + `detalle/${id}`,ld, cabecera);
  }

  public crear(promocion: Promocion, login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'nuevo', {promocion, login}, cabecera);
  }

  public editar(promocion: Promocion, login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'editar', {promocion, login}, cabecera);
  }

  public borrar(promocion: Promocion, login :LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'borrar/', {promocion, login}, cabecera);
  }
}
