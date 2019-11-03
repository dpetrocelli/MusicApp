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

public datosBanda (login : LoginDatos) : Observable <any> {
  return this.httpClient.post<any>(this.baseURL + 'datosBanda', login, cabecera);
}

public obtenerBandaPorNombre (login : LoginDatos, nombreBanda: string) : Observable <any> {
  var formdata: FormData = new FormData();
  formdata.append('login', JSON.stringify(login));
  formdata.append('nombreBanda', JSON.stringify(nombreBanda));
  return this.httpClient.post<any>(this.baseURL + 'obtenerBandaPorNombre', formdata);
}

public datosBandaUna(login : LoginDatos) : Observable <any> {
  return this.httpClient.post<any>(this.baseURL + 'datosBandaUna', login, cabecera);
}
public obtenerArtistasDeBanda (ld : LoginDatos, artista: Artista) : Observable <any> {
  var formdata: FormData = new FormData();
  formdata.append('login', JSON.stringify(ld));
  formdata.append('artista', JSON.stringify(artista) );
  
  return this.httpClient.post<any>(this.baseURL + 'obtenerArtistasDeBanda', formdata);
}


public borrarBanda (ld : LoginDatos, banda: Banda) : Observable <any> {

  var formdata: FormData = new FormData();
  formdata.append('login', JSON.stringify(ld));
  formdata.append('banda',JSON.stringify(banda));

  return this.httpClient.post<any>(this.baseURL + 'borrarBandaTotal', formdata);
}



public buscarArtistas (ld : LoginDatos, banda: Banda) : Observable <any> {
  var formdata: FormData = new FormData();
  formdata.append('login', JSON.stringify(ld));
  formdata.append('banda', JSON.stringify(banda) );
  
  return this.httpClient.post<any>(this.baseURL + 'buscarArtistas', formdata);
}

public detalle(id: number): Observable<Banda> {
  return this.httpClient.get<Banda>(this.baseURL + `detalle/${id}`, cabecera);
}


public editar(banda: Banda, id: number): Observable<any> {
  return this.httpClient.put<any>(this.baseURL + `actualizar/${id}`, banda, cabecera);
}

  public SoyDuenioBanda (ld : LoginDatos, artista: Artista) : Observable <any> {
    var formdata: FormData = new FormData();
    formdata.append('login', JSON.stringify(ld));
    formdata.append('artista', JSON.stringify(artista) );
    return this.httpClient.post<any>(this.baseURL + 'soyDuenioBanda', formdata);
    
}

public SoyDuenioBandaLogin (ld : LoginDatos) : Observable <any> {
  var formdata: FormData = new FormData();
  formdata.append('login', JSON.stringify(ld));
  
  return this.httpClient.post<any>(this.baseURL + 'soyDuenioBandaLogin', formdata);
  
}


public eliminarArtistaDeBanda (ld : LoginDatos, banda: Banda, artista: Artista) : Observable <any> {
  var formdata: FormData = new FormData();
  formdata.append('login', JSON.stringify(ld));
  formdata.append('banda', JSON.stringify(banda) );
  formdata.append('artista', JSON.stringify(artista) );
  return this.httpClient.post<any>(this.baseURL + 'eliminarArtistaDeBanda', formdata);
  
}
  
public crear(login : LoginDatos, banda: Banda): Observable<any> {
  var formdata: FormData = new FormData();
  formdata.append('login', JSON.stringify(login));
  formdata.append('banda', JSON.stringify (banda) );
  
  return this.httpClient.post<any>(this.baseURL + 'nueva', formdata);
}
  
}

