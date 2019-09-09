import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Instrumento } from '../modelos/instrumento';
import { PuntuacionArtista } from '../modelos/puntuacion';
import { LoginDatos } from '../modelos/logindatos';

const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class PuntuacionService {
  // DESARROLLO URL
  baseURL = 'http://localhost:8081/api/puntuacion/';

  // PRODUCCION URL
  //baseURL = 'http://ec2-3-93-69-45.compute-1.amazonaws.com:9000/api/puntuacion/';

  constructor(private httpClient: HttpClient) { }

  public crear(ld : LoginDatos, usuarioPuntuado : string, comentario : string, puntuacion: string): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(ld));
    formdata.append('usuarioPuntuado', usuarioPuntuado);
    formdata.append('comentario', comentario);
    formdata.append('puntuacion', puntuacion);
    return this.httpClient.post<any>(this.baseURL + 'nuevo', formdata);
  }

  public obtenerPuntuacion(login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'obtenerPuntuacion', login, cabecera);
  }

  
/*
  public editar(instrumento: Instrumento, id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseURL + `actualizar/${id}`, instrumento, cabecera);
  }

  public borrar(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.baseURL + `borrar/${id}`, cabecera);
  }*/
}
