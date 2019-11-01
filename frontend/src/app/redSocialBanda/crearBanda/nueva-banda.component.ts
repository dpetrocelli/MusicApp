import { Component, OnInit } from '@angular/core';
import { LoginDatos } from 'src/app/modelos/logindatos';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { BandaService } from 'src/app/servicios/banda.service';
import { Banda } from 'src/app/modelos/banda';
import { GeneroMusical } from 'src/app/modelos/generoMusical';
import { Zona } from 'src/app/modelos/zona';
import { ZonaService } from 'src/app/servicios/zona.service';
import { GeneroMusicalService } from 'src/app/servicios/generoMusical.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nueva-banda',
  templateUrl: './nueva-banda.component.html',
  styleUrls: ['./nueva-banda.component.css']
})
export class NuevaBandaComponent implements OnInit {
  userLogged : LoginDatos;
  form: any = {};
  banda : Banda;
  zonas : Zona[];
  generoMusicales : GeneroMusical[];
  creado = false;
  falloCreacion = false;
  msjFallo = '';
  msjOK = '';
  constructor(private router : Router, private usuarioService: UsuarioService,private bandaServicio: BandaService, private zonaService: ZonaService, private generoMusicalService : GeneroMusicalService) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.loadInfo();

    
  }

  async loadInfo(){
    this.zonas = await this.zonaService.lista().toPromise();
    this.generoMusicales = await this.generoMusicalService.lista().toPromise();
  }
  async onCreate() {
    this.banda = this.form;
    console.log ("BANDA ", this.banda);
    
    await this.bandaServicio.crear(this.userLogged, this.banda).toPromise();
      Swal.fire(
        'Banda Creada con éxito!',
        '',
        'success'
      )
      this.volver();
    
  }

  volver(): void {
    this.router.navigate(['']);
  }

}
