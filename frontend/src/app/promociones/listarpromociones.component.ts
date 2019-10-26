import { Component, OnInit } from '@angular/core';
import { Promocion } from '../modelos/promocion';
import { PromocionService } from '../servicios/promocion.service';
import { UsuarioService } from '../servicios/usuario.service';
import { LoginDatos } from '../modelos/logindatos';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listarpromociones',
  templateUrl: './listarpromociones.component.html',
  styleUrls: ['./listarpromociones.component.css']
})
export class ListarpromocionesComponent implements OnInit {
  promociones : Promocion[] = [];
  userLogged : LoginDatos;
  
  constructor(private promocionService: PromocionService, 
              private usuarioService: UsuarioService,
              private router: Router) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.cargarpromociones();
  }

  cargarpromociones(): void {
    this.promocionService.lista(this.userLogged).subscribe(data => {
      
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

  onDelete(promocion: Promocion): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "La eliminacion es permanente !",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'eliminar!',
      background: 'url(./assets/img/guitar_music_strings_musical_instrument_111863_1920x1080.jpg)'
    }).then((confirmado) => {
      if (confirmado.value) {

        this.promocionService.borrar(promocion, this.userLogged).subscribe(data => {
          this.cargarpromociones();
        });
  
        Swal.fire(
          'Eliminado!',
          '.',
          'success'
        );
      }
    });
  }
}
