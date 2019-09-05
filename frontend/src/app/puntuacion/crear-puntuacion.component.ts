import { Component, OnInit } from '@angular/core';
import { PerfilService } from 'src/app/servicios/perfil.service';
import { PuntuacionService } from 'src/app/servicios/puntuacion.service';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { Router } from '@angular/router';
import { Usuario } from '../modelos/usuario';
import { LoginDatos } from '../modelos/logindatos';
import { Puntuacion } from '../modelos/puntuacion';

@Component({
  selector: 'app-crear-puntuacion',
  templateUrl: './crear-puntuacion.component.html',
  styleUrls: ['./crear-puntuacion.component.css']
})
export class CrearPuntuacionComponent implements OnInit {
  userLogged : LoginDatos;
  puntuacion : Puntuacion;
  form: any = {};
  constructor(private usuarioService: UsuarioService,
              private router: Router,
              private puntuacionService : PuntuacionService) {}

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
   
    this.puntuacion = this.form;
    this.puntuacionService.crear(this.userLogged, this.puntuacion).subscribe(data => {
      
      console.log ("[APP-PUNT] -> puntuacion publicada");
      
    },
    (err: any) => {
      console.log( err );
      //this.router.navigate(['/accesodenegado']);
    });
  }

}
