import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { UsuarioService } from '../servicios/usuario.service';
import { ZonaService } from '../servicios/zona.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Instrumento } from '../modelos/instrumento';
import { Zona } from '../modelos/zona';
import { InstrumentoService } from '../servicios/instrumento.service';
import Swal from 'sweetalert2';
import { formatDate } from '@angular/common';
import { GeneroMusical } from '../modelos/generoMusical';
import { GeneroMusicalService } from '../servicios/generoMusical.service';
import { Genero } from '../modelos/genero';
import { GeneroService } from '../servicios/genero.service';

@Component({
  selector: 'app-nuevousuario',
  templateUrl: './nuevousuario.component.html',
  styleUrls: ['./nuevousuario.component.css']
})
export class NuevousuarioComponent implements OnInit {
  @ViewChild('nombre', null) nombreInput: ElementRef;
  @ViewChild('instrumento', null) instrumentoInput: ElementRef;
  form: any = {};
  formCompleto: false;
  msjFallo = '';
  msjOK = '';
  creado = false;
  falloCreacion = false;
  submitted = false;
  isChecked = false;
  isArtista = false;
  isComercio = false;
  listaInstrumento: Instrumento[];
  zonas: Zona[];
  instrumentosSeleccionados = [];
  generos : Genero[];
  generosMusicales: GeneroMusical[] = [];
  respuesta: [];
  fechaValida = true;
  usuarioValido = true;
  formularioValido = false;
  dniValido = true;
  mailValido = true;

  constructor(private usuarioService: UsuarioService,
    private instrumentoService: InstrumentoService,
    private zonaService: ZonaService,
    private generoMusicalService: GeneroMusicalService,
    private generoService: GeneroService,
    private activatedRoute: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    // [STEP 0] - Voy a buscar al backend la lista de los instrumentos que tengo almacenados
    this.zonaService.lista().subscribe(data => {
      this.zonas = data;
    },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        this.creado = false;
        this.falloCreacion = true;
      }

    );

    this.instrumentoService.lista().subscribe(data => {
      this.listaInstrumento = data;
    },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        this.creado = false;
        this.falloCreacion = true;
      }

    );

    this.generoMusicalService.lista().subscribe(data => {
      this.generosMusicales = data;
    },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        this.creado = false;
        this.falloCreacion = true;
      });

      this.generos = this.generoService.obtenerTodos();

      console.log("entrando en on init: ");
  }

  get fval() {
    return this.form.controls;
  }


  guardarUsuario() {
    this.form.isArtista = this.isArtista;
    console.log(this.form);

    this.submitted = true;

    this.usuarioService.registrar(this.form, this.isArtista, this.instrumentosSeleccionados).subscribe(data => {
      this.msjOK = data.msg;
      this.creado = true;
      this.falloCreacion = false;
      //sessionStorage.clear();
      localStorage.clear();
      Swal.fire({
        type: 'success',
        title: 'Buenísimo...',
        text: "se creó el usuario exitosamente"
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
        this.creado = false;
        this.falloCreacion = true;
      }

    );
  }

  cambioRadioButton(evt) {

    // [STEP 1] - Cuando clickeo habilito la opción artista o comercio
    this.isChecked = true;
    if (evt.target.id == "artista") {
      this.isArtista = true;
      this.isComercio = false;
      this.nombreInput.nativeElement.focus();
    } else {
      this.isComercio = true;
      this.isArtista = false;
    }

  }

  verificarMail() {
    let email = this.form.email;
    let pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"

    if (email.match(pattern) === null) {

      this.mailValido = false;
    } else {
      this.mailValido = true;
      this.habilitarONoFormulario();
    }
  }


  validarDNI() {
    this.dniValido = true;
    let dni: String = new String(this.form.documento);

    if (dni.length != 8) {
      this.formularioValido = false;
      this.dniValido = false;
    } else {
      this.dniValido = true;
    }
  }

  revisarNombreUsuario() {

    this.usuarioValido = true;

    this.usuarioService.existeUsuario(this.form.username).subscribe(data => {

      let result = data;
      if (result) {
        this.usuarioValido = false;
        this.formularioValido = false;
      }
    },

    );
    this.habilitarONoFormulario();
  }

  revisarFecha() {

    this.fechaValida = true;
    try {
      let currentDate = formatDate(new Date(), 'yyyy-MM-dd', 'en');
      let fechanac = formatDate(this.form.fechanacimiento, 'yyyy-MM-dd', 'en');
      if (fechanac > currentDate) {
        this.fechaValida = false;
      }

    } catch{
      this.fechaValida = false;
    }
    this.habilitarONoFormulario();
  }

  habilitarONoFormulario() {

    if (this.isArtista) {
      console.log("Es artista: ");
      if (this.dniValido && this.mailValido && this.fechaValida && this.usuarioValido && this.fechaValida) {
        this.formularioValido = true;
      }
    } else {
      if (this.dniValido && this.mailValido) {
        this.formularioValido = true;
      }
    }

  }

  predictivo(evt) {

    // [STEP 2] -> en la parte de -> texto.length > 2) voy a buscar para autocompletar
    // [STEP 3] -> en la parte de -> key = 13 -> estoy tomando el enter para agregar a lista

    //document.getElementById('instrumento').focus();  
    document.getElementById("instrumento").focus()
    try {
      var texto = (<HTMLInputElement>document.getElementById('instrumento')).value;
      console.log("entrando en predictivo: ");

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
      console.log("error en evento detectado", evt);
    }

  }

  guardarEnLista(texto: string) {
    var found = false;
    // Si el texto que pasé está permitido por lo que habia en la base de datos
    // -> lo marco como found
    this.listaInstrumento.forEach(instrumento => {
      if ((instrumento.nombreInstrumento.length == texto.length) && (instrumento.nombreInstrumento.includes(texto))) {

        found = true;
      }
    });
    if (found) {
      var estaEnSeleccionado = false;
      if (this.instrumentosSeleccionados.length > 0) {
        this.instrumentosSeleccionados.forEach(seleccionado => {

          if ((seleccionado.includes(texto))) {
            estaEnSeleccionado = true;
          }
        });

        if (!estaEnSeleccionado) {
          console.log("Se agrega", texto);
          this.instrumentosSeleccionados.push(texto);
        }
      } else {
        this.instrumentosSeleccionados.push(texto);
      }

    } else {
      console.log(" Instrumento inválido, no permito agregar a la lista de seleccionados");
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
      }
    });

  }
}
