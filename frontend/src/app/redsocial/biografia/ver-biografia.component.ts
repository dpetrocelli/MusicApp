import { Component, OnInit, Input, HostBinding } from '@angular/core';
import { PerfilService } from '../../servicios/perfil.service';
import { LoginDatos } from '../../modelos/logindatos';
import { UsuarioService } from '../../servicios/usuario.service';
import { Router } from '@angular/router';
import { Post } from '../../modelos/post';
import { Elemento } from '../../modelos/elemento';

@Component({
  selector: 'app-ver-biografia',
  templateUrl: './ver-biografia.component.html',
  styleUrls: ['./ver-biografia.component.css']
})
export class VerBiografiaComponent implements OnInit {
  posts : Post[] = [];
  elemString : String[];
  elementos : Elemento[] = [];
  userLogged : LoginDatos;
  biografia : String;
  form: any = {};

  @HostBinding('class')
  @Input() biografiaComponent: VerBiografiaComponent;
  constructor(private usuarioService: UsuarioService,
              private router: Router,
              private perfilService: PerfilService) { }

  ngOnInit() {
    // buscar en la base de datos la biografia
      this.userLogged = this.usuarioService.getUserLoggedIn();
      this.perfilService.obtenerbiografia(this.userLogged).subscribe(data => {
      this.biografia = data.mensaje;
   },
   (err: any) => {
      console.log(err);
      this.router.navigate(['/accesodenegado']);
   });
  }

  actualizar(){
    this.perfilService.actualizarbiografia(this.userLogged, this.form.biografia).subscribe(data => {
      console.log (data);         
    },
    (err: any) => {
      console.log(err);
      this.router.navigate(['/accesodenegado']);
    });
  }
 

}


