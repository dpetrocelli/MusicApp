import { Component, OnInit } from '@angular/core';
import { Pago } from '../modelos/pago';
import { LoginDatos } from '../modelos/logindatos';
import { PagoService } from '../servicios/pago.service';
import { UsuarioService } from '../servicios/usuario.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-lista-pago',
  templateUrl: './lista-pago.component.html',
  styleUrls: ['./lista-pago.component.css']
})
export class ListaPagoComponent implements OnInit {

  pagos: Pago[] = [];
  userLogged : LoginDatos;

  constructor(
    private pagoService: PagoService,
    private usuarioService: UsuarioService,
    private router: Router) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.cargarPagos();
  }

  cargarPagos(): void {
    this.pagoService.lista(this.userLogged).subscribe(data => {
      
         data.forEach(pago => {
            pago.fechaPago = new Date(pago.fechaPago).toLocaleDateString('es-AR');
          });
          this.pagos = data;
      },
      (err: any) => {
        console.log(err);
        this.router.navigate(['/accesodenegado']);
      });
  }
}