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
              this.router.navigate(['/accesodenegado']);
          });
          
          this.usuarioService.obtenerDatosUsuario (this.userLogged).subscribe (data => {
            console.log (data);
            this.artista = data;
            this.isLoaded = true;
            },
            (err: any) => {
                console.log(err);
                this.router.navigate(['/accesodenegado']);
            });
            // nombre y detalle de la banda
            this.bandaService.obtenerDatosBanda(this.userLogged).subscribe (data => {
              console.log (data);
              this.detalleDeLaBanda = data; 
              if (this.detalleDeLaBanda!= null){
                
                this.bandaService.SoyDuenioBanda(this.userLogged).subscribe (data => {
                console.log (data);
                this.listaDeArtistasEnBanda = data; 
                if (this.listaDeArtistasEnBanda.length>0){
                    this.bandaActiva = true;
                }         
                },
                (err: any) => {
                  console.log(err);
                  this.router.navigate(['/accesodenegado']);
                });
                
                this.bandaActiva = true;

              }         
            },
            (err: any) => {
              console.log(err);
              this.router.navigate(['/accesodenegado']);
            });
            
            
          
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




