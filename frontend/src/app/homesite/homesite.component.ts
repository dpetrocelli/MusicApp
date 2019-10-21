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
import { environment } from '../../environments/environment';
import { Banda } from '../modelos/banda';
import { Artista } from '../modelos/artista';
import Swal from 'sweetalert2';
import { NotificacionService } from '../servicios/notificacion.service';
import { BandaService } from '../servicios/banda.service';
import { truncate } from 'fs';
import { ZonaService } from '../servicios/zona.service';
import { InstrumentoService } from '../servicios/instrumento.service';
import { Zona } from '../modelos/zona';
import { Instrumento } from '../modelos/instrumento';

@Component({
  selector: 'app-homesite',
  templateUrl: './homesite.component.html',
  styleUrls: ['./homesite.component.css']
})
export class HomesiteComponent implements OnInit {
  userLogged : LoginDatos;
  hayPosts : boolean = false;
  hayArtistas : boolean = false;
  soyDuenioBanda : boolean;
  hayBandas : boolean = false;
  posts : Post[] = [];
  artistas : Artista[] = [];
  bandas : Banda[] = [];
  imageObject: Array<object> =[];
  videoYoutube : String;
  listaDeElementos : String[];
  zonas : Zona[];
  instrumentos : Instrumento[];
  biografia : String;
  form: any = {};
  nuevoPostForm : boolean;
  nuevoPostComponent : NuevoPostComponent;
  optionSelected : String;
  hayBiografia : boolean = false;
  event_list : Array<object>;
  idImagenAbierta : number;
  temporalElementos: Elemento[];
  safeSrc: SafeResourceUrl;
  artistasQueSonDeMiBanda : Artista[] = [];

  constructor(private usuarioService: UsuarioService,
              private homeSiteService: HomeSiteService,
              private bandaServicio: BandaService,
              private notificacionService: NotificacionService,
              private zonaService: ZonaService,
              private instrumentoService: InstrumentoService,
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
    this.zonas = await this.zonaService.lista().toPromise();
    this.instrumentos = await this.instrumentoService.lista().toPromise();
    this.posts = await this.homeSiteService.obtener(this.userLogged, inicio, fin).toPromise();
    
    
   try{
      if (this.posts.length>0){
         this.hayPosts = true;
      }
      }catch{
      }
    
  }

  revisionEnBanda(artista : Artista){
    //alert (" HOLA ");
    let response : boolean = false;
    if (this.userLogged.nombreUsuario==artista.usuario.username){
        //console.log (artista.usuario.username+" soy yo mismo");
        response = true;
    }
    this.artistasQueSonDeMiBanda.forEach(art => {
      
      if (art.nombre == artista.nombre){
        //console.log (artista.usuario.username+" ya estaba en la banda");
        response = true;
      }
    });
    
    return response;
    
  }

  async buscar(){
    
    this.hayArtistas = false; this.hayBandas = false; this.hayPosts = false;
    let textolibre = (<HTMLInputElement>document.getElementById('buscar')).value;
    let zona = (<HTMLInputElement>document.getElementById('zona')).value;
    let instrumento = (<HTMLInputElement>document.getElementById('instrumento')).value;
    let genero = (<HTMLInputElement>document.getElementById('genero')).value;
    if (this.optionSelected.length>0){
      if (this.optionSelected == "artista"){
        try{
          this.artistasQueSonDeMiBanda = await this.bandaServicio.SoyDuenioBanda(this.userLogged, null).toPromise();
          console.log ("LISTA DE ARTISTAS", this.artistasQueSonDeMiBanda);
          
        }catch {
          console.log (" No pude verificar si soy dueño banda");
        }
        this.artistas = await this.homeSiteService.buscar(this.userLogged, "usuario" ,textolibre, zona, instrumento, genero ).toPromise();
        if ((this.artistas != null) && (this.artistas.length>0)){
          this.hayArtistas = true;
          
        }else{
          Swal.fire({
            type: 'error',
            title: 'Oops...',
            text: "No hay artistas con esos criterios de búsqueda"        
          });
        }
      }else{
        if (this.optionSelected == "banda"){
          this.hayBandas = false;

          this.bandas = await this.homeSiteService.buscar(this.userLogged, "banda",textolibre, zona, instrumento, genero).toPromise();
          if ((this.bandas != null) && (this.bandas.length>0)){
            
            this.hayBandas = true;
          }else{
            Swal.fire({
              type: 'error',
              title: 'Oops...',
              text: "No hay bandas con esos criterios de búsqueda"        
            });
          }
        }else{
          this.posts = await this.homeSiteService.buscar(this.userLogged, "post",textolibre, zona, instrumento, genero ).toPromise();
          if ((this.posts != null) && (this.posts.length>0)){
            this.hayPosts = true;
          }else{
            Swal.fire({
              type: 'error',
              title: 'Oops...',
              text: "No hay posts con esos criterios de búsqueda"        
            });
          }
        }
      }
     
   }
    
  }

  cambioRadioButton(evt){
    
    this.optionSelected = new String (evt.target.id);
    

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

  async artistaEnviarMensaje(artista){
    
     const { value: msg } = await Swal.fire({
      title: 'Ingrese el mensaje',
      input: 'text',
      inputValue: "msg",
      showCancelButton: true,
      inputValidator: (value) => {
        if (!value) {
          return 'Debes ingresar un mensaje!'
        }
      }
    })

    if (msg) {
      
      this.notificacionService.nuevoMensajeNotificacion(this.userLogged, msg, artista, "msg").subscribe(data => {
        console.log ("RESPUESTA:", data);
        
          });      
         
          //console.log (" CARTEL ", data);
        
        (err: any) => {
          console.log(err.error.mensaje);
          
        }
      
      Swal.fire('Mensaje enviado al destinatario '+new String (artista.nombre));
    }
  }

  async artistaInvitarAMiBanda(artista){
    
    const { value: msg } = await Swal.fire({
     title: 'Ingrese el mensaje de invitación',
     input: 'text',
     inputValue: "Te quiero invitar a mi banda",
     showCancelButton: true,
     inputValidator: (value) => {
       if (!value) {
         return 'Debes ingresar un mensaje!'
       }
     }
   })

   if (msg) {
     
     this.notificacionService.nuevoMensajeNotificacion(this.userLogged, msg, artista, "moderacionArtista").subscribe(data => {
       console.log ("RESPUESTA:", data);
       
         });      
        
         //console.log (" CARTEL ", data);
       
       (err: any) => {
         console.log(err.error.mensaje);
         
       }
     
     Swal.fire('Invitación enviada al artista '+new String (artista.nombre));
   }
 }

  bandaEnviarMensaje(){

  }

  ocultarImagen(){
    var contenedor : HTMLElement = document.getElementById('post'+this.idImagenAbierta);
    this.idImagenAbierta = 0;
  }

  isEdited(post: Post) : Boolean {
    return post.fechaEdicion == null ? false : true;
  }

  onDelete(id: number): void {
    if (confirm('¿Estás seguro?')) {
    }
  }
}
