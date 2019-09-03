import { Component, OnInit } from '@angular/core';
import { Post } from '../../modelos/post';
import { PerfilService } from '../../servicios/perfil.service';
import { Router } from '@angular/router';
import { UsuarioService } from '../../servicios/usuario.service';
import { LoginDatos } from '../../modelos/logindatos';

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
  visible : boolean;

  constructor(private postService : PerfilService,
              private usuarioService: UsuarioService,
              private router: Router) { }

  ngOnInit() {
    this.visible = true;
    console.log("Post nuevo. Visible:",this.visible);
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
    if (this.files != null){
      
      Array.from (this.files).forEach(file => {
        this.postService.enviarimagen(file, this.idPost, this.loginDatos).subscribe(data => {
          console.log (" Pude guardar imagen", file.name);
          this.creado = true;
          this.falloCreacion = false;
          this.msjOK = data.mensaje;
        },
        (err: any) => {
          console.log (err);
          this.msjFallo = err.error.mensaje;
          this.creado = false;
          this.falloCreacion = true;
        });
      });
    }
    
    setTimeout(() => {
      this.msjOK = "Posteado correctamente";
    }, 1500);
    this.visible = false;
  }
}
