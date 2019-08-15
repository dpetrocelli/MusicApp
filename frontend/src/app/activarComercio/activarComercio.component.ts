import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../servicios/usuario.service';
import { Usuario } from '../modelos/usuario';
import { MarketplaceService } from '../servicios/marketplace.service';
import { Mensaje } from '../modelos/mensaje';
import { LoginDatos } from '../modelos/logindatos';

@Component({
  selector: 'app-activarComercio',
  templateUrl: './activarComercio.component.html',
  styleUrls: ['./activarComercio.component.css']
})
export class ActivarComercioComponent implements OnInit {
  loginDatos: LoginDatos = new LoginDatos();
  msg: Mensaje;

  constructor(private usuarioService: UsuarioService, private marketplaceService: MarketplaceService) { }

  ngOnInit() {
    this.loginDatos = this.usuarioService.getUserLoggedIn();
  }

  armarenlace(): void {
    
    this.marketplaceService.armarLink(this.loginDatos.idUsuario).subscribe(data => {
        
        this.msg = data;
        
        var win = window.open(this.msg.mensaje, '_blank');
        win.focus();
        setTimeout(function() {
            win.close();
            window.location.href= "";
          }, 5000);
          
        
      },
      (err: any) => {
        //this.msg = err.error.mensaje;
        
        
      }
    );
  }

  volver(): void {
    window.history.back();
  }

}
