import { Component, OnInit } from '@angular/core';
import { PerfilService } from 'src/app/servicios/perfil.service';
import { PuntuacionService } from 'src/app/servicios/puntuacion.service';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { Router } from '@angular/router';
import { Usuario } from '../modelos/usuario';
import { LoginDatos } from '../modelos/logindatos';
import { Puntuacion } from '../modelos/puntuacion';
import { Artista } from '../modelos/artista';

@Component({
  selector: 'app-crear-puntuacion',
  templateUrl: './crear-puntuacion.component.html',
  styleUrls: ['./crear-puntuacion.component.css']
})
export class CrearPuntuacionComponent implements OnInit {
  userLogged : LoginDatos;
  puntuacion : Puntuacion;
  listaDeArtistas : Artista[];
  listaDeNombres : string[];

  form: any = {};
  constructor(private usuarioService: UsuarioService,
              private router: Router,
              private puntuacionService : PuntuacionService) {}

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();

    this.loadUsers();
    
  };

  loadUsers (){
    this.usuarioService.obtenerTodos(this.userLogged).subscribe(data => {
      this.listaDeArtistas = data;
      console.log ("[APP-PUNT] -> trajo usuarios ", this.listaDeArtistas);
      if (this.listaDeArtistas.length>0){
        this.listaDeArtistas.forEach(artista => {
          console.log (artista.usuario.username);
          
        });
        console.log ("[APP-PUNT] -> trajo usuarios ", this.listaDeNombres);
      }
    },
      (err: any) => {
       console.log ( " ERROR BROX ");
      }

    );
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

  onCreate(){
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
