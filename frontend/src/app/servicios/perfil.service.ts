import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { LoginDatos } from '../modelos/logindatos';
import { Observable } from 'rxjs';
import { Post } from '../modelos/post';
import { environment } from '../../environments/environment';

const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class PerfilService {

  baseURL = environment.urlApiBackend + 'api/post/';

  constructor(private httpClient: HttpClient) { 
      
  }

  public buscarimagenperfil (login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'obtenerImagenPerfil',login, cabecera);
  }


  public buscarimagenperfilBanda (login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'obtenerImagenPerfilBanda',login, cabecera);
  }

  public buscarimagenperfilRedSocialBanda (login : LoginDatos, nombre : string): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('nombre', nombre);
    return this.httpClient.post<any>(this.baseURL + 'RedSocialObtenerImagenPerfilBanda', formdata);
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

  public subirImagenPerfilBanda (imagenParaSubir: File, login : LoginDatos): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('file', imagenParaSubir);
    formdata.append('login', JSON.stringify(login));
    return this.httpClient.post<any>(this.baseURL + 'subirImagenPerfilBanda', formdata);
  }
  
  public obtenerbiografia (login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'obtenerBiografia',login, cabecera);
  }

  public obtenerbiografiaBanda (login : LoginDatos): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'obtenerBiografiaBanda',login, cabecera);
  }
  
   public obtenerbiografiaRedSocial (login : LoginDatos, nombre : string): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('nombre', nombre);
    return this.httpClient.post<any>(this.baseURL + 'RedSocialObtenerBiografia', formdata);
  }
  
  public obtenerbiografiaRedSocialBanda (login : LoginDatos, nombre : string): Observable<any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('nombre', nombre);
    return this.httpClient.post<any>(this.baseURL + 'RedSocialObtenerBiografiaBanda', formdata);
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

  public borrarpost (login :LoginDatos, post: Post): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'borrarPost/', {login : login, post : post}, cabecera);
  }

  public actualizarbiografia (login : LoginDatos, biografia : String): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'actualizarBiografia',{login : login, biografia : biografia}, cabecera);
  }

  public actualizarbiografiaBanda (login : LoginDatos, biografia : String): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'actualizarBiografiaBanda',{login : login, biografia : biografia}, cabecera);
  }
  public actualizarbiografiaFacebook (login : LoginDatos, facebook : String): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'actualizarBiografiaFacebook',{login : login, facebook : facebook}, cabecera);
  }

  public actualizarbiografiaFacebookBanda (login : LoginDatos, facebook : String): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'actualizarBiografiaFacebookBanda',{login : login, facebook : facebook}, cabecera);
  }

  public actualizarbiografiaSpotify (login : LoginDatos, spotify : String): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'actualizarBiografiaSpotify',{login : login, spotify : spotify}, cabecera);
  }

  public actualizarbiografiaSpotifyBanda (login : LoginDatos, spotify : String): Observable<any> {
    return this.httpClient.post<any>(this.baseURL + 'actualizarBiografiaSpotifyBanda',{login : login, spotify : spotify}, cabecera);
  }
/*
public actualizarbiografia (login : LoginDatos, biografia : String): Observable<any> {
  return this.httpClient.post<any>(this.baseURL + 'actualizarBiografia',{login : login, biografia : biografia}, cabecera);
}*/

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
