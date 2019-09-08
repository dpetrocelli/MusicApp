import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Instrumento } from '../modelos/instrumento';
import { LoginDatos } from '../modelos/logindatos';

const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class HomeSiteService {
  // DESARROLLO URL
  baseURL = 'http://localhost:8081/api/homesite/';

  // PRODUCCION URL
  //baseURL = 'http://ec2-3-93-69-45.compute-1.amazonaws.com:8080/api/instrumento/';
 
  constructor(private httpClient: HttpClient) { }

  public obtener(login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'obtener', login, cabecera);
  }

  
}
