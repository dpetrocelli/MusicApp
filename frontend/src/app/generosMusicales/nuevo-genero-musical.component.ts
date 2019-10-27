import { Component, OnInit } from '@angular/core';
import { GeneroMusical } from '../modelos/generoMusical';
import { GeneroMusicalService } from '../servicios/generoMusical.service';

@Component({
  selector: 'app-nuevo-genero-musical',
  templateUrl: './nuevo-genero-musical.component.html',
  styleUrls: ['./nuevo-genero-musical.component.css']
})
export class NuevoGeneroMusicalComponent implements OnInit {
  form: any = {};
  generoMusical: GeneroMusical;
  creado = false;
  falloCreacion = false;
  msjFallo = '';
  msjOK ='';

  constructor(private generoMusicalService: GeneroMusicalService) { }

  ngOnInit() {
  }

  onCreate(): void {
    this.generoMusicalService.crear(this.form).subscribe(data => {
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
