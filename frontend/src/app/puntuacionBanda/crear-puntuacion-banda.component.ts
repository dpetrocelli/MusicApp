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
  selector: 'app-crear-puntuacionBanda',
  templateUrl: './crear-puntuacion-banda.component.html',
  styleUrls: ['./crear-puntuacion-banda.component.css']
})
export class CrearPuntuacionBandaComponent implements OnInit {
  userLogged : LoginDatos;
  puntuacion : PuntuacionArtista;

  listaDeNombres : string[] = [];
  nombreBanda : string;
  falloCreacion = false;
  creado = false;
  puntuacionValida = true;
  isValido = false;
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
    this.nombreBanda = this.router.snapshot.paramMap.get("banda");

  };

 
  verificarPuntuacion(){
    this.isValido = true;
    let number = new Number (this.form.puntuacion);
    if (number > 5 || number <1){
      this.puntuacionValida = false;
      this.isValido = false;
    }else{
      this.puntuacionValida = true;
      this.isValido = true;
    }
  }
  onCreate(){

    //let usuarioPuntuado = this.form.artista;
    let comentario = this.form.comentario;
    let puntuacion = this.form.puntuacion;
    this.puntuacionService.crearPuntuacionBanda(this.userLogged, this.nombreBanda, comentario, puntuacion).subscribe(data => {
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
