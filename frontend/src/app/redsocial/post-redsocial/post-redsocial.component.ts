import { Component, OnInit, ComponentFactoryResolver } from '@angular/core';
import { Post } from 'src/app/modelos/post';
import { LoginDatos } from 'src/app/modelos/logindatos';
import { NuevoPostComponent } from 'src/app/miactividad/post/nuevo-post.component';
import { Elemento } from 'src/app/modelos/elemento';
import { SafeResourceUrl, DomSanitizer } from '@angular/platform-browser';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PerfilService } from 'src/app/servicios/perfil.service';
import { ImgSliderComponent } from '../../miactividad/post/imgSlider/imgSlider.component';
import { NgbModalModule, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { YoutubePopupComponent } from '../../miactividad/post/youtubePopup/youtubePopup.component';
import { environment } from '../../../environments/environment';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-post-redsocial',
  templateUrl: './post-redsocial.component.html',
  styleUrls: ['./post-redsocial.component.css']
})
export class PostRedsocialComponent implements OnInit {
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
  nombreUsuario: string;
  temporalElementos: Elemento[];
  safeSrc: SafeResourceUrl;
  
  constructor(private usuarioService: UsuarioService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private perfilService: PerfilService,
    public sanitizer: DomSanitizer,
    private componentFactoryResolver: ComponentFactoryResolver, private modalService: NgbModal) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(routeParams => {

      console.log("parametros: ", routeParams);

      this.userLogged = this.usuarioService.getUserLoggedIn();
      this.nombreUsuario = routeParams.nombre;
      this.obtenerPosts();
      this.hayBiografia = true;
  
    });

  }

  async obtenerPosts(){
      this.posts = await this.perfilService.obtenerpostsRedSocial (this.userLogged, this.nombreUsuario).toPromise();
      console.log('[APP-POST-REDSOCIAL] -> Posts y elementos Obtenidos:',this.posts);

      try{
          if (this.posts.length>0){
          this.hayPosts = true;
          }
      }catch{

      }

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
                image: environment.urlApiBackend + 'api/archivo/descargar?path=' + e.rutaAcceso,
                thumbImage: environment.urlApiBackend + 'api/archivo/descargar?path=' + e.rutaAcceso
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
          image: environment.urlApiBackend + 'api/archivo/descargar?path=' + e.rutaAcceso,
          thumbImage: environment.urlApiBackend + 'api/archivo/descargar?path=' + e.rutaAcceso
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

  onDelete(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "La eliminacion es permanente !",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'eliminar!',
      background: 'url(./assets/img/guitar_music_strings_musical_instrument_111863_1920x1080.jpg)'
    }).then((confirmado) => {
      if (confirmado.value) {

        ///ACA EL CODIGO DE ELIMINACION

        Swal.fire(
          'Eliminado!',
          '.',
          'success'
        );
      }
    });
  }
}
