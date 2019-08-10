import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../servicios/usuario.service';
import { Usuario } from '../modelos/usuario';
import { MarketplaceService } from '../servicios/marketplace.service';
import { Mensaje } from '../modelos/mensaje';

@Component({
  selector: 'app-activarComercio',
  templateUrl: './activarComercio.component.html',
  styleUrls: ['./activarComercio.component.css']
})
export class ActivarComercioComponent implements OnInit {
  usuario: Usuario = new Usuario();
  msg: Mensaje;

  constructor(private usuarioService: UsuarioService, private marketplaceService: MarketplaceService) { }

  ngOnInit() {
    this.usuario = this.usuarioService.getUserLoggedIn();
  }

  armarenlace(): void {
    
    this.marketplaceService.armarLink(this.usuario.id).subscribe(data => {
        
        this.msg = data;
        window.location.href = this.msg.mensaje;
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
