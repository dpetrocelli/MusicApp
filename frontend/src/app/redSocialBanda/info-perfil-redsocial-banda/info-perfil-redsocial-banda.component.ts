import { Component, OnInit, Input, HostBinding } from '@angular/core';
import { LoginDatos } from 'src/app/modelos/logindatos';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PerfilService } from 'src/app/servicios/perfil.service';
import { Artista } from 'src/app/modelos/artista';
import { BandaService } from 'src/app/servicios/banda.service';
import { Banda } from 'src/app/modelos/banda';
import { Post } from 'src/app/modelos/post';
import { Elemento } from 'src/app/modelos/elemento';

@Component({

  selector: 'app-info-perfil-redsocial-banda',
  templateUrl: './info-perfil-redsocial-banda.component.html',
  styleUrls: ['./info-perfil-redsocial-banda.component.css']
})
export class InfoPerfilRedsocialBandaComponent implements OnInit {

  posts: Post[] = [];
  elemString: String[];
  elementos: Elemento[] = [];
  userLogged: LoginDatos;
  biografia: string[];
  nombreBanda: string;
  artistaLider: Artista;
  listaDeArtistasEnBanda: Artista[];
  detalleDeLaBanda: Banda;
  isLoaded: boolean = false;
  form: any = {};
  bandaActiva = false;
  bio: String = null;
  spotify: string = null;
  facebook: string = null;
  haySpotify: boolean = false;
  hayFacebook: boolean = false;
  soyIntegrante: boolean = false;

  @HostBinding('class')
  @Input() biografiaComponent: InfoPerfilRedsocialBandaComponent;
  constructor(private usuarioService: UsuarioService,
    private router: Router,
    private bandaService: BandaService,
    private activatedRoute: ActivatedRoute,
    private perfilService: PerfilService) { }

  ngOnInit() {
    // buscar en la base de datos la biografia
    this.nombreBanda = this.activatedRoute.snapshot.paramMap.get("nombre");
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.obtenerBio();

  }

  abrirSpotify() {
    window.open(this.spotify, '_blank');
  }

  abrirFacebook() {
    window.open(this.facebook, '_blank');
  }

  async obtenerBio() {
    this.biografia = await this.perfilService.obtenerbiografiaBandaRedSocial(this.userLogged, this.nombreBanda).toPromise();


    if (this.biografia[0] != null) {
      this.form.biografia = this.biografia[0];

    }

    if (this.biografia[1] != null) {
      this.spotify = this.biografia[1];
      this.haySpotify = true;

    }

    if (this.biografia[2] != null) {
      this.facebook = this.biografia[2];
      this.hayFacebook = true;

    }
    this.obtenerDatosDeLaBanda();
  }
 
  async obtenerDatosDeLaBanda() {

    this.bandaService.obtenerBandaPorNombre(this.userLogged,this.nombreBanda).subscribe(data => {
      this.detalleDeLaBanda = data;
      console.log("DATOS DE BANDA", this.detalleDeLaBanda);

      this.bandaService.buscarArtistas(this.userLogged, this.detalleDeLaBanda).subscribe(data => {
        console.log(" artistas de la banda", data);
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

      },
        (err: any) => {
          console.log(err);
          //this.router.navigate(['/accesodenegado']);
        });

  }
}
