import { Component, OnInit } from '@angular/core';
import { PerfilService } from 'src/app/servicios/perfil.service';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { ActivatedRoute } from '@angular/router';
import { LoginDatos } from 'src/app/modelos/logindatos';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-imagen-perfil-redsocial-banda',
  templateUrl: './imagen-perfil-redsocial-banda.component.html',
  styleUrls: ['./imagen-perfil-redsocial-banda.component.css']
})
export class ImagenPerfilRedsocialBandaComponent implements OnInit {
  loginDatos: LoginDatos = new LoginDatos();
  nombreUsuario: string;
  img: String;
  constructor(private perfilService: PerfilService,
    private usuarioService: UsuarioService,
    private router: ActivatedRoute) { }

  ngOnInit() {

    this.nombreUsuario = this.router.snapshot.paramMap.get("nombre");
    console.log(this.nombreUsuario);
    this.loginDatos = this.usuarioService.getUserLoggedIn();
    this.cargarImagenPerfil();
  }

  cargarImagenPerfil(){
    this.perfilService.buscarimagenperfilBanda (this.loginDatos).subscribe(data => {
      this.img = environment.urlApiBackend + "api/archivo/descargar?path=";
      this.img+= data.mensaje;

      //console.log ("Img Backend loaded: ",this.img);
    },
    (err: any) => {
      console.log(" TENEMOS UN ERROR ", err);
      //this.router.navigate(['/accesodenegado']);
    });
  }
}
