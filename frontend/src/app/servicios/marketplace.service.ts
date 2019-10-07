import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Marketplace} from '../modelos/marketplace';
import { Mensaje } from '../modelos/mensaje';
import { environment } from '../../environments/environment';

const cabecera = {headers: new HttpHeaders({'Content-Type': 'application/json; charset=utf-8'})};

@Injectable({
  providedIn: 'root'
})

export class MarketplaceService {

  baseURL = environment.urlApiBackend + 'api/marketplace/';

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
