import { Component, OnInit } from '@angular/core';
import { PerfilService } from '../servicios/perfil.service';
import { PuntuacionService } from 'src/app/servicios/puntuacion.service';
import { BandaService } from 'src/app/servicios/banda.service';
import { UsuarioService } from '../servicios/usuario.service';
import { Router } from '@angular/router';
import { Usuario } from '../modelos/usuario';
import { LoginDatos } from '../modelos/logindatos';
import { PuntuacionArtista } from '../modelos/puntuacion';
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

  form: any = {};
  constructor(private usuarioService: UsuarioService,
              private router: Router,
              private puntuacionService : PuntuacionService,
              private bandaService : BandaService) {}

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();

    //this.loadUsuarios();
    //this.loadBanda();


  };

  async loadUsuarios (){
    this.listaDeNombres = await this.usuarioService.obtenerTodos(this.userLogged).toPromise();
        /*

      if (this.listaDeArtistas.length>0){
        this.listaDeArtistas.forEach(artista => {
          console.log (artista.usuario.username);
          this.listaDeNombres.push(artista.usuario.username);
        });
        console.log ("[APP-PUNT] -> trajo usuarios ", this.listaDeNombres);
      }

      */

  }

  async loadBanda(){
    let intermediate : string[] = await this.bandaService.obtenerTodos(this.userLogged).toPromise();
     //this.listaDeNombres.join(intermediate);


  }

  onCreate(){

    let usuarioPuntuado = this.form.artista;
    let comentario = this.form.comentario;
    let puntuacion = this.form.puntuacion;
    this.puntuacionService.crear(this.userLogged, usuarioPuntuado, comentario, puntuacion).subscribe(data => {

      console.log ("[APP-PUNT] -> puntuacion publicada");

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
