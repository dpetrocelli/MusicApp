import { Component, OnInit } from '@angular/core';
import { LoginDatos } from 'src/app/modelos/logindatos';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PerfilService } from 'src/app/servicios/perfil.service';
import { Artista } from 'src/app/modelos/artista';
import { BandaService } from 'src/app/servicios/banda.service';
import { Banda } from 'src/app/modelos/banda';

@Component({
  selector: 'app-info-perfil-redsocial',
  templateUrl: './info-perfil-redsocial.component.html',
  styleUrls: ['./info-perfil-redsocial.component.css']
})
export class InfoPerfilRedsocialComponent implements OnInit {

  loginDatos: LoginDatos;
  artista : Artista;
  biografia : string[];
  bio : String = null;
  nombreUsuario : string;
  isLoaded : boolean = false;
  form: any = {};
  listaDeArtistasEnBanda : Artista[];
  detalleDeLaBanda : Banda; 
  bandaActiva : boolean ; 
  spotify : string = null;
  facebook : string = null;
  haySpotify : boolean = false;
  hayFacebook : boolean = false;
  //@HostBinding('class')
  //@Input() biografiaComponent: InfoPerfilComponent;
  constructor(private usuarioService: UsuarioService,
              private router: Router,
              private bandaService: BandaService,
              private activatedRoute: ActivatedRoute,
              private perfilService: PerfilService) { }

 
  ngOnInit() {
    // buscar en la base de datos la biografia
          this.nombreUsuario = this.activatedRoute.snapshot.paramMap.get("nombre");
          
          this.loginDatos = this.usuarioService.getUserLoggedIn();
          
          this.obtenerbiografia();
          
  }

  async obtenerbiografia(){
    
      this.biografia = await this.perfilService.obtenerbiografiaRedSocial(this.loginDatos,this.nombreUsuario).toPromise();
           
    console.log (" LLEGO ESTO, ", this.biografia);
          if (this.biografia[0]!= null){
            this.bio = this.biografia[0];
            console.log ("PIBe - BIO, ", this.bio);
          }

          if (this.biografia[1]!= null){
            this.spotify = this.biografia[1];
            this.haySpotify = true;
            console.log ("PIBe - SPOT, ", this.spotify);
          }

          if (this.biografia[2]!= null){
            this.facebook = this.biografia[2];
            this.hayFacebook = true;
            console.log ("PIBe - FB, ", this.facebook);
          }
         
  
      this.obtenerDatosUsuario();
      
  }
  abrirSpotify(){
    window.open(this.spotify, '_blank');
  }

  abrirFacebook(){
    window.open(this.facebook, '_blank');
  }
  
  async obtenerDatosUsuario(){
    this.artista = await this.usuarioService.obtenerDatosUsuarioRedSocial (this.loginDatos,this.nombreUsuario).toPromise();     
    this.isLoaded = true;
    

 /*
             Una vez que cargue los datos del usuario voy a validar
              a) si soy integrante de banda
                -> si soy, voy a tener los datos en artista.banda[0]
                -> tengo el duenio de la banda en artista.banda[0].duenio
                -> por lo tanto tengo que fijarme que otro artista tiene esa banda (banda[0])
                -> en su lista de bandas

            
              b) soy el duenio de una banda
                  -> soy yo como artita duenio, no necesito nada
                  -> tengo que ir a buscar todos los artistas de la banda
                */
               
               if (this.artista.banda.length>0){
                console.log (" caso A, soy integrante de banda");
                this.bandaService.obtenerArtistasDeBanda (this.loginDatos, this.artista).subscribe (data => {
                console.log (" artistas de la banda", data);
                this.listaDeArtistasEnBanda = data;
                //this.listaDeArtistasEnBanda.push(this.artista);
                this.detalleDeLaBanda = this.listaDeArtistasEnBanda[0].banda[0];
                this.bandaActiva = true;
                      
                  },
          
                  (err: any) => {
                    console.log(err);
                    this.router.navigate(['/accesodenegado']);
                  });
                
              }else{
                // puedo ser de tipo B o C
                
                this.bandaService.SoyDuenioBanda(this.loginDatos, this.artista).subscribe (data => {
                console.log ("  caso B, soy duenio de banda ", data);
                this.listaDeArtistasEnBanda = data; 
                //let index = this.listaDeArtistasEnBanda.indexOf(this.artista);
                //this.listaDeArtistasEnBanda.splice(index, 1);
                if (this.listaDeArtistasEnBanda.length>0){
                    
                  // es por que soy caso B
                    this.detalleDeLaBanda = this.listaDeArtistasEnBanda[0].banda[0];
                   
                    
                    this.bandaActiva = true;
                }else{
                  // caso C, no soy duenio ni tampoco estoy en una banda
                  console.log (" caso C, soy duenio ni pertenezco de banda");
                }         
                },
                (err: any) => {
                  console.log(err);
                  //this.router.navigate(['/accesodenegado']);
                });
                
              }


  }

}
