import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../servicios/usuario.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-nuevousuario',
  templateUrl: './nuevousuario.component.html',
  styleUrls: ['./nuevousuario.component.css']
})
export class NuevousuarioComponent implements OnInit {

  form: any = {};
  formCompleto: false;
  msjFallo = '';
  msjOK = '';
  creado = false;
  falloCreacion = false;
  isArtista = true;
  constructor(private usuarioService: UsuarioService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
  }

  guardarUsuario() {
    this.form.isArtista = this.isArtista;
    console.log(this.form);
    this.usuarioService.registrar(this.form, this.isArtista).subscribe(data => {
      this.msjOK = data.msg ;
      this.creado = true;
      this.falloCreacion = false;
      this.router.navigate(['']);
    },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        this.creado = false;
        this.falloCreacion = true;
      }

    );
  }
}
