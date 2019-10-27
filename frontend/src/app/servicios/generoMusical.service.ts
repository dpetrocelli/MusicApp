import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GeneroMusical } from '../modelos/generoMusical';
import { Observable } from 'rxjs';

const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class GeneroMusicalService {

  baseURL = environment.urlApiBackend + 'api/generoMusical/';

  constructor(private httpClient: HttpClient) { }

  public lista(): Observable<GeneroMusical[]> {
    return this.httpClient.get<GeneroMusical[]>(this.baseURL + 'listar', cabecera);
  }

  public detalle(id: number): Observable<GeneroMusical> {
    return this.httpClient.get<GeneroMusical>(this.baseURL + `detalle/${id}`, cabecera);
  }

  public crear(generoMusical: GeneroMusical): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'nuevo', generoMusical, cabecera);
  }

  public editar(generoMusical: GeneroMusical, id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseURL + `actualizar/${id}`, generoMusical, cabecera);
  }

  public borrar(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.baseURL + `borrar/${id}`, cabecera);
  }
}