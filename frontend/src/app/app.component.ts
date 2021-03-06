import { Component, OnInit } from '@angular/core';
import { UsuarioService } from './servicios/usuario.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginDatos } from './modelos/logindatos';
import { NotificacionBandaUsuario } from './modelos/notificacionbandausuario';
import { Observable } from 'rxjs';
import {NotificacionService} from './servicios/notificacion.service';
import { BandaService } from './servicios/banda.service';
import { Artista } from './modelos/artista';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  

  title = 'frontend';
  isArtista = false;
  isComercio = false;
  isComercioVinculadoMP = false;
  isAdmin = false;
  isOwnerBand = false;
  isIntegrante = false;
  isLoggedIn = false;
  userLogged : LoginDatos;
  notificacionesCargadas = false;
  items : any[] = [];
  notificaciones : any[];
  modalPopupResponseIsOpen : boolean = false;
  modalPopupUsuario : string;
  modalPopupIdNotificacion : string;

  constructor(private usuarioService: UsuarioService,
              private notificacionService: NotificacionService,
              private activatedRoute: ActivatedRoute,
              private bandaService : BandaService,
              private router: Router) { }
              
  ngOnInit(): void {
    this.reloadInfo();
    
    
  }
  
  obtener(){
    this.notificaciones = [];
    this.items = [];
    this.notificacionService.obtener(this.userLogged).subscribe(data => {
      console.log ("RESPUESTA:", data);
      this.notificaciones = data;
      this.notificaciones.forEach(element => {
        var obj: object = {
            id : element.id,
            origen: element.nombreOrigen,
            destino: element.nombreDestino,
            cliente: element.mensaje,
            fecha: element.fechaNotificacion,
            tipo: element.tipoDeOperacion,
            estado: element.estado
          };
          
          this.items.push(obj);
        });      
       
        //console.log (" CARTEL ", data);
      },
      (err: any) => {
        console.log(err.error.mensaje);
        
      }
    ); 
  }
    

  openModal(open : boolean, user : string, idNotificacion : string) : void {
    this.modalPopupResponseIsOpen = open;
    this.modalPopupUsuario = user;
    this.modalPopupIdNotificacion = idNotificacion;
  }

  async botonRespuesta() {
    this.modalPopupResponseIsOpen = false;
    let msgRespuesta = (<HTMLInputElement>document.getElementById('respuestaPopup')).value;
    await this.notificacionService.actualizarNotificacion(this.userLogged, msgRespuesta, this.modalPopupIdNotificacion).toPromise();
    this.obtener();
  }
  

  
  reloadInfo(): void{
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.isLoggedIn = true;
    
    if (this.userLogged!= null){
      if (this.userLogged.roles == "comercio"){
        this.isComercio = true;
        this.verificarComercioActivado();
      }else {
        if (this.userLogged.roles == "admin"){
          this.isAdmin = true;
        }else {
          if (this.userLogged.roles = "artista"){
            this.isArtista = true;
            
            this.bandaService.SoyDuenioBandaLogin(this.userLogged).subscribe(data => {
              console.log ("DUENIO BANDA:", data);
              this.isOwnerBand = data;
               
                
              },
              (err: any) => {
                //console.log(" NO DuENIO BANDA");
                
              }
            ); 

            this.usuarioService.obtenerDatosUsuario(this.userLogged).subscribe(data => {
              let artista : Artista = data;
              if (artista.banda.length>0){
                this.isIntegrante = true;
              }
              
               
                
              },
              (err: any) => {
                //console.log(" NO DuENIO BANDA");
                
              }
            ); 
            this.obtener();
          }
        }
      }
    }
  }


  async aceptar(itemForm: any) {
    
    // si acepta depende el tipo de operación
    let itemTipo = new String(itemForm.tipo);
    if (itemTipo.startsWith("moderacionBan")){
      // hago una cosa
      console.log ("MODERACION-BANDA", itemForm);
      
      await this.notificacionService.incluirABanda(this.userLogged, itemForm.origen, itemForm.destino , itemForm.id, "moderacionbanda").toPromise();
      this.obtener();
    }else{
      // hago otra cosa
      
      console.log ("MODERACION-ARTISTA", itemForm);
      await this.notificacionService.incluirABanda(this.userLogged, itemForm.origen, itemForm.destino, itemForm.id, "moderacionartista").toPromise();
      this.obtener();
    }
  }
  
  async descartar(itemForm: any){
    let itemTipo = new String(itemForm.tipo);
    if (itemTipo.startsWith("moderacionBan")){
      console.log ("DESCARTE-BANDA", itemForm);
      // si es un usuario
      await this.notificacionService.descartarNotificacion(this.userLogged, itemForm.origen, itemForm.destino, itemForm.id, "moderacionbanda").toPromise();
    }else{
      // Banda <- recibe solicitud <- artista
      console.log ("DESCARTE-ARTISTA", itemForm);
      await this.notificacionService.descartarNotificacion(this.userLogged, itemForm.origen, itemForm.destino, itemForm.id, "moderacionartista").toPromise();
      this.obtener();
      

    }
  }
  
  verperfil(itemForm: any){
    this.router.navigate(['/redsocial/'+itemForm.origen]);
  }
  responder(itemForm: any){

  }
  async eliminar(itemForm: any){
    await this.notificacionService.eliminar(this.userLogged, itemForm.id).toPromise();
    console.log ("ELIMINADO CON EXITO");
    setTimeout(() => 
    {
      this.obtener();
    },
    100);

    
  }

  mostrarNotificaciones(){
    this.notificacionesCargadas = true;
  }
  verificarComercioActivado() {
    
    this.usuarioService.verificarComercioActivado(this.userLogged).subscribe(data => {
      this.isComercioVinculadoMP = data;
    },
    (err: any) => {
      console.log(err.error.mensaje);
      
    }
  );  
    
  }

  cerrarsesion(){
    //sessionStorage.clear();
    localStorage.clear();
    this.userLogged = null;
    this.isArtista = false;
    this.isComercio = false;
    this.isAdmin = false;
    
    this.router.navigate(['']);
   setTimeout(() => 
    {
      window.location.reload();
    },
    30);
    
    
  }
  
  
}
