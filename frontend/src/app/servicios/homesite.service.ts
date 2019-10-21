import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Instrumento } from '../modelos/instrumento';
import { LoginDatos } from '../modelos/logindatos';
import { environment } from '../../environments/environment';

const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class HomeSiteService {

  baseURL = environment.urlApiBackend + 'api/post/';
  alternativeURL = environment.urlApiBackend+"api/";
  constructor(private httpClient: HttpClient) { }

  public obtener(login : LoginDatos, inicio : number, fin : number): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('inicio', String (inicio) );
    formdata.append('fin', String(fin) )

    return this.httpClient.post<any>(this.baseURL + 'obtenerHomeSite', formdata);
  }


  public buscar (login : LoginDatos, opcion : String, textolibre: string, zona: string, instrumento: string, genero: string): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('opcion', String (opcion) );
    formdata.append('busqueda', String (textolibre) );
    formdata.append('zona', String (zona) );
    formdata.append('instrumento', String (instrumento) );
    formdata.append('genero', String (genero) );
    return this.httpClient.post<any>(this.alternativeURL + opcion+"/buscarLike", formdata);
  }
  
}
