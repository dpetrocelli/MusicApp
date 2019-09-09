import { Component, OnInit } from '@angular/core';
import  {Promocion } from '../modelos/promocion';
import { PromocionService } from '../servicios/promocion.service';
import { LoginDatos } from '../modelos/logindatos';
import { UsuarioService } from '../servicios/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';

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
              private usuarioService: UsuarioService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
  }

  onCreate(): void {
    
    this.promocion = this.form;
    this.promocionService.crear(this.promocion, this.userLogged).subscribe(data => {
      this.msjOK = " Creado con éxito";
      this.creado = true;
      this.falloCreacion = false;
      setTimeout(() => {
          this.router.navigate(['/promociones']);
      },3000);
      },
      (err: any) => {
        this.msjFallo = "Ocurrio un error: "+err.error;
        this.creado = false;
        this.falloCreacion = true;
      }
    );
  }

  volver(): void {
    window.history.back();
  }
}
