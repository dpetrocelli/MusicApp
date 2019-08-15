import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Marketplace} from '../modelos/marketplace';
import { Mensaje } from '../modelos/mensaje';

const cabecera = {headers: new HttpHeaders({'Content-Type': 'application/json; charset=utf-8'})};

@Injectable({
  providedIn: 'root'
})

export class MarketplaceService {
  /* Tengo que tener la URL de la aplicación a la cual le voy a pegar
    En este caso recordar que va a ser /api/marketplace
   */
  // DESARROLLO URL
   baseURL = 'http://localhost:8081/api/marketplace/';
  // PROD URL
  // baseURL = 'http://localhost:8080/api/marketplace/';
  constructor(private httpClient: HttpClient) { }

  public obtener(): Observable <Marketplace> {
    return this.httpClient.get<Marketplace>(this.baseURL + 'obtener', cabecera);
  }

  public nuevo(marketPlace: Marketplace): Observable <Marketplace> {
    return this.httpClient.post<Marketplace>(this.baseURL + 'nuevo', marketPlace, cabecera);
  }

  public borrar(): Observable <Marketplace> {
    return this.httpClient.delete<any>(this.baseURL + 'borrar', cabecera);
  }

  public editar(marketPlace: Marketplace): Observable <Marketplace> {
    return this.httpClient.put<any>(this.baseURL + 'actualizar', marketPlace, cabecera);
  }

  public armarLink(id: number): Observable <Mensaje> {
    return this.httpClient.get<Mensaje>(this.baseURL + `armarurlvinculacion/${id}`, cabecera);
  }

  public vincular(msg, id: number): Observable <Mensaje> {
    return this.httpClient.get<Mensaje>(this.baseURL + `armarurlvinculacion/${id}?msg=${msg}`, cabecera);
  }
  
}
