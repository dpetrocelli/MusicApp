import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Lugar } from '../modelos/lugar';
import { environment } from '../../environments/environment';
import { LoginDatos } from '../modelos/logindatos';

const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class LugarService {

  baseURL = environment.urlApiBackend + 'api/lugar/';
 
  constructor(private httpClient: HttpClient) { }

  public lista(): Observable<Lugar[]> {
    return this.httpClient.get<Lugar[]>(this.baseURL + 'listar', cabecera);
  }

  public detalle(id: number): Observable<Lugar> {
    return this.httpClient.get<Lugar>(this.baseURL + `detalle/${id}`, cabecera);
  }

  public crear(Lugar: Lugar): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'nuevo', Lugar, cabecera);
  }

  public editar(Lugar: Lugar, id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseURL + `actualizar/${id}`, Lugar, cabecera);
  }

  public borrar(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.baseURL + `borrar/${id}`, cabecera);
  }

  public buscar (login : LoginDatos, textolibre: string, zona: string): Observable<any> {
    
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('busqueda', String (textolibre) );
    formdata.append('zona', String (zona) );
    
    return this.httpClient.post<any>(this.baseURL+"/buscarLike", formdata);
  }
}
