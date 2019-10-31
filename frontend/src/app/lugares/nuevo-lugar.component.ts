import { Component, OnInit } from '@angular/core';
import {Lugar} from '../modelos/lugar';
import {LugarService} from '../servicios/lugar.service';
import { ZonaService } from '../servicios/zona.service';
import { Zona } from '../modelos/zona';
import { LoginDatos } from '../modelos/logindatos';
import { UsuarioService } from '../servicios/usuario.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-nuevo-lugar',
  templateUrl: './nuevo-lugar.component.html',
  styleUrls: ['./nuevo-lugar.component.css']
})
export class NuevoLugarComponent implements OnInit {
  form: any = {};
  userLogged: LoginDatos;
  lugar: Lugar;
  creado = false;
  falloCreacion = false;
  msjFallo = '';
  msjOK = '';
  zonas: Zona[];
  foto : String ;
  files : FileList;

  constructor(private lugarService: LugarService, private zonaService : ZonaService, private usuarioService: UsuarioService) { }

  ngOnInit() {
   this.loadZonas();
   this.userLogged = this.usuarioService.getUserLoggedIn();
  }

  async loadZonas(){
    this.zonas = await this.zonaService.lista().toPromise();
  }

  public cargandoImagen(files: FileList){
    this.files = files;
    console.log ("FILES: ",this.files);
    if (this.files != null){
      
      Array.from (this.files).forEach(file => {
        this.lugarService.enviarimagen(file, this.userLogged).subscribe(data => {
          console.log (" Pude guardar imagen", file.name);
          this.foto = data.mensaje;
        },
        (err: any) => {
          console.log (err);
          this.msjFallo = err.error.mensaje;
          this.creado = false;
          this.falloCreacion = true;
        });
      });
    }
         
  }

  onCreate(): void {
    this.lugar = new Lugar;
    this.lugar = this.form;
    this.lugar.foto = this.foto;
    this.zonas.forEach(zonita => {
      if (zonita.id==this.form.zona){
        this.lugar.zona=zonita;
      }
    });
    
    
    console.log (" VOY A GUARDAR EL OBJ ", this.lugar);
    
    this.lugarService.crear(this.userLogged, this.lugar).subscribe(data => {
        this.msjOK = data.mensaje;
        this.creado = true;
        this.falloCreacion = false;
       
      },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        this.creado = false;
        this.falloCreacion = true;
      }
    );
  }

  volver(): void {
    window.history.back();
  }

}
