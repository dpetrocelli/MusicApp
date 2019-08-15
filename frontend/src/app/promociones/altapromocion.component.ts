import { Component, OnInit } from '@angular/core';
import  {Promocion } from '../modelos/promocion';
import { PromocionService } from '../servicios/promocion.service';
import { LoginDatos } from '../modelos/logindatos';
import { UsuarioService } from '../servicios/usuario.service';

@Component({
  selector: 'app-altapromocion',
  templateUrl: './altapromocion.component.html',
  styleUrls: ['./altapromocion.component.css']
})
export class AltapromocionComponent implements OnInit {
  form: any = {};
  promocion: Promocion;
  userLogged : LoginDatos;
  creado = false;
  falloCreacion = false;
  msjFallo = '';
  msjOK = '';
  
  constructor(private promocionService: PromocionService,
              private usuarioService: UsuarioService) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
  }

  onCreate(): void {
    
    this.promocion = this.form;
    this.promocionService.crear(this.promocion, this.userLogged).subscribe(data => {
        this.msjOK = data.mensaje;
        this.creado = true;
        this.falloCreacion = false;
      },
      (err: any) => {
        this.msjFallo = JSON.stringify(err);
        this.creado = false;
        this.falloCreacion = true;
      }
    );
  }

}
