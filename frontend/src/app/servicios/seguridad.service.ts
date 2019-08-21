import { Injectable } from '@angular/core';
import { CanActivate, RouterStateSnapshot, ActivatedRouteSnapshot, Router } from '@angular/router';
import { UsuarioService } from './usuario.service';
import { LoginDatos } from '../modelos/logindatos';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class SeguridadService implements CanActivate {

  realRol: string;
  encontrado : boolean;
  loginDatos : LoginDatos;
  expectedRol : String[]; 
  primerboolean : boolean;
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise <boolean> {
    this.expectedRol = route.data.expectedRol;
    this.loginDatos = this.usuarioService.getUserLoggedIn();
    this.encontrado = false;
    
    return new Promise((resolve) => {
      this.usuarioService.chequearPermisosPorRol(this.loginDatos).subscribe(
        data =>{
          console.log (data.mensaje);
         
          this.expectedRol.forEach(element => {
            if (element.trim() == data.mensaje){
              this.encontrado = true;
              console.log (" EXPECTED ROL = ROL DADO")
              resolve(true);
            }
          });
          if (!this.encontrado){
            this.router.navigate(['/accesodenegado']);
            resolve(false);
          }
        },
        (err: any) => {
          console.log("Sali por error: ",err.mensaje);
          
          resolve(false);
          this.router.navigate(['/accesodenegado']);
        }
      );

  }) && new Promise((resolve) => {
  this.usuarioService.validar(this.loginDatos).subscribe( data =>{
    if (data) {
      console.log ( " VALIDAMOS TOKEN")
      resolve(true);
    } else {
             
      console.log (" NO SE PUDO VALIDAR TOKEN");
       resolve(false);
       this.router.navigate(['/accesodenegado']);
   }
  });
});


    
    
    
  }



  constructor(private usuarioService: UsuarioService, private router: Router) { }
}