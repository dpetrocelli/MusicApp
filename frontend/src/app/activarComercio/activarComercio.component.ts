import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../servicios/usuario.service';
import { Usuario } from '../modelos/usuario';
import { MarketplaceService } from '../servicios/marketplace.service';
import { Mensaje } from '../modelos/mensaje';
import { LoginDatos } from '../modelos/logindatos';
import { Router } from '@angular/router';

@Component({
  selector: 'app-activarComercio',
  templateUrl: './activarComercio.component.html',
  styleUrls: ['./activarComercio.component.css']
})
export class ActivarComercioComponent implements OnInit {
  loginDatos: LoginDatos = new LoginDatos();
  msg: Mensaje;
  unauthorized : boolean;

  constructor(private usuarioService: UsuarioService,
              private marketplaceService: MarketplaceService,
              private router: Router) { }

  ngOnInit() {
    this.loginDatos = this.usuarioService.getUserLoggedIn();
    //this.unauthorized = 
    //this.chequearPermisos();
    
    //if(!this.unauthorized){
    //  this.router.navigate(['/accesodenegado']);
    //}
  }

  armarenlace(): void {
    
    this.marketplaceService.armarLink(this.loginDatos.idUsuario).subscribe(data => {
        this.msg = data;
        var win = window.open(this.msg.mensaje, '_blank');
        win.focus();
        setTimeout(function() {
            win.close();
            window.location.href= "";
          }, 10000);
      },
      (err: any) => {
        console.log(err.error.mensaje);
        this.router.navigate(['/accesodenegado']);
      }
    );
  }

  chequearPermisos() {
    this.usuarioService.chequearPermisosPorSubsite(this.loginDatos, 'activarcomercio').subscribe(
      data =>{
        console.log("Sali todo bien: ",data.mensaje);
        return false;
      },
      (err: any) => {
        console.log("Sali por error: ",err.mensaje);
        this.router.navigate(['/accesodenegado']);
        return true;
      }
    );
  }

  volver(): void {
    window.history.back();
  }

}
