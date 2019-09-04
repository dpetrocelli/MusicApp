import { Component, OnInit, HostBinding, Input, ViewChild, ViewContainerRef, ComponentFactoryResolver, NgModule  } from '@angular/core';
import { PerfilService } from 'src/app/servicios/perfil.service';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { Router } from '@angular/router';
import { Post } from 'src/app/modelos/post';
import { Elemento } from 'src/app/modelos/elemento';
import { LoginDatos } from 'src/app/modelos/logindatos';
import { NuevoPostComponent } from 'src/app/redsocial/post/nuevo-post.component';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { NgImageSliderModule } from 'ng-image-slider';
import { BrowserModule } from '@angular/platform-browser';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { analyzeAndValidateNgModules } from '@angular/compiler';
import { promise } from 'selenium-webdriver';



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
  imageObject: Array<object> =[];
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
  
  temporalElementos: Elemento[];
  safeSrc: SafeResourceUrl;
  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    
    this.nuevoPostComponent = new NuevoPostComponent(this.perfilService, this.usuarioService, this.router);
    this.nuevoPostForm = false;
    // luego voy a buscar los posts
    this.perfilService.existebiografia (this.userLogged).subscribe(data => {
      
      console.log ("[APP-POST] -> Biografía obtenida");
      this.hayBiografia = true;
    },
    (err: any) => {
      console.log( err );
      //this.router.navigate(['/accesodenegado']);
    });

    this.obtenerPosts();

  
    
  }

  async obtenerPosts(){
      this.posts = await this.perfilService.obtenerposts (this.userLogged).toPromise();
      console.log("[APP-POST] -> Posts y elementos Obtenidos:",this.posts);
      
      try{
          if (this.posts.length>0){
          this.hayPosts = true;
          }
      }catch{

      }
             
  }
/*
  obtenerElementos(id : number)  {
    var e : Elemento[] ;
    this.perfilService.obtenerelementos(id).subscribe(data => {
      console.log("[APP-POST] -> Elementos Obtenidos:", data);
      this.temporalElementos = data;
      
    },
    (err: any) => {
      console.log("Sali obtenerElemento ",err );
      
    });
    return e;
  }
*/
  nuevoPost(){
   this.nuevoPostForm = !this.nuevoPostForm;
   this.nuevoPostComponent.visible = this.nuevoPostForm;
  }
  
  hasResource(post : Post) : Boolean{
    try{
      var result : Boolean = false;
      if (post.elementos.length>0){
        result = true;
      }
      this.imageObject = [];
      post.elementos.forEach(e => {
        if (e.tipoRecurso == "youtube"){
          this.safeSrc =  this.sanitizer.bypassSecurityTrustResourceUrl(String (e.rutaAcceso));
        }else{
          // URL REF
          
          var obj: object = {image: "http://localhost:8081/api/archivo/descargar?path="+e.rutaAcceso, thumbImage:"http://localhost:8081/api/archivo/descargar?path="+e.rutaAcceso};
          this.imageObject.push(obj);
          
        }
        
        
      });
      //console.log (JSON.stringify(this.imageObject));
      return result;
    }catch{
      console.log(" ERROR POST " + post);
      return false;
    }
  }
  

  isEdited(post: Post) : Boolean {
    return post.fechaEdicion == null?false : true;
  }
}

