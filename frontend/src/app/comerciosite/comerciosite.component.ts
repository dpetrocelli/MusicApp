import { Component, OnInit, ComponentFactoryResolver, NgModule } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Router } from '@angular/router';
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
import { Comercio } from '../modelos/comercio';
import { Lugar } from '../modelos/lugar';
import { LugarService } from '../servicios/lugar.service';

@Component({
  selector: 'app-comerciosite',
  templateUrl: './comerciosite.component.html',
  styleUrls: ['./comerciosite.component.css']
})
export class ComercioSiteComponent implements OnInit {
  userLogged: LoginDatos;

  hayLugares: boolean = false;
  lugares: Lugar[] = [];

  zonas: Zona[];

  safeSrc: SafeResourceUrl;



  constructor(private usuarioService: UsuarioService,
    private homeSiteService: HomeSiteService,
    private bandaServicio: BandaService,
    private notificacionService: NotificacionService,
    private zonaService: ZonaService,
    private generoMusicalService: GeneroMusicalService,
    private lugaresServicio: LugarService,
    private router: Router,
    private sanitizer: DomSanitizer,
    private puntuacionService: PuntuacionService,
    private componentFactoryResolver: ComponentFactoryResolver,
    private modalService: NgbModal) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();

    this.loadInfo();

  }

  async loadInfo() {
    let inicio = 0;
    let fin = 10;
    this.zonas = await this.zonaService.lista().toPromise();


    

  }



  async buscar() {

    this.hayLugares = false;

    let zona: any;
    let textolibre: any;


    zona = (<HTMLSelectElement>document.getElementById('zona')).value;

    textolibre = (<HTMLInputElement>document.getElementById('buscar')).value;

    this.lugares = await this.lugaresServicio.buscar(this.userLogged, zona, textolibre).toPromise();
    if ((this.lugares != null) && (this.lugares.length > 0)) {

      this.hayLugares = true;

    } else {
      Swal.fire({
        type: 'error',
        title: 'Oops...',
        text: "No hay bandas con esos criterios de búsqueda"
      });
    }
  }

    
    
  
    
 


verImagen(post: Post) {
  var packImg: Array<object> = [];
  post.elementos.forEach(e => {
    if (e.tipoRecurso.includes('img')) {
      var obj: object = {
        image: environment.urlApiBackend + 'api/archivo/descargar?path=' + e.rutaAcceso,
        thumbImage: environment.urlApiBackend + 'api/archivo/descargar?path=' + e.rutaAcceso
      };
      packImg.push(obj);
    }
  });
  const modalRef = this.modalService.open(ImgSliderComponent, { centered: true, size: 'xl' });
  modalRef.componentInstance.packImg = packImg;
}


}
