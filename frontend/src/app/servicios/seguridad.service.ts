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
              console.log (" SON IGUALES NOS CONOCEMOS")
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


    /*return new Promise((resolve) => {
      this.usuarioService.validar(this.loginDatos).subscribe( data =>{
          if (data) {
            // es validar token
            resolve(true);
            /*
            this.usuarioService.chequearPermisosPorRol(this.loginDatos).subscribe(
              data =>{
                console.log (data.mensaje);
               
                this.expectedRol.forEach(element => {
                  if (element.trim() == data.mensaje){
                    console.log (" SON IGUALES NOS CONOCEMOS")
                    resolve(true);
                  }
                });
                
              
                
              },
              (err: any) => {
                console.log("Sali por error: ",err.mensaje);
                
                //resolve(false);
                this.router.navigate(['/accesodenegado']);
              }
            );
              
              
          } else {
             
             console.log ("salimos por false");
              resolve(false);
              this.router.navigate(['/accesodenegado']);
          }
      });
  });

  */
    // 1ro valido si los datos del token están activos y no fueron falseados
    /*
  
    this.usuarioService.validar(this.loginDatos).subscribe(
      data =>{
        
        // 2do , voy a validar permisos del login datos (por si lo falsean)
        this.usuarioService.chequearPermisosPorRol(this.loginDatos).subscribe(
          data =>{
            console.log (data.mensaje);
           
            this.expectedRol.forEach(element => {
              if (element.trim() == data.mensaje){
                this.result = true;
                return this.result;
              }
            });
            console.log ( " HAY O NO "+this.result);
            
          },
          (err: any) => {
            console.log("Sali por error: ",err.mensaje);
            this.router.navigate(['/accesodenegado']);
            return this.result;
          }
        );
    
        
      },
      (err: any) => {
        console.log("Sali por error: ",err.mensaje);
        this.router.navigate(['/accesodenegado']);
        return this.result;
      }
    );
    
    
      */
    
    
  }



  constructor(private usuarioService: UsuarioService, private router: Router) { }
}