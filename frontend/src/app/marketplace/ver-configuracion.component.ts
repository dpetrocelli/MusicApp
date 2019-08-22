import { Component, OnInit } from '@angular/core';
import { Marketplace} from '../modelos/marketplace';
import {MarketplaceService} from '../servicios/marketplace.service';
import {Observable} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import { $ } from 'protractor';

@Component({
  selector: 'app-ver-configuracion',
  templateUrl: './ver-configuracion.component.html',
  styleUrls: ['./ver-configuracion.component.css']
})
export class VerConfiguracionComponent implements OnInit {

  marketPlace: Marketplace;
  datosRegistrados: boolean;

  constructor(private marketPlaceService: MarketplaceService, private activatedRoute: ActivatedRoute,
              private router: Router) {

  }

  ngOnInit() {
    this.marketPlace = null;
    this.datosRegistrados = false;
    this.obtenerConfiguracion();
  }

  private obtenerConfiguracion(): void {
    this.marketPlaceService.obtener().subscribe(data => {
      this.marketPlace = data;
      if ((this.marketPlace.clientSecret != null) && (this.marketPlace.appID != null)){
        this.datosRegistrados = true;
      }else{
        this.datosRegistrados = false;
      }
      
      console.log (JSON.stringify(this.marketPlace));

      },
       (err: any) => {
          console.log (err);
    });

  }

  onDelete() {
    if (confirm('¿Estás seguro?')) {

       this.marketPlaceService.borrar().subscribe(data => {
        this.obtenerConfiguracion();
      });

   }
  }
}
