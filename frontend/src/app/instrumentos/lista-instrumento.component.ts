import { Component, OnInit } from '@angular/core';
import { Instrumento } from '../modelos/instrumento';
import { InstrumentoService } from '../servicios/instrumento.service';
import { Router } from '@angular/router';

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
    if (confirm('¿Estás seguro?')) {
      this.instrumentoService.borrar(id).subscribe(data => {
        this.router.navigate(['/instrumento/'])
        this.cargarInstrumentos();
        
      });
    }
  }
}
