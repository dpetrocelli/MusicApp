import { Component, OnInit } from '@angular/core';
import { PromocionService } from '../servicios/promocion.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Promocion } from '../modelos/promocion';
import { LoginDatos } from '../modelos/logindatos';
import { UsuarioService } from '../servicios/usuario.service';

@Component({
  selector: 'app-editarpromocion',
  templateUrl: './editarpromocion.component.html',
  styleUrls: ['./editarpromocion.component.css']
})
export class EditarpromocionComponent implements OnInit {
  form: any = {};
  actualizado = false;
  falloActualizacion = false;
  msjFallo = '';
  msjOK = '';
  promocion : Promocion;
  idPromo : number;
  init_point_mercadopago : String;
  idPublicacionMP : String; 
  fallaInit = false;
  userLogged : LoginDatos;

  constructor(private promocionService: PromocionService,
              private usuarioService: UsuarioService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    const id = this.activatedRoute.snapshot.params.id;
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.promocionService.detalle(id, this.userLogged).subscribe(data => {//tengo que pasar el logindata
        this.promocion = data;
        this.form.titulo = this.promocion.titulo;
        this.form.descripcion = this.promocion.descripcion;
        this.form.tipomoneda = this.promocion.tipomoneda;
        this.form.valorpromocion = this.promocion.valorpromocion;
        this.form.importe = this.promocion.importe;
        this.form.vigencia = this.promocion.vigencia;
        this.idPromo = this.promocion.id;
        this.init_point_mercadopago = this.promocion.init_point_mercadopago;
        this.idPublicacionMP = this.promocion.idPublicacionMP;

      },
      (err: any) => {
        console.log("Ocurrio un error",err)
      }
    );
  }

  onUpdate(): void {
    const id = this.activatedRoute.snapshot.params.id;
    this.promocion = this.form;
    this.promocion.id = this.idPromo;
    this.promocion.init_point_mercadopago = this.init_point_mercadopago;
    this.promocion.idPublicacionMP = this.idPublicacionMP;
    this.promocionService.editar(this.promocion, this.userLogged).subscribe(data => {
        this.actualizado = true;
        this.falloActualizacion = false;
        this.msjOK = data.mensaje;
        this.router.navigate(['promociones']);

      },
      (err: any) => {
        this.actualizado = false;
        this.falloActualizacion = true;
        this.msjFallo = err.error.mensaje;
      }
    );
  }

  volver(): void {
    window.history.back();
  }

}
