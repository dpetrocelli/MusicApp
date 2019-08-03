import { Component, OnInit } from '@angular/core';
import { Marketplace} from '../modelos/marketplace';
import {MarketplaceService} from '../servicios/marketplace.service';
import {Observable} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-ver-configuracion',
  templateUrl: './ver-configuracion.component.html',
  styleUrls: ['./ver-configuracion.component.css']
})
export class VerConfiguracionComponent implements OnInit {

  marketPlace: Marketplace = null;

  constructor(private marketPlaceService: MarketplaceService, private activatedRoute: ActivatedRoute,
              private router: Router) {

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

  onDelete() {
    if (confirm('¿Estás seguro?')) {

       this.marketPlaceService.borrar().subscribe();
       location.reload();

    }
  }
}
