import { Component, OnInit } from '@angular/core';
import { LoginDatos } from '../../modelos/logindatos';
import { PuntuacionArtista } from '../../modelos/puntuacion';
import { PuntuacionService } from '../../servicios/puntuacion.service';
import { UsuarioService } from '../../servicios/usuario.service';
import { Router } from '@angular/router';
import { findSafariExecutable } from 'selenium-webdriver/safari';

@Component({
  selector: 'app-puntuacion-banda',
  templateUrl: './puntuacion-banda.component.html',
  styleUrls: ['./puntuacion-banda.component.css']
})
export class PuntuacionBandaComponent implements OnInit {
  userLogged : LoginDatos;
  listaPuntuacion : PuntuacionArtista[] = [];
  promedio : number = 0;
  contador : number = 0;
  isReady : boolean = false;

  constructor(private usuarioService: UsuarioService,
              private router: Router,
              private puntuacionService : PuntuacionService) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.obtenerPuntuacion();
    this.delay(3000);
    this.isReady = true;
    
  }

  async obtenerPuntuacion(){
    this.listaPuntuacion = await this.puntuacionService.obtenerPuntuacion(this.userLogged).toPromise();
    if (this.listaPuntuacion.length> 0){
      this.listaPuntuacion.forEach(puntuacion => {
        this.buscarUsuario(puntuacion);
        this.promedio+=puntuacion.puntuacion;
        this.contador+=1;
        //console.log (" OBJETO", puntuacion);
      });
      this.promedio = this.promedio / this.contador;
      //console.log (" PROM PUNT : "+this.promedio);
      
    }else{
      this.promedio = 0;
      
    }
    
  }
  async buscarUsuario (puntuacion : PuntuacionArtista){
    puntuacion.artistaLoaded = await this.usuarioService.obtenerDatosUsuarioPorId(this.userLogged, puntuacion.artistaPuntuador).toPromise();
    
  }

  hasResource (id : number){
    console.log(id);
    
  }

  async delay(ms: number) {
    return await new Promise( resolve => setTimeout(resolve, ms) );
  }
  

}