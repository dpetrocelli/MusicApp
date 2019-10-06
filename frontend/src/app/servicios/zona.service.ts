import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Zona } from '../modelos/zona';

const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class ZonaService {
  // DESARROLLO URL
  baseURL = 'http://localhost:8081/api/zonaGeografica/';

  // PRODUCCION URL
  //baseURL = 'http://ec2-3-93-69-45.compute-1.amazonaws.com:9000/api/Zona/';
 
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
