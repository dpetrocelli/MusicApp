import { Component, OnInit } from '@angular/core';
import { GeneroMusical } from '../modelos/generoMusical';
import { GeneroMusicalService } from '../servicios/generoMusical.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-lista-genero-musical',
  templateUrl: './lista-genero-musical.component.html',
  styleUrls: ['./lista-genero-musical.component.css']
})
export class ListaGeneroMusicalComponent implements OnInit {

  generosMusicales: GeneroMusical[] = [];

  constructor(private generoMusicalService: GeneroMusicalService, private router: Router) { }

  ngOnInit() {
    this.cargarGenerosMusicales();
  }
  cargarGenerosMusicales() {
    this.generoMusicalService.lista().subscribe(data => {
      this.generosMusicales = data;
      console.log(data);
      console.log(this.generosMusicales);
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

        this.generoMusicalService.borrar(id).subscribe(data => {
          this.router.navigate(['/generoMusical/'])
          this.cargarGenerosMusicales();
        },
        (err: any) => {
          console.log(err);
          Swal.fire(
            'Ocurrio un error!',
            err,
            'error'
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
