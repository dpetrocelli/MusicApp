import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../modelos/usuario';
import { LoginDatos } from '../modelos/logindatos';
import { Mensaje } from '../modelos/mensaje';
import { environment } from '../../environments/environment';
import { Artista } from '../modelos/artista';
import { Banda } from '../modelos/banda';

const cabecera = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class NotificacionService {

  baseURL = environment.urlApiBackend + 'api/notificacionesBandaUsuario/';
  urlUsuario = environment.urlApiBackend + 'api/notificacionesBandaUsuario/';

  private isUserLoggedIn = false;
  usuarioFrontEnd: Usuario = new Usuario();
  loginDatos: LoginDatos = new LoginDatos();
  valueByGet : String;

  constructor(private httpClient: HttpClient) { }

  public obtener (ld : LoginDatos) : Observable <any> {
    return this.httpClient.post<any>(this.baseURL + 'obtener', ld, cabecera);
  }

  public eliminar (login: LoginDatos, id: number) : Observable <any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('id', String (id));
    return this.httpClient.post<any>(this.baseURL + 'eliminar', formdata);
  }

  public actualizarNotificacion (login: LoginDatos, msgRespuesta: string, id: string) : Observable <any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('msg', msgRespuesta);
    formdata.append('id', id);
    return this.httpClient.post<any>(this.baseURL + 'actualizar', formdata);
  }
  
  public descartarNotificacion (login: LoginDatos, nombreOrigen: string, nombreDestino: string, id: string) : Observable <any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('nombreOrigen', nombreOrigen);
    formdata.append('nombreDestino', nombreDestino);
    formdata.append('id', id);
    return this.httpClient.post<any>(this.baseURL + 'descartar', formdata);
  }
  
  public incluirABanda (login: LoginDatos, nombreOrigen: string, nombreDestino: string, id: string, moderacion: string) : Observable <any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('nombreOrigen', nombreOrigen);
    formdata.append('nombreDestino', nombreDestino);
    formdata.append('id', id);
    formdata.append('moderacion', moderacion);
    return this.httpClient.post<any>(this.baseURL + 'incluirABanda', formdata);
  }

  public nuevoMensajeNotificacion (login: LoginDatos, msg: string, artista : Artista, tipomsg : string) : Observable <any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(login));
    formdata.append('msg', msg);
    formdata.append('artista', JSON.stringify(artista));
    formdata.append('tipomsg', tipomsg);
    return this.httpClient.post<any>(this.urlUsuario + 'nuevoMensaje', formdata);
  }

  
  
}

