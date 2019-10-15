import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { Post } from '../../modelos/post';
import { PerfilService } from '../../servicios/perfil.service';
import { Router, ActivatedRoute } from '@angular/router';
import { UsuarioService } from '../../servicios/usuario.service';
import { LoginDatos } from '../../modelos/logindatos';
import { PostComponent } from './post.component';

@Component({
  selector: 'app-editar-post',
  templateUrl: './editar-post.component.html',
  styleUrls: ['./editar-post.component.css']
})
export class EditarPostComponent implements OnInit {
  form: any = {};
  post: Post;
  files : FileList;
  Actualizado = false;
  falloActualizacion = false;
  msjFallo = '';
  msjOK = '';
  loginDatos: LoginDatos = new LoginDatos();
  idPost: string;
  visible : boolean;
  message: string = "hola mundo!"
  meLlamanDeRedSocial : boolean = false; 
  @Output() messageEvent = new EventEmitter<string>();
  

  constructor(private postService : PerfilService,
              private usuarioService: UsuarioService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit() {
    
    if (this.route.snapshot.params.id === "homeSocialNetwork"){
      this.meLlamanDeRedSocial = true;
    }
    this.visible = true;
    console.log("Actualizar Post. Visible:",this.visible);
    this.loginDatos = this.usuarioService.getUserLoggedIn();
  }
  
  public cargandoImagen(files: FileList){
    this.files = files;
  }

  onCreate(): void {
    this.post = this.form;
    this.crearPost();
  }

  async crearPost () {
    let data = await this.postService.crearpost (this.loginDatos, this.post).toPromise();
    console.log ("ejecute editar post", data.mensaje);
    this.idPost = data.mensaje;
    if (this.files != null){
      
      Array.from (this.files).forEach(file => {
        this.postService.enviarimagen(file, this.idPost, this.loginDatos).subscribe(data => {
          console.log (" Pude guardar imagen", file.name);
          this.Actualizado = true;
          this.falloActualizacion = false;
          this.msjOK = data.mensaje;
        },
        (err: any) => {
          console.log (err);
          this.msjFallo = err.error.mensaje;
          this.Actualizado = false;
          this.falloActualizacion = true;
        });
      });
    }
    
    setTimeout(() => {
      this.msjOK = "Posteado correctamente";
    }, 1500);
    this.visible = false;
  
    if (!this.meLlamanDeRedSocial){
      this.messageEvent.emit(this.message);
      console.log(" TRUE PAPU ", this.message);
    }else{
      window.history.back();
    }
  }
}