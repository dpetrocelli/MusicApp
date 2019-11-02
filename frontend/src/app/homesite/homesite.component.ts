import { Component, OnInit, ComponentFactoryResolver, NgModule } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginDatos } from '../modelos/logindatos';
import { HomeSiteService } from '../servicios/homesite.service';
import { UsuarioService } from '../servicios/usuario.service';
import { Post } from '../modelos/post';
import { PuntuacionArtista } from '../modelos/puntuacion';
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
import { GeneroMusicalService } from '../servicios/generoMusical.service';
import { GeneroMusical } from '../modelos/generoMusical';
import { PuntuacionService } from '../servicios/puntuacion.service';

@Component({
  selector: 'app-homesite',
  templateUrl: './homesite.component.html',
  styleUrls: ['./homesite.component.css']
})
export class HomesiteComponent implements OnInit {
  userLogged : LoginDatos;
  hayPosts : boolean = false;
  hayArtistas : boolean = false;
  soyAdmin : boolean = false;
  soyIntegrante : boolean = false;
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
  generosmusicales : GeneroMusical[];
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
  integrantes : Artista[] = [];
  listaPuntuacion : PuntuacionArtista[] = [];
  promedio : number = 0;
  promedioCargado : boolean = false;
  vinoPedidoBanda : boolean = true;

  constructor(private usuarioService: UsuarioService,
              private homeSiteService: HomeSiteService,
              private bandaServicio: BandaService,
              private notificacionService: NotificacionService,
              private zonaService: ZonaService,
              private generoMusicalService : GeneroMusicalService,
              private instrumentoService: InstrumentoService,
              private router: Router,
              private routersnap: ActivatedRoute,
              private sanitizer: DomSanitizer,
              private puntuacionService : PuntuacionService,
              private componentFactoryResolver: ComponentFactoryResolver,
              private modalService: NgbModal) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();

    this.optionSelected = this.routersnap.snapshot.paramMap.get("opcion");
    if (this.optionSelected.startsWith("banda")){
      
      (<HTMLSelectElement>document.getElementById('instrumento')).disabled = true;
      (<HTMLSelectElement>document.getElementById('zona')).disabled = false;
      (<HTMLInputElement>document.getElementById('buscar')).disabled = false;
      (<HTMLSelectElement>document.getElementById('generomusical')).disabled = false;
      this.vinoPedidoBanda = true;
    }
    
    this.loadInfo();

  }

  async loadInfo (){
    try{
      this.artistasQueSonDeMiBanda = await this.bandaServicio.SoyDuenioBanda(this.userLogged, null).toPromise();
    this.soyAdmin=true;
    }catch{

    }
    let arti : Artista = await this.usuarioService.obtenerDatosUsuario(this.userLogged).toPromise();
    if (arti.banda.length>0){
      this.soyIntegrante = true;
    }
    
    let inicio = 0;
    let fin = 10;
    this.zonas = await this.zonaService.lista().toPromise();
    this.instrumentos = await this.instrumentoService.lista().toPromise();
    this.generosmusicales = await this.generoMusicalService.lista().toPromise();
    this.posts = await this.homeSiteService.obtener(this.userLogged, inicio, fin).toPromise();
    
    
   try{
      if (this.posts.length>0){
         this.hayPosts = true;
      }
      }catch{
      }
    
  }

  async tienePuntuacionArtista(artista : Artista){
    
      this.listaPuntuacion = await this.puntuacionService.obtenerPuntuacionRedSocial(this.userLogged, artista.usuario.username).toPromise();
      //this.promedio = 0;
      let promedio = 0;
      let contador = 0;
  
      if (this.listaPuntuacion.length> 0){
        this.listaPuntuacion.forEach(puntuacion => {
         
          promedio+=puntuacion.puntuacion;
          contador+=1;
          //console.log (" OBJETO", puntuacion);
        });
        promedio = promedio / contador;
        console.log (" PROM PUNT : "+promedio);
        
      }else{
        promedio = 0;
        
      }
      
      
     
      this.promedioCargado = true;
      return promedio;
    
   
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
    
    this.hayArtistas = false; 
    this.hayBandas = false; 
    this.hayPosts = false;
    let textolibre : any;
    let zona : any;
    let instrumento : any;
    let genero: any;
    
      textolibre = (<HTMLInputElement>document.getElementById('buscar')).value;
      genero = (<HTMLSelectElement>document.getElementById('generomusical')).value;
      zona = (<HTMLSelectElement>document.getElementById('zona')).value;
      instrumento = (<HTMLSelectElement>document.getElementById('instrumento')).value;
     
  
  
    console.log ("opt selected",this.optionSelected);
    console.log (" parametros", textolibre, genero, zona, instrumento);
    try{
      if (this.optionSelected.length >0){
      
        if (this.optionSelected.startsWith ("ar")){
          console.log (" ARTISTA");
          
          try{
            
            
          }catch {
            console.log (" No pude verificar si soy dueño banda");
          }
          this.artistas = await this.homeSiteService.buscar(this.userLogged, "usuario" ,textolibre, zona, instrumento, genero).toPromise();
          if ((this.artistas != null) && (this.artistas.length>0)){
            for (const artista of this.artistas){
              
              this.listaPuntuacion = await this.puntuacionService.obtenerPuntuacionRedSocial(this.userLogged, artista.usuario.username).toPromise();
              //this.promedio = 0;
              let promedio = 0;
              let contador = 0;
          
              if (this.listaPuntuacion.length> 0){
                this.listaPuntuacion.forEach(puntuacion => {
                
                  promedio+=puntuacion.puntuacion;
                  contador+=1;
                  //console.log (" OBJETO", puntuacion);
                });
                promedio = promedio / contador;
                console.log (" PROM PUNT : "+promedio);
                
              }else{
                promedio = 0;
                
              }
              artista.promedio = promedio;
              
            };
            
            
            this.hayArtistas = true;
            
          }else{
            Swal.fire({
              type: 'error',
              title: 'Oops...',
              text: "No hay artistas con esos criterios de búsqueda"        
            });
          }
          
        }else{
          if (this.optionSelected.startsWith ("band")){
            this.hayBandas = false;
            
            this.bandas = await this.homeSiteService.buscar(this.userLogged, "banda",textolibre, zona, instrumento, genero).toPromise();
            if ((this.bandas != null) && (this.bandas.length>0)){
              this.hayBandas = true;
              for (const banda of this.bandas){
                console.log (banda.artistaLider);
                this.integrantes = await this.bandaServicio.buscarArtistas(this.userLogged, banda).toPromise();
                console.log (" INTEGRANTES ", this.integrantes);
                this.listaPuntuacion = await this.puntuacionService.obtenerPuntuacionBanda(this.userLogged, banda.nombre).toPromise();
                //this.promedio = 0;
                let promedio = 0;
                let contador = 0;
                
                if (this.listaPuntuacion.length> 0){
                  this.listaPuntuacion.forEach(puntuacion => {
                  
                    promedio+=puntuacion.puntuacion;
                    contador+=1;
                    //console.log (" OBJETO", puntuacion);
                  });
                  promedio = promedio / contador;
                  console.log (" PROM PUNT : "+promedio);
                  
                }else{
                  promedio = 0;
                  
                }
                banda.promedio = promedio;
                
              };
              
              
             
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
  }catch{
      Swal.fire({
        type: 'error',
        title: 'Oops...',
        text: "Elija un criterio de búsqueda"        
      });
    }
    
    
  
    
  }

  tieneInstrumentos(artista : Artista){
    if (artista.instrumento.length>0){
      return true;
    }else{
      return false;
    }
   
  }

  cambioRadioButton(evt){
    
    this.optionSelected = new String (evt.target.id);
    if (this.optionSelected.startsWith("ar")) {
      (<HTMLSelectElement>document.getElementById('instrumento')).disabled = false;
      (<HTMLSelectElement>document.getElementById('zona')).disabled = false;
      (<HTMLInputElement>document.getElementById('buscar')).disabled = false;
      (<HTMLSelectElement>document.getElementById('generomusical')).disabled = false;
    }
    if (this.optionSelected.startsWith("ban")) {
      (<HTMLSelectElement>document.getElementById('instrumento')).disabled = true;
      (<HTMLSelectElement>document.getElementById('zona')).disabled = false;
      (<HTMLInputElement>document.getElementById('buscar')).disabled = false;
      (<HTMLSelectElement>document.getElementById('generomusical')).disabled = false;
      
    }
    if (this.optionSelected.startsWith("post")) {
      (<HTMLSelectElement>document.getElementById('instrumento')).disabled = true;
      (<HTMLSelectElement>document.getElementById('zona')).disabled = true;
      (<HTMLInputElement>document.getElementById('buscar')).disabled = false;
      (<HTMLSelectElement>document.getElementById('generomusical')).disabled = true;
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

  async bandaEnviarMensaje(banda){
    
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
     
     this.notificacionService.nuevoMensajeNotificacion(this.userLogged, msg, banda.artistaLider, "msg").subscribe(data => {
       console.log ("RESPUESTA:", data);
       
         });      
        
         //console.log (" CARTEL ", data);
       
       (err: any) => {
         console.log(err.error.mensaje);
         
       }
     
     Swal.fire('Mensaje enviado al destinatario '+new String (banda.nombre));
   }
 }

  async artistaInvitarAMiBanda(artista : Artista){
    console.log ("voy a invitar a artista: ", artista);
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


 async artistaSolicitaAccesoABanda(banda : Banda){
    
  const { value: msg } = await Swal.fire({
   title: 'Ingrese el mensaje de solicitud de acceso a banda',
   input: 'text',
   inputValue: "Hola, quiero ingresar a tu banda "+banda.nombre,
   showCancelButton: true,
   inputValidator: (value) => {
     if (!value) {
       return 'Debes ingresar un mensaje!'
     }
   }
 })

 if (msg) {
   let artistax : Artista = await this.usuarioService.obtenerDatosUsuario(this.userLogged).toPromise();
   this.notificacionService.nuevoMensajeNotificacion(this.userLogged, msg, banda.artistaLider , "moderacionBanda").subscribe(data => {
     console.log ("RESPUESTA:", data);
     
       });      
      
       //console.log (" CARTEL ", data);
     
     (err: any) => {
       console.log(err.error.mensaje);
       
     }
   
   Swal.fire('Invitación enviada a la banda '+new String (banda.nombre));
 }
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
