import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginDatos } from '../modelos/logindatos';
import { Genero } from '../modelos/genero';

@Injectable({
  providedIn: 'root'
})
export class GeneroService {

  constructor(private httpClient: HttpClient) { }

  public obtenerTodos () : Genero[] {

    var generos: Genero[] = [];

    var masculino: Genero = new Genero();
    var femenino: Genero = new Genero();

    masculino.id = 1;
    masculino.nombreGenero = "Masculino";
    femenino.id = 2;
    femenino.nombreGenero = "Femenino";

    generos.push(masculino);
    generos.push(femenino);

    return generos;
}
}
