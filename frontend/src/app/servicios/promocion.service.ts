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
  // DESARROLLO IP
  baseURL = 'http://localhost:8081/api/promocion/';
  // PRODUCCION IP
  //baseURL = 'http://localhost:8080/api/instrumento/';
 
//  instrumentoURL = 'http://ec2-54-224-216-200.compute-1.amazonaws.com:9000/api/instrumento/';

  constructor(private httpClient: HttpClient) { }

  public lista(login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'listar',login, cabecera);
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

  public borrar(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.baseURL + 'borrar/${id}', cabecera);
  }
}
