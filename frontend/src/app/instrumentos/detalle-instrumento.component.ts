import { Component, OnInit } from '@angular/core';
import {Instrumento} from '../modelos/instrumento';
import {InstrumentoService} from '../servicios/instrumento.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-detalle-instrumento',
  templateUrl: './detalle-instrumento.component.html',
  styleUrls: ['./detalle-instrumento.component.css']
})
export class DetalleInstrumentoComponent implements OnInit {

  instrumento: Instrumento = null;

  constructor(private instrumentoService: InstrumentoService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    const id = this.activatedRoute.snapshot.params.id;
    this.instrumentoService.detalle(id).subscribe(data => {
        this.instrumento = data;
      },
      err => {
        this.router.navigate(['']);
      }
    );
  }

  volver(): void {
    window.history.back();
  }

}
