import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { UsuarioService } from '../servicios/usuario.service';
import { ZonaService } from '../servicios/zona.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Instrumento } from '../modelos/instrumento';
import { Zona } from '../modelos/zona';
import { InstrumentoService } from '../servicios/instrumento.service';
import Swal from 'sweetalert2';
import { formatDate, DatePipe } from '@angular/common';
import { LoginDatos } from '../modelos/logindatos';
import { Artista } from '../modelos/artista';
import { Genero } from '../modelos/genero';
import { GeneroService } from '../servicios/genero.service';
import { Usuario } from '../modelos/usuario';

@Component({
  selector: 'app-editar-usuario',
  templateUrl: './editar-usuario.component.html',
  styleUrls: ['./editar-usuario.component.css']
})
export class EditarUsuarioComponent implements OnInit {
  @ViewChild('nombre', null) nombreInput: ElementRef;
  @ViewChild('instrumento', null) instrumentoInput: ElementRef;
  form: any = {};
  formCompleto: false;
  msjFallo = '';
  msjOK = '';
  actualizado = false;
  falloActualizacion = false;
  submitted = false;
  isChecked = false;

  listaInstrumento: Instrumento[]=[];
  listaInstrumentoSeleccionado: Instrumento[]=[];
  zonas: Zona[];
  instrumentosSeleccionados = [];
  respuesta: [];
  generos: Genero[];
  fechaValida = true;
  usuarioValido = true;
  formularioValido = false;
  dniValido = true;
  mailValido = true;

  userLogged: LoginDatos;
  artista: Artista;
  username: String;
  email: String;

  frm_nombre: String;
  frm_apellido: String;
  frm_nickname: String;
  frm_documento: number;
  frm_fechanacimiento: string;
  frm_genero: Genero;
  frm_zona: Zona;

  fallaInit = false;
  isLoaded: boolean;
  datePipe: DatePipe;

  constructor(private usuarioService: UsuarioService,
    private instrumentoService: InstrumentoService,
    private zonaService: ZonaService,
    private generoService: GeneroService,
    private activatedRoute: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {

    const id = this.activatedRoute.snapshot.params.id;
    this.userLogged = this.usuarioService.getUserLoggedIn();

    // [STEP 0] - Voy a buscar al backend la lista de los instrumentos que tengo almacenados
    this.zonaService.lista().subscribe(data => {
      this.zonas = data;
    },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        this.actualizado = false;
        this.falloActualizacion = true;
        this.fallaInit = true;
      }
    );

    this.instrumentoService.lista().subscribe(data => {
      this.listaInstrumento = data;
    },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        this.actualizado = false;
        this.falloActualizacion = true;
        this.fallaInit = true;
      }
    );
    console.log("listaInstrumento", this.listaInstrumento);

    this.generos = this.generoService.obtenerTodos();

    //HAY QUE OBTENER LOS DATOS DE USUARIO DESPUES DE CARGAR LAS ENTIDADES
    //PORQUE SI TIENE INSTRUMENTOS VA A AGREGARLOS EN LA LISTA DE SELECCIONADOS
    //SI NO SE CARGAN LOS INSTRUMENTOS ANTES FALLA EL HTML
    this.obtenerDatosUsuario();

    this.fallaInit = false;

  }

  async obtenerDatosUsuario() {
    this.artista = await this.usuarioService.obtenerDatosUsuario(this.userLogged).toPromise()

    this.username = this.artista.usuario.username;
    this.email = this.artista.usuario.email;

    this.frm_nombre = this.artista.nombre;
    this.frm_apellido = this.artista.apellido;
    this.frm_nickname = this.artista.nickname;
    this.frm_documento = this.artista.documentoIdentidad;
    this.frm_fechanacimiento = formatDate(this.artista.fechaNacimiento, 'yyyy-MM-dd', 'es-AR');


    if (this.artista.instrumento.length > 0) {
      let instrumentoDeArtista: Instrumento;
      for (let i = 0; i < this.artista.instrumento.length; i++) {
        instrumentoDeArtista = this.artista.instrumento[i];
        console.log(" agregue en la lista el instrumento: ", instrumentoDeArtista.nombreInstrumento)
        this.guardarEnLista(instrumentoDeArtista.nombreInstrumento);
      }
    }

    this.frm_genero = this.generoService.obtenerPorNombre(this.artista.genero);
    this.frm_zona = this.artista.zona;

  }

  compararGenero(optionOne: Genero, optionTwo: Genero): boolean {
    return optionOne && optionTwo ? optionOne.nombreGenero === optionTwo.nombreGenero : optionOne === optionTwo;
  }

  compararZona(optionOne: Zona, optionTwo: Zona): boolean {
    return optionOne && optionTwo ? optionOne.id === optionTwo.id : optionOne === optionTwo;
  }

  get fval() {
    return this.form.controls;
  }


  actualizarUsuario() {

    this.artista.nombre = this.frm_nombre;
    this.artista.apellido = this.frm_apellido;
    this.artista.nickname = this.frm_nickname;
    this.artista.documentoIdentidad = this.frm_documento;
    this.artista.fechaNacimiento = new Date(this.frm_fechanacimiento);
    this.artista.genero = this.frm_genero.nombreGenero;
    this.artista.zona = this.frm_zona;

    console.log("instrumentos seleccionados", this.listaInstrumentoSeleccionado);

    this.artista.instrumento = this.listaInstrumentoSeleccionado;

    this.usuarioService.actualizarArtista(this.artista, this.userLogged).subscribe(data => {
      this.msjOK = data.msg;
      this.actualizado = true;
      this.falloActualizacion = false;

      Swal.fire({
        type: 'success',
        title: 'Buenísimo...',
        text: "se actualizó el usuario exitosamente"
      });
      this.router.navigate(['']);
    },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        Swal.fire({
          type: 'error',
          title: 'Oops...',
          text: "hay problemas " + err.error.mensaje
        });
        this.actualizado = false;
        this.falloActualizacion = true;
      }
    );

  }

  validarDNI() {
    this.dniValido = true;
    let dni: String = new String(this.frm_documento);

    if (dni.length != 8) {
      this.formularioValido = false;
      this.dniValido = false;
    } else {
      this.dniValido = true;
    }
  }

  revisarFecha() {
    this.fechaValida = true;
    try {
      let currentDate = formatDate(new Date(), 'yyyy-MM-dd', 'en');

      let fechanac = this.frm_fechanacimiento;
      if (fechanac > currentDate) {
        this.fechaValida = false;
      }
    } catch{
      this.fechaValida = false;

    }
    this.habilitarONoFormulario();

  }

  habilitarONoFormulario() {
    if (this.dniValido && this.fechaValida) {
      this.formularioValido = true;
    }
  }

  predictivo(evt) {

    // [STEP 2] -> en la parte de -> texto.length > 2) voy a buscar para autocompletar
    // [STEP 3] -> en la parte de -> key = 13 -> estoy tomando el enter para agregar a lista


    document.getElementById("instrumento").focus()
    try {
      var texto = (<HTMLInputElement>document.getElementById('instrumento')).value;


      // PARTE ENTER
      if (evt.keyCode == 13) {
        //this.myInput.nativeElement.focus(); 
        this.guardarEnLista(texto);
      } else {
        // PARTE DE AUTOCOMPLETAR
        if (texto.length > 2) {
          this.respuesta = this.buscarTextoEnArray(texto);
          console.log("predictivo: ", this.respuesta);
        }
      }

      this.instrumentoInput.nativeElement.focus();
    } catch {
      // ERROR 
      console.log(evt);
    }

  }

  guardarEnLista(texto: string) {
    var found = false;
    let instrumentoSeleccionado: Instrumento;

    // Si el texto que pasé está permitido por lo que habia en la base de datos
    // -> lo marco como found
    console.log("listaInstrumento", this.listaInstrumento);
    if (this.listaInstrumento) {
      this.listaInstrumento.forEach(instrumento => {
        if ((instrumento.nombreInstrumento.length == texto.length) && (instrumento.nombreInstrumento === texto)) {

          found = true;
          instrumentoSeleccionado = instrumento;
        }
      });
      console.log("instrumento seleccionado", instrumentoSeleccionado);
      if (found) {
        var estaEnSeleccionado = false;
        if (this.instrumentosSeleccionados.length > 0) {
          this.instrumentosSeleccionados.forEach(seleccionado => {

            if ((seleccionado === texto)) {
              estaEnSeleccionado = true;
            }
          });

          if (!estaEnSeleccionado) {
            console.log("Se agrega", texto);
            this.instrumentosSeleccionados.push(texto);
            this.listaInstrumentoSeleccionado.push(instrumentoSeleccionado);
            console.log("listaInstrumentoSeleccionado:", this.listaInstrumentoSeleccionado);
          }
        } else {
          this.instrumentosSeleccionados.push(texto);
        }

      } else {
        console.log(" Instrumento inválido, no permito agregar a la lista de seleccionados");
      }
    } else {
      console.log(" lista de instrumentos vacia");
    }
  }

  buscarTextoEnArray(texto: string): any {
    var resp = [];

    this.listaInstrumento.forEach(instrumento => {
      if (instrumento.nombreInstrumento.includes(texto)) {
        resp.push(instrumento.nombreInstrumento);
      }
    });
    return resp;
  }

  borrarLinea(evt) {

    var eliminar = evt.target.parentElement.firstChild.innerHTML;
    this.instrumentosSeleccionados.forEach((seleccionado, index) => {

      if (seleccionado === eliminar) {
        this.instrumentosSeleccionados.splice(index, 1);
        this.listaInstrumentoSeleccionado.forEach((instrumento, index) => {
          if (instrumento.nombreInstrumento === eliminar) {
            this.listaInstrumentoSeleccionado.splice(index, 1);
            console.log("listaInstrumentoSeleccionado:", this.listaInstrumentoSeleccionado);
          }
        });
      }
    });
  }
}
