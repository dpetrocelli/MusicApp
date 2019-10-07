import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Instrumento } from '../modelos/instrumento';
import { environment } from '../../environments/environment';

const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class InstrumentoService {

  baseURL = environment.urlApiBackend + 'api/instrumento/';
 
  constructor(private httpClient: HttpClient) { }

  public lista(): Observable<Instrumento[]> {
    return this.httpClient.get<Instrumento[]>(this.baseURL + 'listar', cabecera);
  }

  public detalle(id: number): Observable<Instrumento> {
    return this.httpClient.get<Instrumento>(this.baseURL + `detalle/${id}`, cabecera);
  }

  public crear(instrumento: Instrumento): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'nuevo', instrumento, cabecera);
  }

  public editar(instrumento: Instrumento, id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseURL + `actualizar/${id}`, instrumento, cabecera);
  }

  public borrar(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.baseURL + `borrar/${id}`, cabecera);
  }
}
