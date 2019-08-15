import { Component, OnInit } from '@angular/core';
import { Promocion } from '../modelos/promocion';
import { PromocionService } from '../servicios/promocion.service';
import { UsuarioService } from '../servicios/usuario.service';
import { LoginDatos } from '../modelos/logindatos';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-listarpromociones',
  templateUrl: './listarpromociones.component.html',
  styleUrls: ['./listarpromociones.component.css']
})
export class ListarpromocionesComponent implements OnInit {
  promociones : Promocion[] = [];
  userLogged : LoginDatos;
  formattedData : Date;
  datePipe : DatePipe;
  
  constructor(private promocionService: PromocionService, 
              private usuarioService: UsuarioService) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.cargarpromociones();
  }

  cargarpromociones(): void {
    this.promocionService.lista(this.userLogged).subscribe(data => {
        this.promociones = data;

        
      },
      (err: any) => {
        console.log(err);
      });
  }

  onDelete(id: number): void {
    if (confirm('¿Estás seguro?')) {
      this.promocionService.borrar(id).subscribe(data => {
        this.cargarpromociones();
      });
    }
  }
}
