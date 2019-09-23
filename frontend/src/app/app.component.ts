import { Component, OnInit } from '@angular/core';
import { UsuarioService } from './servicios/usuario.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginDatos } from './modelos/logindatos';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  
  title = 'frontend';
  isArtista = false;
  isComercio = false;
  isComercioVinculadoMP = false;
  isAdmin = false;
  isLoggedIn = false;
  userLogged : LoginDatos;
  notificacionesCargadas = false;
  items : any[];

  constructor(private usuarioService: UsuarioService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }
              
  ngOnInit(): void {
    this.items = [
      {
        id: 19,
        cliente: 'El gato volador',
        total: 1000
      },
      {
        id: 212,
        cliente: 'servicios industriales del reino del muy pero muy lejano sa de cv',
        total: 78456
      },
      {
        id: 312,
        cliente: 'Cliente nuevo',
        total: 10000
      },
      {
        id: 5000,
        cliente: 'Eduardo Cespedes Carrera',
        total: 100000
      }
    ];
    this.reloadInfo();
    
  }

  
  reloadInfo(): void{
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.isLoggedIn = true;
    
    if (this.userLogged!= null){
      if (this.userLogged.roles == "comercio"){
        this.isComercio = true;
        this.verificarComercioActivado();
      }else {
        if (this.userLogged.roles == "admin"){
          this.isAdmin = true;
        }else {
          if (this.userLogged.roles = "artista"){
            this.isArtista = true;
          }
        }
      }
    }
  }


  mostrarNotificaciones(){
    this.notificacionesCargadas = true;
  }
  verificarComercioActivado() {
    
    this.usuarioService.verificarComercioActivado(this.userLogged).subscribe(data => {
      this.isComercioVinculadoMP = data;
    },
    (err: any) => {
      console.log(err.error.mensaje);
      
    }
  );  
    
  }

  cerrarsesion(){
    localStorage.clear();
    this.userLogged = null;
    this.isArtista = false;
    this.isComercio = false;
    this.isAdmin = false;
    
    this.router.navigate(['']);
   setTimeout(() => 
    {
      window.location.reload();
    },
    30);
    
    
  }
  
  
}
