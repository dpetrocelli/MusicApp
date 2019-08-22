import { Component, OnInit } from '@angular/core';
import { Promocion } from '../modelos/promocion';
import { PromocionService } from '../servicios/promocion.service';
import { UsuarioService } from '../servicios/usuario.service';
import { LoginDatos } from '../modelos/logindatos';
import { Router } from '@angular/router';

@Component({
  selector: 'app-listarpromociones',
  templateUrl: './listarpromociones.component.html',
  styleUrls: ['./listarpromociones.component.css']
})
export class ListarpromocionesComponent implements OnInit {
  promociones : Promocion[] = [];
  userLogged : LoginDatos;
  
  constructor(private promocionService: PromocionService, 
              private usuarioService: UsuarioService,
              private router: Router) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.cargarpromociones();
  }

  cargarpromociones(): void {
    this.promocionService.lista(this.userLogged).subscribe(data => {
      
      
         data.forEach(element => {
            element.vigenciaStr = new Date(element.vigencia).toLocaleDateString('es-AR');
          });
          this.promociones = data;
          
         
      
         
      
            
       
      },
      (err: any) => {
        console.log(err);
        this.router.navigate(['/accesodenegado']);
      });
  }

  onDelete(promocion: Promocion): void {
    if (confirm('¿Estás seguro?')) {
      this.promocionService.borrar(promocion, this.userLogged).subscribe(data => {
        this.cargarpromociones();
      });
    }
  }
}
