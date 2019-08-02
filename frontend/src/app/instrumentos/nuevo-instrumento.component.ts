import {Component, OnInit} from '@angular/core';
import {Instrumento} from '../modelos/instrumento';
import {InstrumentoService} from '../servicios/instrumento.service';

@Component({
  selector: 'app-nuevo-instrumento',
  templateUrl: './nuevo-instrumento.component.html',
  styleUrls: ['./nuevo-instrumento.component.css']
})
export class NuevoInstrumentoComponent implements OnInit {
  form: any = {};
  instrumento: Instrumento;
  creado = false;
  falloCreacion = false;
  msjFallo = '';
  msjOK = '';

  constructor(private instrumentoService: InstrumentoService) {
  }

  ngOnInit() {
  }

  onCreate(): void {
    this.instrumentoService.crear(this.form).subscribe(data => {
        this.msjOK = data.mensaje;
        this.creado = true;
        this.falloCreacion = false;
      },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        this.creado = false;
        this.falloCreacion = true;
      }
    );
  }

  volver(): void {
    window.history.back();
  }

}
