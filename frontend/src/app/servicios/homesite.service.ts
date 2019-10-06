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
  //baseURL = 'https://localhost:8081/api/post/';

  // PRODUCCION URL
  baseURL = 'https://localhost:8081/api/post/';
 
  constructor(private httpClient: HttpClient) { }

  public obtener(login : LoginDatos, inicio : number, fin : number): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('inicio', String (inicio) );
    formdata.append('fin', String(fin) )

    return this.httpClient.post<any>(this.baseURL + 'obtenerHomeSite', formdata);
  }

  
}
