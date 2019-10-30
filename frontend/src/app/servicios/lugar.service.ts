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

  public listarLosMios(login : LoginDatos): Observable<Lugar[]> {
    return this.httpClient.get<Lugar[]>(this.baseURL + `listarLosMios/${login.idUsuario}`, cabecera);
  }
  
  public detalle(id: number): Observable<Lugar> {
    return this.httpClient.get<Lugar>(this.baseURL + `detalle/${id}`, cabecera);
  }

  public crear(login : LoginDatos, Lugar: Lugar): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('lugar', JSON.stringify (Lugar) );
    
    return this.httpClient.post<any>(this.baseURL + 'nuevo', formdata);
  }

  public editar(Lugar: Lugar, id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseURL + `actualizar/${id}`, Lugar, cabecera);
  }

  public borrar(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.baseURL + `borrar/${id}`, cabecera);
  }

  public buscar (login : LoginDatos, zona: string, textolibre: string): Observable<any> {
    
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('busqueda', String (textolibre) );
    formdata.append('zona', String (zona) );
    
    return this.httpClient.post<any>(this.baseURL+"/buscarLike", formdata);
  }

  public buscarimagen (login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'obtenerImagen',login, cabecera);
  }


  public enviarimagen (imagenParaSubir: File, login : LoginDatos): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('file', imagenParaSubir);
    formdata.append('login',JSON.stringify(login) )
    console.log (" ESTAMOS ENVIANDO", formdata);
    return this.httpClient.post<any>(this.baseURL + 'subirImagenLugar', formdata);
	
  }
}
