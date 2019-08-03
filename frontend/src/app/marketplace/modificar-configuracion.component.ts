import { Component, OnInit } from '@angular/core';
import {MarketplaceService} from '../servicios/marketplace.service';
import {ActivatedRoute, Route, Router} from '@angular/router';

@Component({
  selector: 'app-modificar-configuracion',
  templateUrl: './modificar-configuracion.component.html',
  styleUrls: ['./modificar-configuracion.component.css']
})
export class ModificarConfiguracionComponent implements OnInit {
  form: any = {};
  actualizado = false;
  falloActualizacion = false;
  msjFallo = '';
  msjOK = '';
  fallaInit = false;

  constructor(private marketplaceService: MarketplaceService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {

    this.marketplaceService.obtener().subscribe(data => {

      this.form.appID = data.appID;
        this.form.clientSecret = data.clientSecret;
      },
      (err: any) => {
        this.fallaInit = true;
        this.router.navigate(['']);
      }
    );
  }

  onUpdate() {
    this.marketplaceService.editar(this.form).subscribe(data => {
        this.actualizado = true;
        this.falloActualizacion = false;
        this.msjOK = 'Actualización de configuración de MarketPlace OK';
      },
        (err: any) => {
          this.actualizado = false;
          this.falloActualizacion = true;
          this.msjFallo = 'No se pudo actualizar configuración MarketPlace';
        }

    );


  }

  volver(): void {
    window.history.back();
  }


}
