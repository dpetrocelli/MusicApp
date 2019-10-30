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

  public obtenerTodos(): Genero[] {

    let generos: Genero[] = [];

    let masculino: Genero = new Genero();
    let femenino: Genero = new Genero();

    masculino.id = 1;
    masculino.nombreGenero = "Masculino";
    femenino.id = 2;
    femenino.nombreGenero = "Femenino";

    generos.push(masculino);
    generos.push(femenino);

    return generos;
  }

  public obtenerPorNombre(nombreGenero: String): Genero {

    let generos: Genero[] = this.obtenerTodos();
    let genero: Genero;
    let generoEncontrado: Genero;
    let found: Boolean;

    if (generos.length > 0) {
      for (let i = 0; i < generos.length; i++) {
        genero = generos[i];
        if ((genero.nombreGenero.length == nombreGenero.length) && (genero.nombreGenero.includes(nombreGenero.valueOf()))) {
          found = true;
          generoEncontrado = genero;
        }
        if (found) {
          return genero;
        }
      }

    }
  }
}
