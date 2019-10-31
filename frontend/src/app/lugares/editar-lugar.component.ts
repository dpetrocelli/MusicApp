import { Component, OnInit } from '@angular/core';
import {LugarService} from '../servicios/lugar.service';
import {ActivatedRoute, Router} from '@angular/router';
import { Zona } from '../modelos/zona';
import { ZonaService } from '../servicios/zona.service';
import { UsuarioService } from '../servicios/usuario.service';
import { LoginDatos } from '../modelos/logindatos';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-lugar',
  templateUrl: './editar-lugar.component.html',
  styleUrls: ['./editar-lugar.component.css']
})
export class EditarLugarComponent implements OnInit {
  form: any = {};
  actualizado = false;
  falloActualizacion = false;
  msjFallo = '';
  msjOK = '';
  zonas: Zona[];
  frm_zona: Zona;
  fallaInit = false;
  old_foto : String;
  foto : String ;
  files : FileList;
  userLogged: LoginDatos;
  
  constructor(private lugarService: LugarService,
              private activatedRoute: ActivatedRoute,
              private router: Router, private zonaService : ZonaService, private usuarioService: UsuarioService) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.loadZonas();
    const id = this.activatedRoute.snapshot.params.id;
    this.lugarService.detalle(id).subscribe(data => {
//        this.form.nombreLugar = data.nombre;
//        this.form.tipoLugar = data.tipoLugar;
          this.form = data;
          this.frm_zona = this.form.zona;
          this.old_foto = this.form.foto;
      },
      (err: any) => {
        this.fallaInit = true;
        this.router.navigate(['']);
      }
    );
  }

  async loadZonas(){
    this.zonas = await this.zonaService.lista().toPromise();
  }

  compararZona(optionOne: Zona, optionTwo: Zona): boolean {
    return optionOne && optionTwo ? optionOne.id === optionTwo.id : optionOne === optionTwo;
  }

  public cargandoImagen(files: FileList){
    this.files = files;
    console.log ("FILES: ",this.files);
    if (this.files != null){
      
      Array.from (this.files).forEach(file => {
        this.lugarService.enviarimagen(file, this.userLogged).subscribe(data => {
          console.log (" Pude guardar imagen", file.name);
          this.foto = data.mensaje;
          this.old_foto = this.foto;
        },
        (err: any) => {
          console.log (err);
          this.msjFallo = err.error.mensaje;
          //this.creado = false;
          //this.falloCreacion = true;
        });
      });
    }
  }

  onUpdate(): void {
    const id = this.activatedRoute.snapshot.params.id;
    this.form.foto = this.old_foto;
    this.form.zona = this.frm_zona;
    
    this.lugarService.editar(this.form, id).subscribe(data => {
        this.actualizado = true;
        this.falloActualizacion = false;
        this.msjOK = data.mensaje;
        Swal.fire(
          'Actualización correcta!',
          'Regresamos al menu anterior',
          'success'
        )
        this.volver();
      },
      (err: any) => {
        this.actualizado = false;
        this.falloActualizacion = true;
        this.msjFallo = err.error.mensaje;
      }
    );
  }

  volver(): void {
    window.history.back();
  }
}
