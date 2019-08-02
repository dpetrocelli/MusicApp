import {Component, OnInit} from '@angular/core';
import {InstrumentoService} from '../servicios/instrumento.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-editar-instrumento',
  templateUrl: './editar-instrumento.component.html',
  styleUrls: ['./editar-instrumento.component.css']
})
export class EditarInstrumentoComponent implements OnInit {
  form: any = {};
  actualizado = false;
  falloActualizacion = false;
  msjFallo = '';
  msjOK = '';

  fallaInit = false;

  constructor(private instrumentoService: InstrumentoService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    const id = this.activatedRoute.snapshot.params.id;
    this.instrumentoService.detalle(id).subscribe(data => {
        this.form.nombreInstrumento = data.nombreInstrumento;
        this.form.tipoInstrumento = data.tipoInstrumento;
      },
      (err: any) => {
        this.fallaInit = true;
        this.router.navigate(['']);
      }
    );
  }

  onUpdate(): void {
    const id = this.activatedRoute.snapshot.params.id;
    this.instrumentoService.editar(this.form, id).subscribe(data => {
        this.actualizado = true;
        this.falloActualizacion = false;
        this.msjOK = data.mensaje;
      },
      (err: any) => {
        this.actualizado = false;
        this.falloActualizacion = true;
        this.msjFallo = err.error.mensaje;
      }
    );
  }

  volver(): void {
    window.history.back();
  }
}
