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
  files : FileList;
  creado = false;
  falloCreacion = false;
  msjFallo = '';
  msjOK = '';
  loginDatos: LoginDatos = new LoginDatos();
  idPost: string;

  constructor(private postService : PostService,
              private usuarioService: UsuarioService,
              private router: Router) { }

  ngOnInit() {
    this.loginDatos = this.usuarioService.getUserLoggedIn();
  }
  
  public cargandoImagen(files: FileList){
    this.files = files;
         
  }

  
 
  onCreate(): void {
    this.post = this.form;
    this.crearPost();
    /*this.postService.crearpost (this.loginDatos, this.post).subscribe(data => {
        this.msjOK = data.mensaje;
        this.creado = true;
        this.falloCreacion = false;
        this.idPost = data.mensaje;
      },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        this.creado = false;
        this.falloCreacion = true;
      }
    );
      
    this.postService.enviarimagen(this.files[0], this.idPost).subscribe(data => {
      console.log (" HOLI ENTRE");
    },
    (err: any) => {
      console.log (err);
    });
*/
    
  }

  async crearPost () {
    let data = await this.postService.crearpost (this.loginDatos, this.post).toPromise();
    console.log ("ejecute crear post", data.mensaje);
    this.idPost = data.mensaje;

    this.postService.enviarimagen(this.files[0], this.idPost).subscribe(data => {
      console.log (" Pude guardar imagen");
    },
    (err: any) => {
      console.log (err);
    });

    
  }


}
