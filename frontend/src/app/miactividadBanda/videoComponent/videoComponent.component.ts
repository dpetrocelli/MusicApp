import { Component, OnInit } from '@angular/core';
import { LoginDatos } from 'src/app/modelos/logindatos';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { BandaService } from 'src/app/servicios/banda.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PerfilService } from 'src/app/servicios/perfil.service';
import { Banda } from 'src/app/modelos/banda';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { TouchSequence } from 'selenium-webdriver';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-videoComponent',
  templateUrl: './videoComponent.component.html',
  styleUrls: ['./videoComponent.component.css']
})
export class VideoComponentComponent implements OnInit {
  userLogged : LoginDatos;
  videoYoutube : string;
  listaYoutube : string;
  
  biografia : string[];

  isLoaded : boolean;
  constructor(private usuarioService: UsuarioService, private _sanitizer: DomSanitizer, private bandaService: BandaService, private router: Router, private perfilService: PerfilService) { }

  ngOnInit() {
    this.isLoaded = false;
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.obtenerDatos();
  }

  
  getVideoIframe(url) {
    var video, results;
 
    if (url === null) {
        return '';
    }
    results = url.match('[\\?&]v=([^&#]*)');
    video   = (results === null) ? url : results[1];
 
    return this._sanitizer.bypassSecurityTrustResourceUrl(url);   
}


async obtenerDatos(){
  this.biografia = await this.perfilService.obtenerbiografiaBanda(this.userLogged).toPromise();
  console.log( " DATOS BIOG: ", this.biografia);
  this.videoYoutube = null;
  this.listaYoutube = null;
  try{
    this.videoYoutube = this.biografia[3];
    
  }catch {

  }

  try{
    this.listaYoutube = this.biografia[4];
  }catch{

  }
 
    
  
  
}

async editarVideoYoutube(){
  const { value: text } = await Swal.fire({
    input: 'text',
    inputPlaceholder: this.videoYoutube
  })
  
  if (text) {
    let inp : string = text;
    if ((inp.startsWith ("https://www.youtube.com/"))) {
      this.videoYoutube = text;
      
      this.actualizarVideoYoutube();
      Swal.fire('Se actualizó link videoYoutube ');
    }else{
      Swal.fire({
        type: 'error',
        title: 'Oops...',
        text: 'direccion no valida!'
        
      })
    }
   
    
  }
}

async editarListaYoutube(){
  const { value: text } = await Swal.fire({
    input: 'text',
    inputPlaceholder: this.listaYoutube
  })
  
  if (text) {
    let inp : string = text;
    if ((inp.startsWith ("https://www.youtube.com/"))) {
      this.listaYoutube = text;
      
      this.actualizarListaYoutube();
      Swal.fire('Se actualizó link ListaYoutube ');
    }else{
      Swal.fire({
        type: 'error',
        title: 'Oops...',
        text: 'direccion no valida!'
        
      })
    }
   
    
  }
}

actualizarVideoYoutube(){
  this.perfilService.actualizarVideoYoutube(this.userLogged, this.videoYoutube).subscribe(data => {
    //console.log (data);   
    this.obtenerDatos();      
  },
  (err: any) => {
    console.log(err);
    this.router.navigate(['/accesodenegado']);
  });
}

actualizarListaYoutube(){
  this.perfilService.actualizarListaYoutube(this.userLogged, this.listaYoutube).subscribe(data => {
    //console.log (data);   
    this.obtenerDatos();      
  },
  (err: any) => {
    console.log(err);
    this.router.navigate(['/accesodenegado']);
  });
}

abrirYoutube(){
  window.open(this.listaYoutube,'_blank');
}
 

  

}
