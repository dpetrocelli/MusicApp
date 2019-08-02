import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Marketplace} from '../modelos/marketplace';

const cabecera = {headers: new HttpHeaders({'Content-TYpe': 'application/json'})};

@Injectable({
  providedIn: 'root'
})

export class MarketplaceService {
  /* Tengo que tener la URL de la aplicación a la cual le voy a pegar
    En este caso recordar que va a ser /api/marketplace
   */
  baseURL = 'localhost:8080/api/marketplace/';

  constructor(private httpClient: HttpClient) { }

  public obtener(): Observable <Marketplace> {
    return this.httpClient.get<Marketplace>(this.baseURL + '/obtener', cabecera);
  }
}
