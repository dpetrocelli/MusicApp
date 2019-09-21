import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { LoginDatos } from '../modelos/logindatos';
import { Observable } from 'rxjs';
import { Post } from '../modelos/post';



const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class PerfilService {

  // DESARROLLO URL
  baseURL = 'http://localhost:8081/api/post/';

  // PRODUCCION URL
  //baseURL = 'http://ec2-3-93-69-45.compute-1.amazonaws.com:9000/api/post/';

  constructor(private httpClient: HttpClient) { 

      
  }

  public buscarimagenperfil (login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'obtenerImagenPerfil',login, cabecera);
  }

  public buscarimagenperfilRedSocial (login : LoginDatos, nombre : string): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('nombre', nombre);
    return this.httpClient.post<any>(this.baseURL + 'RedSocialObtenerImagenPerfil', formdata);
  }

  public subirImagenPerfil (imagenParaSubir: File, login : LoginDatos): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('file', imagenParaSubir);
    formdata.append('login', JSON.stringify(login));
    return this.httpClient.post<any>(this.baseURL + 'subirImagenPerfil', formdata);
  }

  
  public obtenerbiografia (login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'obtenerBiografia',login, cabecera);
  }
  
   public obtenerbiografiaRedSocial (login : LoginDatos, nombre : string): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('nombre', nombre);
    return this.httpClient.post<any>(this.baseURL + 'RedSocialObtenerBiografia', formdata);
  }
  
  public existebiografia (login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'existeBiografia',login, cabecera);
  }

  public obtenerposts (login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'obtenerpostsporusuario',login, cabecera);
  }

  public obtenerpostsRedSocial (login : LoginDatos, nombre : string): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('nombre', nombre);
    return this.httpClient.post<any>(this.baseURL + 'RedSocialObtenerPost',formdata);
  }
  public crearpost (login : LoginDatos, post : Post ): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'crearPost',{login : login, post : post}, cabecera);
  }

  public actualizarbiografia (login : LoginDatos, biografia : String ): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'actualizarBiografia',{login : login, biografia : biografia}, cabecera);
  }

  public lista(login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'listar',login, cabecera);
  }

  public obtenerelementos(idpost: number): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'obtenerElementos', idpost, cabecera);
  }

  public enviarimagen (imagenParaSubir: File, id: string, login : LoginDatos): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('file', imagenParaSubir);
    formdata.append('id', id);
    formdata.append('login',JSON.stringify(login) )
    
    return this.httpClient.post<any>(this.baseURL + 'subirimagen', formdata);
		//return this.http.post(this.url_servidor, formData);
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
