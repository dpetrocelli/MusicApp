import { Component, OnInit, HostBinding, Input, ViewChild, ViewContainerRef, ComponentFactoryResolver, NgModule  } from '@angular/core';
import { PerfilService } from 'src/app/servicios/perfil.service';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { Router } from '@angular/router';
import { Post } from 'src/app/modelos/post';
import { LoginDatos } from 'src/app/modelos/logindatos';
import { NuevoPostComponent } from 'src/app/redsocial/post/nuevo-post.component';
import {DomSanitizer} from '@angular/platform-browser';
import { NgImageSliderModule } from 'ng-image-slider';
import { BrowserModule } from '@angular/platform-browser';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';


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
              public sanitizer: DomSanitizer,
              private componentFactoryResolver: ComponentFactoryResolver) { }

  posts : Post[] = [];
  videoYoutube : String;
  listaDeElementos : String[];
  userLogged : LoginDatos;
  biografia : String;
  form: any = {};
  nuevoPostForm : boolean;
  nuevoPostComponent : NuevoPostComponent;
  hayPosts : boolean = false;
  hayBiografia : boolean = false;
  event_list : Array<object>;
  imageObject: Array<object> = [];
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
            
          
            if ((data[0].includes('youtube'))){
              this.videoYoutube = data[0];
              console.log (" THISVIDEOYOUTUBE: "+this.videoYoutube);
              try{
                this.listaDeElementos = data.slice (1, data.length);
              }catch{
                console.log (" NO HAY ELEMENTOS");
              }
            }else{
              this.listaDeElementos = data;
            }

            
            let campo : object;
              this.listaDeElementos.forEach(element => {
                
                        
                this.imageObject.push({image: element,thumbImage: element});
              });
            console.log (" SIZE IMG : ",this.imageObject.length );
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
