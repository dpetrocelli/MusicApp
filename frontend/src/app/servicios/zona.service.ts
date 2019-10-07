import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Zona } from '../modelos/zona';
import { environment } from '../../environments/environment';

const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class ZonaService {

  baseURL = environment.urlApiBackend + 'api/zonaGeografica/';

  constructor(private httpClient: HttpClient) { }

  public lista(): Observable<Zona[]> {
    return this.httpClient.get<Zona[]>(this.baseURL + 'listar', cabecera);
  }

  public detalle(id: number): Observable<Zona> {
    return this.httpClient.get<Zona>(this.baseURL + `detalle/${id}`, cabecera);
  }

  public crear(Zona: Zona): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'nuevo', Zona, cabecera);
  }

  public editar(Zona: Zona, id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseURL + `actualizar/${id}`, Zona, cabecera);
  }

  public borrar(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.baseURL + `borrar/${id}`, cabecera);
  }
}
