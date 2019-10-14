import { Component, OnInit } from '@angular/core';
import { LoginDatos } from 'src/app/modelos/logindatos';
import { PuntuacionArtista } from 'src/app/modelos/puntuacion';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PuntuacionService } from 'src/app/servicios/puntuacion.service';

@Component({
  selector: 'app-puntuacion-redsocial',
  templateUrl: './puntuacion-redsocial.component.html',
  styleUrls: ['./puntuacion-redsocial.component.css']
})
export class PuntuacionRedsocialComponent implements OnInit {
  userLogged : LoginDatos;
  listaPuntuacion : PuntuacionArtista[] = [];
  promedio : number = 0;
  contador : number = 0;
  isReady : boolean = false;
  isPunctuated : boolean = false;
  nombreUsuario : string;

  constructor(private usuarioService: UsuarioService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private puntuacionService : PuntuacionService) { }

    ngOnInit() {
      this.userLogged = this.usuarioService.getUserLoggedIn();
      this.nombreUsuario = this.activatedRoute.snapshot.paramMap.get("nombre");
      if (this.userLogged.nombreUsuario==this.nombreUsuario){
        this.isPunctuated = true;
      }else{
        this.puntuacionService.verificarSiPuntuee(this.userLogged, this.nombreUsuario).subscribe(data => {
        
          this.isPunctuated = data;
          
        },
        (err: any) => {
          
         
        });
      }
      
      this.obtenerPuntuacion(this.nombreUsuario);
      this.delay(3000);
      this.isReady = true;
    }

    async obtenerPuntuacion(nombreUsuario : string){

      this.listaPuntuacion = await this.puntuacionService.obtenerPuntuacionRedSocial(this.userLogged, this.nombreUsuario).toPromise();
      
      if (this.listaPuntuacion.length> 0){
        this.listaPuntuacion.forEach(puntuacion => {
          this.buscarUsuario(puntuacion);
          this.promedio+=puntuacion.puntuacion;
          this.contador+=1;
          //console.log (" OBJETO", puntuacion);
        });
        this.promedio = this.promedio / this.contador;
        //console.log (" PROM PUNT : "+this.promedio);
        
      }else{
        this.promedio = 0;
        
      }
      
    }
    async buscarUsuario (puntuacion : PuntuacionArtista){
      puntuacion.artistaLoaded = await this.usuarioService.obtenerDatosUsuarioPorId(this.userLogged, puntuacion.artistaPuntuador).toPromise();
      
    }
  
    puntuar(){
      // tomar el usuario y armar la url para puntuar
      // quien puntuador -> sesion
      // a quien puntuar -> 
      //alert (this.nombreUsuario);
      this.router.navigate(['puntuacion/nuevo/'+this.nombreUsuario]);

    }
   
  
    async delay(ms: number) {
      return await new Promise( resolve => setTimeout(resolve, ms) );
    }

}
