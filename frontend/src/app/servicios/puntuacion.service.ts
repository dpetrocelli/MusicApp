import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Instrumento } from '../modelos/instrumento';
import { Puntuacion } from '../modelos/puntuacion';
import { LoginDatos } from '../modelos/logindatos';

const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class PuntuacionService {
  // DESARROLLO IP
  baseURL = 'http://localhost:8081/api/puntuacion/';
  // PRODUCCION IP
  //baseURL = 'http://localhost:8080/api/instrumento/';
 
//  instrumentoURL = 'http://ec2-54-224-216-200.compute-1.amazonaws.com:9000/api/instrumento/';

  constructor(private httpClient: HttpClient) { }

  public crear(ld : LoginDatos, puntuacion: Puntuacion): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(ld));
    formdata.append('puntuacion', JSON.stringify(puntuacion));
    return this.httpClient.post<any>(this.baseURL + 'nuevo', formdata);
  }

  

  
/*
  public editar(instrumento: Instrumento, id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseURL + `actualizar/${id}`, instrumento, cabecera);
  }

  public borrar(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.baseURL + `borrar/${id}`, cabecera);
  }*/
}
