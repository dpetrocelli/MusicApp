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
  userLogged : LoginDatos;

  constructor(private usuarioService: UsuarioService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }
              
  ngOnInit(): void {
    this.reloadInfo();
    
  }

  
  reloadInfo(): void{
    this.userLogged = this.usuarioService.getUserLoggedIn();
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

  verificarComercioActivado() {
    
    this.usuarioService.verificarComercioActivado(this.userLogged).subscribe(data => {
      this.isComercioVinculadoMP = true;
    },
    (err: any) => {
      console.log(err.error.mensaje);
      
    }
  );  
    
  }
  
}
