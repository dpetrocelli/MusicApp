import { Component, OnInit } from '@angular/core';
import { Marketplace} from '../modelos/marketplace';
import {MarketplaceService} from '../servicios/marketplace.service';

@Component({
  selector: 'app-ver-configuracion',
  templateUrl: './ver-configuracion.component.html',
  styleUrls: ['./ver-configuracion.component.css']
})
export class VerConfiguracionComponent implements OnInit {

  marketPlace: Marketplace;

  constructor(private marketPlaceService: MarketplaceService) {

  }

  ngOnInit() {
    this.obtenerConfiguracion();
  }

  private obtenerConfiguracion(): void {
    this.marketPlaceService.obtener().subscribe(data => {
      this.marketPlace = data;
      },
       (err: any) => {
          console.log (err);
    });

  }
}
