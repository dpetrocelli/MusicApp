import { Component, OnInit, HostBinding, Input, ViewChild, ViewContainerRef, ComponentFactoryResolver  } from '@angular/core';
import { PerfilService } from 'src/app/servicios/perfil.service';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { Router } from '@angular/router';
import { Post } from 'src/app/modelos/post';
import { LoginDatos } from 'src/app/modelos/logindatos';
import { NuevoPostComponent } from 'src/app/redsocial/post/nuevo-post.component';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  @HostBinding('class')
  @Input() postComponent: PostComponent;

  constructor(private usuarioService: UsuarioService,
              private router: Router,
              private perfilService : PerfilService,
              private componentFactoryResolver: ComponentFactoryResolver) { }

  posts : Post[] = [];
  listaDeElementos : String[];
  userLogged : LoginDatos;
  biografia : String;
  form: any = {};
  nuevoPostForm : boolean;
  nuevoPostComponent : NuevoPostComponent;
  hayPosts : boolean = false;
  hayBiografia : boolean = false;

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    
    this.nuevoPostComponent = new NuevoPostComponent(this.perfilService, this.usuarioService, this.router);
    this.nuevoPostForm = false;
    // luego voy a buscar los posts
    this.perfilService.existebiografia (this.userLogged).subscribe(data => {
      
      console.log (data);
      this.hayBiografia = true;
    },
    (err: any) => {
      console.log( err );
      //this.router.navigate(['/accesodenegado']);
    });
    this.perfilService.obtenerposts (this.userLogged).subscribe(data => {
      //this.biografia = data.mensaje;
      this.posts = data;
     
      console.log("Biografia: ",this.posts);
     
      // ahora voy a buscar todos los recursos
      if (this.posts.length>0){
        this.hayPosts = true;
        this.posts.forEach(post => {
          
          this.perfilService.obtenerelementos(post.id).subscribe(data => {
            this.listaDeElementos = data;
            console.log ("Post: "+JSON.stringify(post) +" / Recursos: ",this.listaDeElementos);
          },
          (err: any) => {
            console.log(err);
            //this.router.navigate(['/accesodenegado']);
          });
      });
      }
      
    },
    (err: any) => {
      console.log(err);
      
    });
  }

  nuevoPost(){
   this.nuevoPostForm = !this.nuevoPostForm;
   this.nuevoPostComponent.visible = this.nuevoPostForm;
  }
}
