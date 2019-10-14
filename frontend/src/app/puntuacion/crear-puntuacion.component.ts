import { Component, OnInit } from '@angular/core';
import { PerfilService } from '../servicios/perfil.service';
import { PuntuacionService } from 'src/app/servicios/puntuacion.service';
import { BandaService } from 'src/app/servicios/banda.service';
import { UsuarioService } from '../servicios/usuario.service';
import { Router, Route } from '@angular/router';
import { Usuario } from '../modelos/usuario';
import { LoginDatos } from '../modelos/logindatos';
import { PuntuacionArtista } from '../modelos/puntuacion';
import { ActivatedRoute } from '@angular/router';
import { Artista } from '../modelos/artista';

@Component({
  selector: 'app-crear-puntuacion',
  templateUrl: './crear-puntuacion.component.html',
  styleUrls: ['./crear-puntuacion.component.css']
})
export class CrearPuntuacionComponent implements OnInit {
  userLogged : LoginDatos;
  puntuacion : PuntuacionArtista;

  listaDeNombres : string[] = [];
  nombreUsuario : string;
  falloCreacion = false;
  creado = false;
  msjOK = "puntuación generada correctamente";
  msjFallo = '';
  form: any = {};
  constructor(private usuarioService: UsuarioService,
              private router: ActivatedRoute,
              private router2: Router,
              private puntuacionService : PuntuacionService,
              private bandaService : BandaService) {}

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.nombreUsuario = this.router.snapshot.paramMap.get("usuario");
    
   

  };

 

  onCreate(){

    //let usuarioPuntuado = this.form.artista;
    let comentario = this.form.comentario;
    let puntuacion = this.form.puntuacion;
    this.puntuacionService.crear(this.userLogged, this.nombreUsuario, comentario, puntuacion).subscribe(data => {
      this.creado = true;
      console.log ("[APP-PUNT] -> puntuacion publicada");
      setTimeout(() => {
        window.history.back();
      },2000);

    },
    (err: any) => {
      console.log( err );
      //this.router.navigate(['/accesodenegado']);
    });
  }

  volver(): void {
    window.history.back();
  }
}
