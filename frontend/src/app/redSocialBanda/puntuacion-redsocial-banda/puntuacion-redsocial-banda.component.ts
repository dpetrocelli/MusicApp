import { Component, OnInit } from '@angular/core';
import { LoginDatos } from 'src/app/modelos/logindatos';
import { PuntuacionArtista } from 'src/app/modelos/puntuacion';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PuntuacionService } from 'src/app/servicios/puntuacion.service';
import { BandaService } from 'src/app/servicios/banda.service';

@Component({
  selector: 'app-puntuacion-redsocial-banda',
  templateUrl: './puntuacion-redsocial-banda.component.html',
  styleUrls: ['./puntuacion-redsocial-banda.component.css']
})
export class PuntuacionRedsocialBandaComponent implements OnInit {
  userLogged: LoginDatos;
  listaPuntuacion: PuntuacionArtista[] = [];
  promedio: number = 0;
  contador: number = 0;
  isReady: boolean = false;
  isPunctuated : boolean = false;
  nombreBanda : string;

  constructor(private usuarioService: UsuarioService,
    private router: Router,
    private puntuacionService: PuntuacionService, 
    private activatedRoute: ActivatedRoute,
    private bandaService: BandaService) { }

  ngOnInit() {

    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.nombreBanda = this.activatedRoute.snapshot.paramMap.get("nombre");
    if (this.userLogged.nombreUsuario==this.nombreBanda){
      this.isPunctuated = true;
    }else{
      this.puntuacionService.verificarSiPuntueeBanda(this.userLogged, this.nombreBanda).subscribe(data => {
      
        this.isPunctuated = data;
        
      },
      (err: any) => {
        
       
      });
    }

    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.obtenerPuntuacion();
    this.delay(3000);
    this.isReady = true;

  }

  async obtenerPuntuacion() {
    /*let band : Banda = (await this.bandaService.datosBandaUna(this.userLogged).toPromise());
    
    
    console.log("OBtUVIMOS BANDA, ", band.nombre);*/
    this.listaPuntuacion = await this.puntuacionService.obtenerPuntuacionBanda(this.userLogged,this.nombreBanda).toPromise();
    if (this.listaPuntuacion.length > 0) {
      this.listaPuntuacion.forEach(puntuacion => {
        this.buscarUsuario(puntuacion);
        this.promedio += puntuacion.puntuacion;
        this.contador += 1;
        //console.log (" OBJETO", puntuacion);
      });
      this.promedio = this.promedio / this.contador;
      //console.log (" PROM PUNT : "+this.promedio);

    } else {
      this.promedio = 0;

    }

  }
  async buscarUsuario(puntuacion: PuntuacionArtista) {
    puntuacion.artistaLoaded = await this.usuarioService.obtenerDatosUsuarioPorId(this.userLogged, puntuacion.artistaPuntuador).toPromise();

  }

  hasResource(id: number) {
    console.log(id);

  }

  async delay(ms: number) {
    return await new Promise(resolve => setTimeout(resolve, ms));
  }

  puntuar() {
    // tomar el usuario y armar la url para puntuar
    // quien puntuador -> sesion
    // a quien puntuar -> 
    //alert (this.nombreUsuario);
    this.router.navigate(['puntuacionBanda/nuevo/' + this.nombreBanda]);

  }

}
