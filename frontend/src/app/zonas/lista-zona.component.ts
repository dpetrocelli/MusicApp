import { Component, OnInit } from '@angular/core';
import { Zona } from '../modelos/zona';
import { ZonaService } from '../servicios/zona.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-lista-zona',
  templateUrl: './lista-zona.component.html',
  styleUrls: ['./lista-zona.component.css']
})
export class ListaZonaComponent implements OnInit {

  zonas: Zona[] = [];

  constructor(private zonaService: ZonaService, private router: Router) { }

  ngOnInit() {
    this.cargarZonas();
  }
  cargarZonas() {
    this.zonaService.lista().subscribe(data => {
      this.zonas = data;
      console.log(data);
      console.log(this.zonas);
    },
    (err: any) => {
      console.log(err);
    })
  }

  onDelete(id: number): void {
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

        this.zonaService.borrar(id).subscribe(data => {
          this.router.navigate(['/zona/'])
          this.cargarZonas();
        },
        (err: any) => {
          console.log(err);
          Swal.fire(
            'Ocurrio un error!',
            '.',
            err
          );
        })

        Swal.fire(
          'Eliminado!',
          '.',
          'success'
        );
      }
    });
  }
}
