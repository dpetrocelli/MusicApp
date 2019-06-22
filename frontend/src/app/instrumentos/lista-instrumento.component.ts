import { Component, OnInit } from '@angular/core';
import { Instrumento } from '../modelos/instrumento';
import { InstrumentoService } from '../servicios/instrumento.service';

@Component({
  selector: 'app-lista-instrumento',
  templateUrl: './lista-instrumento.component.html',
  styleUrls: ['./lista-instrumento.component.css']
})
export class ListaInstrumentoComponent implements OnInit {

  instrumentos: Instrumento[] = [];

  constructor(private instrumentoService: InstrumentoService) { }

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

}
