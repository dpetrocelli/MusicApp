import { Component, OnInit } from '@angular/core';
import { GeneroMusicalService } from '../servicios/generoMusical.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-editar-genero-musical',
  templateUrl: './editar-genero-musical.component.html',
  styleUrls: ['./editar-genero-musical.component.css']
})
export class EditarGeneroMusicalComponent implements OnInit {
  form: any = {};
  actualizado = false;
  falloActualizacion = false;
  msjFallo = '';
  msjOK = '';

  fallaInit = false;

  constructor(private generoMusicalService: GeneroMusicalService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    const id = this.activatedRoute.snapshot.params.id;
    this.generoMusicalService.detalle(id).subscribe(data => {
        this.form.nombre = data.nombre;
      },
      (err: any) => {
        this.fallaInit = true;
        this.router.navigate(['']);
      }
    );
  }

  onUpdate(): void {
    const id = this.activatedRoute.snapshot.params.id;
    this.generoMusicalService.editar(this.form, id).subscribe(data => {
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
