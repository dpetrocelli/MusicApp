import { Component, OnInit } from '@angular/core';
import { BandaService } from 'src/app/servicios/banda.service';
import { DomSanitizer } from '@angular/platform-browser';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PerfilService } from 'src/app/servicios/perfil.service';
import { LoginDatos } from 'src/app/modelos/logindatos';

@Component({
  selector: 'app-videoComponentRedSocial',
  templateUrl: './videoComponentRedSocial.component.html',
  styleUrls: ['./videoComponentRedSocial.component.css']
})
export class VideoComponentComponentRedSocial implements OnInit {
  userLogged : LoginDatos;
  videoYoutube : string;
  listaYoutube : string;
  nombreUsuario : string
  
  biografia : string[];

  isLoaded : boolean;
  constructor(private usuarioService: UsuarioService, private _sanitizer: DomSanitizer, private bandaService: BandaService, private router: Router, private activatedRoute: ActivatedRoute, private perfilService: PerfilService) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.nombreUsuario = this.activatedRoute.snapshot.paramMap.get("nombre");
    this.obtenerDatos();
    
  }

  async obtenerDatos(){
    this.biografia = await this.perfilService.obtenerbiografiaBandaRedSocial(this.userLogged, this.nombreUsuario).toPromise();
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

  getVideoIframe(url) {
    var video, results;
 
    if (url === null) {
        return '';
    }
    results = url.match('[\\?&]v=([^&#]*)');
    video   = (results === null) ? url : results[1];
 
    return this._sanitizer.bypassSecurityTrustResourceUrl(url);   
}
abrirYoutube(){
  window.open(this.listaYoutube,'_blank');
}
}
