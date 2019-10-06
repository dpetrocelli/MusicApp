import { Component, OnInit, ComponentFactoryResolver, NgModule } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { LoginDatos } from '../modelos/logindatos';
import { HomeSiteService } from '../servicios/homesite.service';
import { UsuarioService } from '../servicios/usuario.service';
import { Post } from '../modelos/post';
import { NuevoPostComponent } from '../miactividad/post/nuevo-post.component';
import { Elemento } from '../modelos/elemento';
import { YoutubePopupComponent } from '../miactividad/post/youtubePopup/youtubePopup.component';
import { ImgSliderComponent } from '../miactividad/post/imgSlider/imgSlider.component';


@Component({
  selector: 'app-homesite',
  templateUrl: './homesite.component.html',
  styleUrls: ['./homesite.component.css']
})
export class HomesiteComponent implements OnInit {
  userLogged : LoginDatos;
  hayPosts : boolean = false;
  posts : Post[] = [];
  imageObject: Array<object> =[];
  videoYoutube : String;
  listaDeElementos : String[];
  
  biografia : String;
  form: any = {};
  nuevoPostForm : boolean;
  nuevoPostComponent : NuevoPostComponent;
  
  hayBiografia : boolean = false;
  event_list : Array<object>;
  idImagenAbierta : number;
  temporalElementos: Elemento[];
  safeSrc: SafeResourceUrl;

  constructor(private usuarioService: UsuarioService,
              private homeSiteService: HomeSiteService,
              private router: Router,
              private sanitizer: DomSanitizer,
              private componentFactoryResolver: ComponentFactoryResolver,
              private modalService: NgbModal) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.loadInfo();

  }

  async loadInfo (){
    let inicio = 0;
    let fin = 10;
    
    this.posts = await this.homeSiteService.obtener(this.userLogged, inicio, fin).toPromise();
    
    
   try{
      if (this.posts.length>0){
         this.hayPosts = true;
         
         
      }
      }catch{

      }
    
  }

  buscar(){

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
                image: 'https://localhost:8081/api/archivo/descargar?path=' + e.rutaAcceso,
                thumbImage: 'https://localhost:8081/api/archivo/descargar?path=' + e.rutaAcceso
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
          image: 'https://localhost:8081/api/archivo/descargar?path=' + e.rutaAcceso,
          thumbImage: 'https://localhost:8081/api/archivo/descargar?path=' + e.rutaAcceso
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
