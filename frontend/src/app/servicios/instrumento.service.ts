import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Instrumento } from '../modelos/instrumento';

const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class InstrumentoService {
  // DESARROLLO IP
  baseURL = 'http://localhost:8081/api/instrumento/';
  // PRODUCCION IP
  //baseURL = 'http://localhost:8080/api/instrumento/';
 
//  instrumentoURL = 'http://ec2-54-224-216-200.compute-1.amazonaws.com:9000/api/instrumento/';

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
