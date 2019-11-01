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
import Swal from 'sweetalert2';
import { NotificacionService } from 'src/app/servicios/notificacion.service';

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
  biografia : string[];
  artista : Artista;
  listaDeArtistasEnBanda : Artista[];
  detalleDeLaBanda : Banda;
  isLoaded : boolean = false;
  form: any = {};
  bandaActiva = false;
  bio : String = null;
  spotify : string = null;
  facebook : string = null;
  haySpotify : boolean = false;
  hayFacebook : boolean = false;
  soyIntegrante : boolean = false;
  @HostBinding('class')
  @Input() biografiaComponent: InfoPerfilComponent;
  constructor(private usuarioService: UsuarioService,
            private notificacionService : NotificacionService,
              private bandaService: BandaService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private perfilService: PerfilService) { }

 
  ngOnInit() {
    // buscar en la base de datos la biografia
    
          
          this.userLogged = this.usuarioService.getUserLoggedIn();
          this.obtenerBio();
             
          
         
          
              
            
  }

  abrirSpotify(){
    window.open(this.spotify, '_blank');
  }

  abrirFacebook(){
    window.open(this.facebook, '_blank');
  }

  async editarSpotify(){
    const { value: text } = await Swal.fire({
      input: 'text',
      inputPlaceholder: this.spotify
    })
    
    if (text) {
      let inp : string = text;
      if (inp.includes ("https://spotify.com")){
        this.form.spotify = text;
        console.log (" VAMOS A ACTUALIAR CON", this.form.spotify);
        this.actualizar();
        Swal.fire('Se actualizó link spotify ');
      }else{
        Swal.fire({
          type: 'error',
          title: 'Oops...',
          text: 'direccion no valida!'
          
        })
      }
     
      
    }
  }
  async editarFacebook(){
    const { value: text } = await Swal.fire({
      input: 'text',
      inputPlaceholder: this.facebook
    })
    
    if (text) {
      let inp : string = text;
      if (inp.includes ("https://facebook.com")){
        this.form.facebook = text;
        console.log (" VAMOS A ACTUALIAR CON", this.form.facebook);
        this.actualizar();
        Swal.fire('Se actualizó facebook ');
      }else{
        Swal.fire({
          type: 'error',
          title: 'Oops...',
          text: 'direccion no valida!'
          
        })
      }
     
      
    }
  }
  async obtenerBio(){
    this.biografia = await this.perfilService.obtenerbiografia(this.userLogged).toPromise();
           
    
          if (this.biografia[0]!= null){
            this.bio = this.biografia[0];
            
          }

          if (this.biografia[1]!= null){
            this.spotify = this.biografia[1];
            this.haySpotify = true;
            
          }

          if (this.biografia[2]!= null){
            this.facebook = this.biografia[2];
            this.hayFacebook = true;
            
          }
          this.obtenerDatosUsuario();
  }

  async borrarIntegranteBanda(detalleDeLaBanda : Banda, integrante : Artista, quienfue : String){
    let header = "";
    if (quienfue.startsWith("soyint")){
      header = "Estás seguro de salir de la banda: ";
      header+=this.detalleDeLaBanda.nombre;
    }else{
      header = "Estás seguro de eliminar a: ";
      header+= integrante.usuario.username;
    }
    
    Swal.fire({
      title: header ,
      text: "No podrás revertir esto",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Si quiero!',
      background: 'url(./assets/img/guitar_music_strings_musical_instrument_111863_1920x1080.jpg)'
    }).then((result) => {
      if (result.value) {
        this.bandaService.eliminarArtistaDeBanda(this.userLogged, detalleDeLaBanda, integrante).toPromise();
        if (quienfue.startsWith("soyint")){
          this.notificacionService.nuevoMensajeNotificacion(this.userLogged, "Salí de tu banda", this.detalleDeLaBanda.artistaLider, "msg").toPromise();  
        }else{
          this.notificacionService.nuevoMensajeNotificacion(this.userLogged, "Te he eliminado de mi banda", integrante, "msg").toPromise();
        }
       
        Swal.fire(
          'Eliminado corretamente!',
          '',
          'success'
        )
        this.obtenerDatosUsuario();
      }
    })
  }


  async obtenerDatosUsuario(){
    this.artista = await this.usuarioService.obtenerDatosUsuario (this.userLogged).toPromise();
    
    console.log (" ARTISTA logueado -> IUPU",this.artista);
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
      this.soyIntegrante = true;
            
        },

        (err: any) => {
          console.log(err);
          this.router.navigate(['/accesodenegado']);
        });
      
    }else{
      // puedo ser de tipo B o C
      //this.fullbanda();
      
      this.bandaService.datosBanda(this.userLogged).subscribe (data => {
        this.detalleDeLaBanda = data;
        console.log ("DATOS DE MI BANDA", this.detalleDeLaBanda);
        
        //this.listaDeArtistasEnBanda = data; 
        this.bandaService.SoyDuenioBanda(this.userLogged, null).subscribe (data => {
          console.log ( " MIS INTEGRANTES", data);
          this.listaDeArtistasEnBanda = data; 
          //let index = this.listaDeArtistasEnBanda.indexOf(this.artista);
          //this.listaDeArtistasEnBanda.splice(index, 1);
          
          },
          (err: any) => {
            console.log (" caso C, no soy duenio ni pertenezco de banda");
            console.log(err);
            //this.router.navigate(['/accesodenegado']);
          });
          this.bandaActiva=true;
        },
        (err: any) => {
          console.log ("ERROR GATO");
          console.log(err);
          //this.router.navigate(['/accesodenegado']);
        });
        
        /*
      
      this.bandaService.SoyDuenioBanda(this.userLogged, null).subscribe (data => {
      
      this.listaDeArtistasEnBanda = data; 
      //let index = this.listaDeArtistasEnBanda.indexOf(this.artista);
      //this.listaDeArtistasEnBanda.splice(index, 1);
      if (this.listaDeArtistasEnBanda.length>0){
        console.log ("  caso B, soy duenio de banda ", data);
        // es por que soy caso B
          this.detalleDeLaBanda = this.listaDeArtistasEnBanda[0].banda[0];
         
          
          this.bandaActiva = true;
      }else{
        // caso C, no soy duenio ni tampoco estoy en una banda
       
      }        
      },
      (err: any) => {
        console.log (" caso C, no soy duenio ni pertenezco de banda");
        console.log(err);
        //this.router.navigate(['/accesodenegado']);
      });
      */ 
    }

  }

  
  actualizar(){
    let error = false;
    if ((this.form.facebook!="") && (!(this.form.facebook.includes ("https://facebook.com")))){
        
        Swal.fire({
          type: 'error',
          title: 'Facebook...',
          text: 'direccion no valida!, ajuste para actualizar'
          
        })
        this.form.facebook = "";
        error = true;
    }

    if ( (this.form.spotify!="") && ((!this.form.spotify.includes ("https://spotify.com")))){
        
        Swal.fire({
          type: 'error',
          title: 'Spotify...',
          text: 'direccion no valida!, ajuste para actualizar'
          
        })
        this.form.spotify = "";
        error = true;
    }

    if (!error){
      this.perfilService.actualizarbiografia(this.userLogged, this.form.biografia, this.form.spotify, this.form.facebook).subscribe(data => {
        //console.log (data);   
        this.obtenerBio();      
      },
      (err: any) => {
        console.log(err);
        this.router.navigate(['/accesodenegado']);
      });
    }
    
  }
 
  
}




