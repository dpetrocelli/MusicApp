import { Component, OnInit } from '@angular/core';
import {Usuario} from '../modelos/usuario';
import {UsuarioService} from '../servicios/usuario.service';
import {ActivatedRoute, Router} from '@angular/router';
import { FormGroup } from '@angular/forms';
import { userInfo } from 'os';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  form: any = {};
  userLogged : Usuario;
  loginDialog = true;
  registryDialog = false;
  logOff = false;
  // tipo user
  isArtista = true;

  // si pude pasar y loguear
  conectado = false;
  errorConexion = false;
  rol: Number;

  //si cree el usuario
  creado = false;
  falloCreacion = false;

  msjFallo = '';
  msjOK = '';
  constructor(private usuarioService: UsuarioService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    if(this.userLogged === null){
      this.loginDialog = true;
      this.registryDialog = false;
      this.logOff = false;
    }else{
      this.loginDialog = false;
      this.registryDialog = false;
      this.logOff = true;
    }
  }


  guardarUsuario() {
    this.usuarioService.registrar(this.form, this.isArtista).subscribe(data => {
      this.msjOK = data.msg ;
      this.creado = true;
      this.falloCreacion = false;
    },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        this.creado = false;
        this.falloCreacion = true;
      }

    );
  }

  // función login
  ingresar() {
      
    this.usuarioService.ingresar(this.form).subscribe(data => {
      this.msjOK = data.msg ;
      this.conectado = true;
      this.errorConexion = false;
      console.log(data);

      //Voy a guardar el usuario en LocalStorage
      this.userLogged  = data;
      //Guardando...
      this.usuarioService.setUserLoggedIn(this.userLogged);
      this.logOff = true;
      this.loginDialog = false;
      this.userLogged.username = data.username;
    },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        this.conectado = false;
        this.errorConexion = true;
      }

    );
  }

  cerrarsesion(){
    localStorage.setItem('currentUser', null);
    this.loginDialog = false;
    this.registryDialog = false;
    this.logOff = true;
    location.reload();
  }

  volver(): void {
    window.history.back();
  }

  // Método para habilitar una u otra opcion
  habilitarRegistrar(): void{
    this.loginDialog = false;
    this.registryDialog = true;
  }
   
}
