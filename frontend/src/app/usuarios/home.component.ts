import { Component, OnInit } from '@angular/core';
import {LoginDatos} from '../modelos/logindatos';
import {UsuarioService} from '../servicios/usuario.service';
import {ActivatedRoute, Router} from '@angular/router';
import { FormGroup } from '@angular/forms';
import { userInfo } from 'os';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  form: any = {};
  userLogged : LoginDatos;
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
              private appComponent: AppComponent,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    
    this.userLogged = this.usuarioService.getUserLoggedIn();
   
    // Casos posibles
    // C1 - Si es primera vez -> es decir null -> tiene que loguear
    if(this.userLogged === null){
      this.loginDialog = true;
      this.registryDialog = false;
      this.logOff = false;
    }else{ 
      // C2y3 - Tengo una sesión activa y tengo que revisar si esta vig
      
      this.usuarioService.validar(this.userLogged).subscribe(data => {
        //this.msjOK = data.msg ;
       
        if (data){
          
          this.loginDialog = false;
          this.registryDialog = false;
          this.logOff = true;
          
        }else{
       
          this.loginDialog = true;
          this.registryDialog = false;
          this.logOff = false;
        }
      },
        (err: any) => {
          console.log (" ALGUN ERROR "+err.error);
          this.msjFallo = err.error.mensaje;
          this.loginDialog = true;
          this.registryDialog = false;
          this.logOff = false;
        }
  
      );
      
    }
  }

  
/*
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
*/
  // función login
  ingresar() {
      
    this.usuarioService.ingresar(this.form).subscribe(data => {
      //this.msjOK = data.msg ;
      this.conectado = true;
      this.errorConexion = false;
      console.log(data);

      //Voy a guardar el usuario en LocalStorage
      this.userLogged  = data;
      //Guardando...
      this.usuarioService.setUserLoggedIn(this.userLogged);
      this.logOff = true;
      this.loginDialog = false;
      this.userLogged.nombreUsuario = this.userLogged.nombreUsuario;
      this.appComponent.reloadInfo();
    },
      (err: any) => {
        console.log(err.error);
        this.msjFallo = err.error;
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
  
   
}
