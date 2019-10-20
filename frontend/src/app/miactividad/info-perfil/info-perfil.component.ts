import { Component, OnInit, Input, HostBinding } from '@angular/core';
import { PerfilService } from '../../servicios/perfil.service';
import { LoginDatos } from '../../modelos/logindatos';
import { UsuarioService } from '../../servicios/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Post } from '../../modelos/post';
import { Elemento } from '../../modelos/elemento';
import { Usuario } from '../../modelos/usuario';
import { Artista } from '../../modelos/artista';
import { BandaService } from 'src/app/servicios/banda.service';
import { Banda } from 'src/app/modelos/banda';

@Component({
  selector: 'app-info-perfil',
  templateUrl: './info-perfil.component.html',
  styleUrls: ['./info-perfil.component.css']
})
export class InfoPerfilComponent implements OnInit {
  posts : Post[] = [];
  elemString : String[];
  elementos : Elemento[] = [];
  userLogged : LoginDatos;
  biografia : String;
  artista : Artista;
  listaDeArtistasEnBanda : Artista[];
  detalleDeLaBanda : Banda;
  isLoaded : boolean = false;
  form: any = {};
  bandaActiva = false;

  @HostBinding('class')
  @Input() biografiaComponent: InfoPerfilComponent;
  constructor(private usuarioService: UsuarioService,
              private bandaService: BandaService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private perfilService: PerfilService) { }

 
  ngOnInit() {
    // buscar en la base de datos la biografia
    
          
          this.userLogged = this.usuarioService.getUserLoggedIn();
          this.perfilService.obtenerbiografia(this.userLogged).subscribe(data => {
          this.biografia = data.mensaje;
          },
          (err: any) => {
              console.log(err);
              this.biografia = "ingrese una descripción";
          });
          
          this.obtenerDatosUsuario();
          
              
            
  }

  async obtenerDatosUsuario(){
    this.artista = await this.usuarioService.obtenerDatosUsuario (this.userLogged).toPromise()
    
    console.log (" ARTISTA logueado",this.artista.banda);
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
      this.bandaService.obtenerArtistasDeBanda (this.userLogged, null).subscribe (data => {
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
      
      this.bandaService.SoyDuenioBanda(this.userLogged).subscribe (data => {
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
  actualizar(){
    this.perfilService.actualizarbiografia(this.userLogged, this.form.biografia).subscribe(data => {
      //console.log (data);         
    },
    (err: any) => {
      console.log(err);
      this.router.navigate(['/accesodenegado']);
    });
  }
 
  
}




