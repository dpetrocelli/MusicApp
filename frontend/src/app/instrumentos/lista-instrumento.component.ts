import { Component, OnInit } from '@angular/core';
import { Instrumento } from '../modelos/instrumento';
import { InstrumentoService } from '../servicios/instrumento.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-lista-instrumento',
  templateUrl: './lista-instrumento.component.html',
  styleUrls: ['./lista-instrumento.component.css']
})
export class ListaInstrumentoComponent implements OnInit {

  instrumentos: Instrumento[] = [];

  constructor(private instrumentoService: InstrumentoService, private router: Router) { }

  ngOnInit() {
    this.cargarInstrumentos();
  }

  cargarInstrumentos(): void {
    this.instrumentoService.lista().subscribe(data => {
      this.instrumentos = data;
    },
      (err: any) => {
        console.log(err);
      });
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

        this.instrumentoService.borrar(id).subscribe(data => {
          this.router.navigate(['/instrumento/'])
          this.cargarInstrumentos();
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