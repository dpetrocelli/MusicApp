import { Component, OnInit } from '@angular/core';
import { LoginDatos } from '../modelos/logindatos';
import { NotificacionMPService } from '../servicios/notificacion-mp.service';
import { UsuarioService } from '../servicios/usuario.service';
import { Router } from '@angular/router';
import { Notificacion } from '../modelos/notificacion';

@Component({
  selector: 'app-lista-notificacion',
  templateUrl: './lista-notificacion.component.html',
  styleUrls: ['./lista-notificacion.component.css']
})
export class ListaNotificacionComponent implements OnInit {

  notificacionesMP: Notificacion[] = [];
  userLogged : LoginDatos;

  constructor(
    private notificacionMPService: NotificacionMPService,
    private usuarioService: UsuarioService,
    private router: Router) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.cargarNotificaciones();
  }

  cargarNotificaciones(): void {
    this.notificacionMPService.lista(this.userLogged).subscribe(data => {
      
         data.forEach(notificacion => {
            notificacion.fechaPago = new Date(notificacion.fechaPago).toLocaleDateString('es-AR');
          });
          this.notificacionesMP = data;
      },
      (err: any) => {
        console.log(err);
        this.router.navigate(['/accesodenegado']);
      });
  }
}