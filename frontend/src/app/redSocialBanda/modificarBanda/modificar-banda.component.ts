import { Component, OnInit } from '@angular/core';
import { LoginDatos } from 'src/app/modelos/logindatos';
import { UsuarioService } from 'src/app/servicios/usuario.service';
import { BandaService } from 'src/app/servicios/banda.service';
import { Banda } from 'src/app/modelos/banda';
import { GeneroMusical } from 'src/app/modelos/generoMusical';
import { Zona } from 'src/app/modelos/zona';
import { ZonaService } from 'src/app/servicios/zona.service';
import { GeneroMusicalService } from 'src/app/servicios/generoMusical.service';
import Swal from 'sweetalert2';
import { Router, ActivatedRoute } from '@angular/router';
import { Genero } from 'src/app/modelos/genero';

@Component({
  selector: 'app-modificar-banda',
  templateUrl: './modificar-banda.component.html',
  styleUrls: ['./modificar-banda.component.css']
})
export class ModificarBandaComponent implements OnInit {
  userLogged : LoginDatos;
  form: any = {};
  banda : Banda;
  zonas : Zona[];
  generoMusicales : GeneroMusical[];
  creado = false;
  falloCreacion = false;
  msjFallo = '';
  msjOK = '';
  frm_zona: Zona;
  frm_genero : GeneroMusical;
  constructor(private router : Router, private activatedRoute: ActivatedRoute, private usuarioService: UsuarioService,private bandaServicio: BandaService, private zonaService: ZonaService, private generoMusicalService : GeneroMusicalService) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.loadInfo();
    const id = this.activatedRoute.snapshot.params.id;
    this.bandaServicio.detalle(id).subscribe(data => {
        
                this.form = data;
                this.frm_zona = this.form.zona;
                this.frm_genero = this.form.generoMusical;
            },
            (err: any) => {
              //this.fallaInit = true;
              this.router.navigate(['']);
            }
          );
    
  }

  async loadInfo(){
    this.zonas = await this.zonaService.lista().toPromise();
    this.generoMusicales = await this.generoMusicalService.lista().toPromise();
  }

  compararZona(optionOne: Zona, optionTwo: Zona): boolean {
    return optionOne && optionTwo ? optionOne.id === optionTwo.id : optionOne === optionTwo;
  }

  compararGenero(optionOne: GeneroMusical, optionTwo: GeneroMusical): boolean {
    return optionOne && optionTwo ? optionOne.nombre === optionTwo.nombre : optionOne === optionTwo;
  }
  async onUpdate() {
    this.banda = this.form;
    const id = this.activatedRoute.snapshot.params.id;
    this.form.generoMusical = this.frm_genero;
    this.form.zona = this.frm_zona;
    console.log ("BANDA ", this.banda);
    
    this.bandaServicio.editar(this.form, id).subscribe(data => {
      
      Swal.fire(
        'Actualización correcta!',
        'Regresamos al menu anterior',
        'success'
      )
      this.volver();
    },
    (err: any) => {
      Swal.fire(
        'Actualización incorrecta!',
        'Por favor revise los campos',
        'error'
      )
    }
  );
    
  }

  volver(): void {
    window.history.back();
    //this.router.navigate(['']);
  }

}
