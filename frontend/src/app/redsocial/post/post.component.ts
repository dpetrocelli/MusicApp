import { Component, OnInit, HostBinding, Input, ViewChild, ViewContainerRef, ComponentFactoryResolver, NgModule  } from '@angular/core';
import { PerfilService } from 'src/app/servicios/perfil.service';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { Router } from '@angular/router';
import { Post } from 'src/app/modelos/post';
import { Elemento } from 'src/app/modelos/elemento';
import { LoginDatos } from 'src/app/modelos/logindatos';
import { NuevoPostComponent } from 'src/app/miactividad/post/nuevo-post.component';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ImgSliderComponent } from './imgSlider/imgSlider.component';
import { NgbModalModule, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { YoutubePopupComponent } from './youtubePopup/youtubePopup.component';



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
              private perfilService: PerfilService,
              public sanitizer: DomSanitizer,
              private componentFactoryResolver: ComponentFactoryResolver,
              private modalService: NgbModal) { }

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
  idImagenAbierta : number;

  temporalElementos: Elemento[];
  safeSrc: SafeResourceUrl;
  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();

    this.nuevoPostComponent = new NuevoPostComponent(this.perfilService, this.usuarioService, this.router);
    this.nuevoPostForm = false;
    // luego voy a buscar los posts
    this.perfilService.existebiografia (this.userLogged).subscribe(data => {

      console.log ('[APP-POST] -> Biografía obtenida');
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
      console.log('[APP-POST] -> Posts y elementos Obtenidos:',this.posts);

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

  hasResource(post : Post, type : string) : Boolean{
    try{
      var result : Boolean = false;
      this.imageObject = [];
      if (post.elementos.length > 0) {
        if (type.includes('vid')) {
          this.imageObject = [];
          post.elementos.forEach(e => {
            if (e.tipoRecurso.includes('youtube')) {
              this.safeSrc =  this.sanitizer.bypassSecurityTrustResourceUrl(String(e.rutaAcceso));
              result = true;
            }
          });
        }
        if(type.includes('img')){
          post.elementos.forEach(e => {
            if(e.tipoRecurso.includes('img')){
              var obj: object = {
                image: 'http://localhost:8081/api/archivo/descargar?path=' + e.rutaAcceso,
                thumbImage: 'http://localhost:8081/api/archivo/descargar?path=' + e.rutaAcceso
              };
              this.imageObject.push(obj);
              result = true;
            }
          });
        }
      }
      return result;
    }catch{
      console.log(' ERROR POST ' + post);
      return false;
    }
  }

  verImagen(post: Post) {
    var packImg: Array<object> =[];
    post.elementos.forEach(e => {
      if(e.tipoRecurso.includes('img')) {
        var obj: object = {
          image: 'http://localhost:8081/api/archivo/descargar?path=' + e.rutaAcceso,
          thumbImage: 'http://localhost:8081/api/archivo/descargar?path=' + e.rutaAcceso
        };
        packImg.push(obj);
      }
    });
    const modalRef = this.modalService.open(ImgSliderComponent, { centered: true , size: 'xl'});
    modalRef.componentInstance.packImg = packImg;
  }

  verVideo(post: Post) {
    var safeSrc : SafeResourceUrl;
    post.elementos.forEach(e => {
      if(e.tipoRecurso.includes('youtube')) {
        safeSrc =  this.sanitizer.bypassSecurityTrustResourceUrl(String(e.rutaAcceso));
      }
    });
    const modalRef = this.modalService.open(YoutubePopupComponent, { centered: true , size: 'xl'});
    modalRef.componentInstance.url = safeSrc;
  }

  ocultarImagen(){
    var contenedor : HTMLElement = document.getElementById('post'+this.idImagenAbierta);
    this.idImagenAbierta = 0;
  }

  isEdited(post: Post) : Boolean {
    return post.fechaEdicion == null ? false : true;
  }
}

