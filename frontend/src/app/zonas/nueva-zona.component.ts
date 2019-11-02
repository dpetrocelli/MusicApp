import { Component, OnInit } from '@angular/core';
import { ZonaService } from '../servicios/zona.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-nueva-zona',
  templateUrl: './nueva-zona.component.html',
  styleUrls: ['./nueva-zona.component.css']
})
export class NuevaZonaComponent implements OnInit {

  form: any = {};
  actualizado = false;
  falloActualizacion = false;
  msjFallo = '';
  msjOK = '';

  fallaInit = false;

  constructor(private zonaService: ZonaService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    
  }

  onCreate(): void {
    
    this.zonaService.crear(this.form).subscribe(data => {
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

