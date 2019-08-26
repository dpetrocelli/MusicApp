import { Component, OnInit } from '@angular/core';
import { Post } from '../modelos/post';
import { PostService } from '../servicios/post.service';
import { Router } from '@angular/router';
import { UsuarioService } from '../servicios/usuario.service';
import { LoginDatos } from '../modelos/logindatos';

@Component({
  selector: 'app-nuevo-post',
  templateUrl: './nuevo-post.component.html',
  styleUrls: ['./nuevo-post.component.css']
})
export class NuevoPostComponent implements OnInit {
  form: any = {};
  post: Post;
  creado = false;
  falloCreacion = false;
  msjFallo = '';
  msjOK = '';
  loginDatos: LoginDatos = new LoginDatos();

  constructor(private postService : PostService,
              private usuarioService: UsuarioService,
              private router: Router) { }

  ngOnInit() {
    this.loginDatos = this.usuarioService.getUserLoggedIn();
  }
  
  public cargandoImagen(files: FileList){
    console.log (files);
      this.postService.enviarimagen(files[0]).subscribe(data => {
        console.log (" HOLI ENTRE");
      },
      (err: any) => {
        console.log (err);
      }
    );
      
  }
  
  onCreate(): void {
    this.post = this.form;
    this.postService.crearpost (this.loginDatos, this.post).subscribe(data => {
        this.msjOK = data.mensaje;
        this.creado = true;
        this.falloCreacion = false;
      },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        this.creado = false;
        this.falloCreacion = true;
      }
    );
  }


}
