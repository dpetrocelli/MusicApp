import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../modelos/usuario';
import { LoginDatos } from '../modelos/logindatos';
import { Mensaje } from '../modelos/mensaje';
import { environment } from '../../environments/environment';
import { Banda } from '../modelos/banda';
import { Artista } from '../modelos/artista';

const cabecera = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class BandaService {

  baseURL = environment.urlApiBackend + 'api/banda/';

  private isUserLoggedIn = false;
  usuarioFrontEnd: Usuario = new Usuario();
  loginDatos: LoginDatos = new LoginDatos();
  valueByGet : String;

  constructor(private httpClient: HttpClient) { }


  public obtenerTodos (ld : LoginDatos) : Observable <any> {
      return this.httpClient.post<any>(this.baseURL + 'obtenerTodos', ld, cabecera);
  }

  public obtenerDatosBanda (banda : Banda) : Observable <any> {
    return this.httpClient.post<any>(this.baseURL + 'obtenerDatosBanda', banda, cabecera);
}
public obtenerArtistasDeBanda (ld : LoginDatos, artista: Artista) : Observable <any> {
  var formdata: FormData = new FormData();
  formdata.append('login', JSON.stringify(ld));
  formdata.append('artista', JSON.stringify(artista) );
  
  return this.httpClient.post<any>(this.baseURL + 'obtenerArtistasDeBanda', formdata);
}

public buscarArtistas (ld : LoginDatos, banda: Banda) : Observable <any> {
  var formdata: FormData = new FormData();
  formdata.append('login', JSON.stringify(ld));
  formdata.append('banda', JSON.stringify(banda) );
  
  return this.httpClient.post<any>(this.baseURL + 'buscarArtistas', formdata);
}

  public SoyDuenioBanda (ld : LoginDatos, artista: Artista) : Observable <any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(ld));
    formdata.append('artista', JSON.stringify(artista) );
    return this.httpClient.post<any>(this.baseURL + 'soyDuenioBanda', formdata);
    
}

public eliminarArtistaDeBanda (ld : LoginDatos, banda: Banda, artista: Artista) : Observable <any> {
  var formdata: FormData = new FormData();
  formdata.append('login', JSON.stringify(ld));
  formdata.append('banda', JSON.stringify(banda) );
  formdata.append('artista', JSON.stringify(artista) );
  return this.httpClient.post<any>(this.baseURL + 'eliminarArtistaDeBanda', formdata);
  
}
  
  
  
}

