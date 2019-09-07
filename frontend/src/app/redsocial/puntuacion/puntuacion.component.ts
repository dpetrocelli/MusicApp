import { Component, OnInit } from '@angular/core';
import { LoginDatos } from '../../modelos/logindatos';
import { PuntuacionArtista } from '../../modelos/puntuacion';
import { PuntuacionService } from 'src/app/servicios/puntuacion.service';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { Router } from '@angular/router';
import { findSafariExecutable } from 'selenium-webdriver/safari';

@Component({
  selector: 'app-puntuacion',
  templateUrl: './puntuacion.component.html',
  styleUrls: ['./puntuacion.component.css']
})
export class PuntuacionComponent implements OnInit {
  userLogged : LoginDatos;
  listaPuntuacion : PuntuacionArtista[] = [];
  promedio : number = 0;
  contador : number = 0;
  isReady : boolean = false;

  constructor(private usuarioService: UsuarioService,
              private router: Router,
              private puntuacionService : PuntuacionService,) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.puntuacionService.obtenerPuntuacion(this.userLogged).subscribe(data => {
      console.log (data);
      this.listaPuntuacion = data;

      this.listaPuntuacion.forEach(puntuacion => {
        this.promedio+=puntuacion.puntuacion;
        this.contador+=1;
      });
      this.promedio = this.promedio / this.contador;
      console.log (" PROM PUNT : "+this.promedio);
      this.isReady = true;
      //| limitTo : 3
    },
    (err: any) => {
      console.log( err );
      //this.router.navigate(['/accesodenegado']);
    });
  }
  

}
