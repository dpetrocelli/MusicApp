import { Component, OnInit } from '@angular/core';
import { LoginDatos } from 'src/app/modelos/logindatos';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PerfilService } from 'src/app/servicios/perfil.service';
import { Artista } from 'src/app/modelos/artista';

@Component({
  selector: 'app-info-perfil-redsocial',
  templateUrl: './info-perfil-redsocial.component.html',
  styleUrls: ['./info-perfil-redsocial.component.css']
})
export class InfoPerfilRedsocialComponent implements OnInit {

  loginDatos: LoginDatos;
  artista : Artista;
  biografia : String;
  nombreUsuario : string;
  isLoaded : boolean = false;
  form: any = {};

  //@HostBinding('class')
  //@Input() biografiaComponent: InfoPerfilComponent;
  constructor(private usuarioService: UsuarioService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private perfilService: PerfilService) { }

 
  ngOnInit() {
    // buscar en la base de datos la biografia
          this.nombreUsuario = this.activatedRoute.snapshot.paramMap.get("nombre");
          
          this.loginDatos = this.usuarioService.getUserLoggedIn();
          
          this.perfilService.obtenerbiografiaRedSocial(this.loginDatos,this.nombreUsuario).subscribe(data => {
          this.biografia = data.mensaje;
          },
          (err: any) => {
              console.log(err);
              this.router.navigate(['/accesodenegado']);
          });
          
          this.usuarioService.obtenerDatosUsuarioRedSocial (this.loginDatos,this.nombreUsuario).subscribe (data => {
            console.log (data);
            this.artista = data;
            this.isLoaded = true;
            },
            (err: any) => {
                console.log(err);
                this.router.navigate(['/accesodenegado']);
            });
  }

}
