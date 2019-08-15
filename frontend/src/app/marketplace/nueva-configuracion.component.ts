import { Component, OnInit } from '@angular/core';
import {Marketplace} from '../modelos/marketplace';
import {MarketplaceService} from '../servicios/marketplace.service';

@Component({
  selector: 'app-nueva-configuracion',
  templateUrl: './nueva-configuracion.component.html',
  styleUrls: ['./nueva-configuracion.component.css']
})
export class NuevaConfiguracionComponent implements OnInit {
 // variables de alta
  form: any = {};
  marketPlace: Marketplace;
  creado = false;
  falloCreacion = false;
  msjFallo = '';
  msjOK = '';
  constructor(private marketplaceService: MarketplaceService) {

  }

  ngOnInit() {
  }

  onCreate(): void {
    this.marketplaceService.nuevo(this.form).subscribe(data => {
        this.msjOK = 'Configuración OK';
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
