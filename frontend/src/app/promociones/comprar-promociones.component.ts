import { Component, OnInit } from '@angular/core';
import { Promocion } from '../modelos/promocion';
import { PromocionService } from '../servicios/promocion.service';
import { UsuarioService } from '../servicios/usuario.service';
import { LoginDatos } from '../modelos/logindatos';
import { Router } from '@angular/router';
import { stringify } from '@angular/compiler/src/util';

@Component({
  selector: 'app-comprar-promociones',
  templateUrl: './comprar-promociones.component.html',
  styleUrls: ['./comprar-promociones.component.css']
})
export class ComprarPromocionesComponent implements OnInit {
  promociones : Promocion[] = [];
  userLogged : LoginDatos;
  
  constructor(private promocionService: PromocionService, 
              private usuarioService: UsuarioService,
              private router: Router) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.cargarPromocionesVigentes();
  }

  cargarPromocionesVigentes(): void {
    this.promocionService.obtenerVigentes(this.userLogged).subscribe(data => {
      
         data.forEach(element => {
            element.vigenciaStr = new Date(element.vigencia).toLocaleDateString('es-AR');
          });
          this.promociones = data;
       
      },
      (err: any) => {
        console.log(err);
        this.router.navigate(['/accesodenegado']);
      });
  }

  comprar(promocion: Promocion): void {

    var win = window.open(String(promocion.init_point_mercadopago), '_blank');
    win.focus();
    /*
    this.promocionService.comprar(promocion, this.userLogged).subscribe(data => {
        this.cargarPromocionesVigentes();
      });
    */
  }
}
