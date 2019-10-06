import { Component, OnInit } from '@angular/core';
import { UsuarioService } from './servicios/usuario.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginDatos } from './modelos/logindatos';
import { NotificacionBandaUsuario } from './modelos/notificacionbandausuario';
import { Observable } from 'rxjs';
import {NotificacionService} from './servicios/notificacion.service';


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
    

  private openModal(open : boolean, user : string, idNotificacion : string) : void {
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
            this.obtener();
          }
        }
      }
    }
  }


  async aceptar(itemForm: any) {
    // si acepta depende el tipo de operación
    if (itemForm.tipoDeOperacion==="moderacionArtista"){
      // hago una cosa
    }else{
      // hago otra cosa
      console.log ("aceptar e incluir a banda");
      await this.notificacionService.incluirABanda(this.userLogged, itemForm.origen, itemForm.destino, itemForm.id).toPromise();
      this.obtener();
    }
  }
  
  async descartar(itemForm: any){
    
    if (itemForm.tipoDeOperacion==="moderacionArtista"){
      // si es un usuario
    }else{
      // Banda <- recibe solicitud <- artista
      console.log ("descartar");
      await this.notificacionService.descartarNotificacion(this.userLogged, itemForm.origen, itemForm.destino, itemForm.id).toPromise();
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
