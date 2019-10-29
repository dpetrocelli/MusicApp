import { Component, OnInit } from '@angular/core';
import {LugarService} from '../servicios/lugar.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-editar-lugar',
  templateUrl: './editar-lugar.component.html',
  styleUrls: ['./editar-lugar.component.css']
})
export class EditarLugarComponent implements OnInit {
  form: any = {};
  actualizado = false;
  falloActualizacion = false;
  msjFallo = '';
  msjOK = '';

  fallaInit = false;

  constructor(private lugarService: LugarService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    const id = this.activatedRoute.snapshot.params.id;
    this.lugarService.detalle(id).subscribe(data => {
//        this.form.nombreLugar = data.nombre;
//        this.form.tipoLugar = data.tipoLugar;
      },
      (err: any) => {
        this.fallaInit = true;
        this.router.navigate(['']);
      }
    );
  }

  onUpdate(): void {
    const id = this.activatedRoute.snapshot.params.id;
    this.lugarService.editar(this.form, id).subscribe(data => {
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
