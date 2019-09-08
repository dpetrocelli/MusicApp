import { Component, OnInit } from '@angular/core';
import { PerfilService } from 'src/app/servicios/perfil.service';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { Router } from '@angular/router';
import { LoginDatos } from 'src/app/modelos/logindatos';

@Component({
  selector: 'app-imagen-perfil',
  templateUrl: './imagen-perfil.component.html',
  styleUrls: ['./imagen-perfil.component.css']
})
export class ImagenPerfilComponent implements OnInit {
  files: FileList;
  loginDatos: LoginDatos = new LoginDatos();
  img: String;
  constructor(private perfilService: PerfilService,
              private usuarioService: UsuarioService,
              private router: Router) { }

  ngOnInit() {

    this.loginDatos = this.usuarioService.getUserLoggedIn();
    this.cargarImagenPerfil();

  }

  cargarImagenPerfil(){
    this.perfilService.buscarimagenperfil (this.loginDatos).subscribe(data => {
      this.img = "http://localhost:8081/api/archivo/descargar?path=";
      this.img+= data.mensaje;

      //console.log ("Img Backend loaded: ",this.img);
    },
    (err: any) => {
      console.log(" TENEMOS UN ERROR ", err);
      //this.router.navigate(['/accesodenegado']);
    });
  }
}
