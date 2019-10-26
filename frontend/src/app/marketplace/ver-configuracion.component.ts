import { Component, OnInit } from '@angular/core';
import { Marketplace} from '../modelos/marketplace';
import {MarketplaceService} from '../servicios/marketplace.service';
import {Observable} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import { $ } from 'protractor';
import Swal from 'sweetalert2';

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
    Swal.fire({
      title: '¿Estás seguro?',
      text: "La eliminacion es permanente !",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'eliminar!',
      background: 'url(./assets/img/guitar_music_strings_musical_instrument_111863_1920x1080.jpg)'
    }).then((confirmado) => {
      if (confirmado.value) {

        this.marketPlaceService.borrar().subscribe(data => {
          this.obtenerConfiguracion();
        });
  
        Swal.fire(
          'Eliminado!',
          '.',
          'success'
        );
      }
    });
  }
}
