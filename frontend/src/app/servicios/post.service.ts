import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { LoginDatos } from '../modelos/logindatos';
import { Observable } from 'rxjs';
import { Post } from '../modelos/post';



const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class PostService {
  baseURL = 'http://localhost:8081/api/post/';

  constructor(private httpClient: HttpClient) { 

      
  }

  public obtenerbiografia (login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'obtenerBiografia',login, cabecera);
  }

  public obtenerposts (login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'obtenerPosts',login, cabecera);
  }

  public crearpost (login : LoginDatos, post : Post ): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'actualizarBiografia',{login : login, post : post}, cabecera);
  }

  public actualizarbiografia (login : LoginDatos, biografia : String ): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'actualizarBiografia',{login : login, biografia : biografia}, cabecera);
  }

  public lista(login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'listar',login, cabecera);
  }
/*
  public detalle(id: number, ld :LoginDatos): Observable<Promocion> {
    return this.httpClient.post<Promocion>(this.baseURL + `detalle/${id}`,ld, cabecera);
  }

  public crear(promocion: Promocion, login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'nuevo', {promocion, login}, cabecera);
  }
*/
}
