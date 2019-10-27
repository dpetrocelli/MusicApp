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
  isArtista = false;
  isComercio = false;
  listaInstrumento : Instrumento[]; 
  zonas : Zona[];
  instrumentosSeleccionados = [];
  respuesta : [];
  generos : Genero[];
  fechaValida = true;
  usuarioValido = true;
  formularioValido = false;
  dniValido = true;
  mailValido = true;

  userLogged : LoginDatos;
  artista : Artista;

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

    this.obtenerDatosUsuario();
    
    // [STEP 0] - Voy a buscar al backend la lista de los instrumentos que tengo almacenados
    this.zonaService.lista().subscribe(data => {
      this.zonas = data;
    },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        this.actualizado = false;
        this.falloActualizacion = true;
      }
    );
    this.instrumentoService.lista().subscribe(data => {
      this.listaInstrumento = data;
    },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        this.actualizado = false;
        this.falloActualizacion = true;
      }
    );

    this.generos = this.generoService.obtenerTodos();
  }

  async obtenerDatosUsuario(){
    this.artista = await this.usuarioService.obtenerDatosUsuario (this.userLogged).toPromise()

    console.log (" ARTISTA logueado",this.artista.banda);

    console.log (" ARTISTA CARGADO",this.artista);

    console.log (" Esta es la zona", this.artista.zona);

    this.isLoaded = true;

    this.guardarEnLista(this.artista.instrumento[0].nombreInstrumento);

  }

  get fval() {
    return this.form.controls;
  }


  actualizarUsuario() {
    this.form.isArtista = this.isArtista;
    console.log(this.form);
    
    this.submitted = true;
    
    //this.usuarioService.registrar(this.form, this.isArtista, this.instrumentosSeleccionados).subscribe(data => {
    //  this.msjOK = data.msg ;
    //  this.actualizado = true;
    //  this.falloActualizacion = false;

    //  Swal.fire({
    //    type: 'success',
    //    title: 'Buenísimo...',
    //    text: "se actualizó el usuario exitosamente"        
    //  });
    //  this.router.navigate(['']);
    //},
    //  (err: any) => {
    //    this.msjFallo = err.error.mensaje;
    //    Swal.fire({
    //      type: 'error',
    //      title: 'Oops...',
    //      text: "hay problemas "+err.error.mensaje        
    //    });
    //    this.actualizado = false;
    //    this.falloActualizacion = true;
    //  }
    //);
  }

  verificarMail(){
    let email = this.form.email;
    let pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
    
    if (email.match(pattern)===null){
      this.mailValido = false;
    }else{
      this.mailValido = true;
      this.habilitarONoFormulario();
    }
  }

  validarDNI (){
    this.dniValido = true;
    let dni : String = new String(this.form.documento);
   
    if (dni.length!=8){
      this.formularioValido = false;
      this.dniValido = false;
    }else{
      this.dniValido = true;
    }
  }

  revisarNombreUsuario(){
    this.usuarioValido=true;
   
      this.usuarioService.existeUsuario(this.form.username).subscribe(data => {
        
        let result = data;
        if(result){
          this.usuarioValido = false;
          this.formularioValido = false;
        }
      },
      );
      this.habilitarONoFormulario();
    }

  revisarFecha(){
    this.fechaValida=true;
    try{
      let currentDate = formatDate(new Date(), 'yyyy-MM-dd', 'en');
      let fechanac = formatDate(this.form.fechanacimiento, 'yyyy-MM-dd', 'en');
       if(fechanac > currentDate ){
        this.fechaValida = false;
       }
    }catch{
      this.fechaValida = false;
    }
    this.habilitarONoFormulario();
  }

  habilitarONoFormulario (){
    if (this.isArtista){
      if (this.dniValido && this.mailValido && this.fechaValida && this.usuarioValido && this.fechaValida){
        this.formularioValido = true;
      }
    }else{
      if (this.dniValido && this.mailValido){
        this.formularioValido = true;
      }
    }
  }

  predictivo (evt){
    
      // [STEP 2] -> en la parte de -> texto.length > 2) voy a buscar para autocompletar
      // [STEP 3] -> en la parte de -> key = 13 -> estoy tomando el enter para agregar a lista
      document.getElementById("instrumento").focus()
      try{
       var texto = (<HTMLInputElement>document.getElementById('instrumento')).value;
        // PARTE ENTER
        if (evt.keyCode == 13){
          //this.myInput.nativeElement.focus(); 
          this.guardarEnLista(texto);
        }else{
          // PARTE DE AUTOCOMPLETAR
            if (texto.length > 2){
              this.respuesta = this.buscarTextoEnArray(texto);
              console.log ("predictivo: ", this.respuesta);
            }
        }
        this.instrumentoInput.nativeElement.focus(); 
      }catch {
        // ERROR 
        console.log (evt);
      }
  }
    
  guardarEnLista (texto : string){
    var found = false;
    // Si el texto que pasé está permitido por lo que habia en la base de datos
    // -> lo marco como found
    this.listaInstrumento.forEach(instrumento => {
      if ((instrumento.nombreInstrumento.length == texto.length) && (instrumento.nombreInstrumento.includes(texto))){
         found=true;
      }
    });
    if (found){
      var estaEnSeleccionado = false;
      if (this.instrumentosSeleccionados.length>0){
        this.instrumentosSeleccionados.forEach(seleccionado => {
          if ((seleccionado.includes(texto))){
            estaEnSeleccionado = true;          }
        });

        if (!estaEnSeleccionado){
          console.log ("Se agrega", texto);
          this.instrumentosSeleccionados.push(texto);  
        }
      }else{
        this.instrumentosSeleccionados.push (texto);
      }
    }else{
      console.log (" Instrumento inválido, no permito agregar a la lista de seleccionados");
    }
  }
  
  buscarTextoEnArray (texto: string) : any {
      var resp = [];

      this.listaInstrumento.forEach(instrumento => {
          if (instrumento.nombreInstrumento.includes(texto)){
             resp.push (instrumento.nombreInstrumento);
          }
      });
      return resp;
  }

  borrarLinea (evt) {
    
    var eliminar = evt.target.parentElement.firstChild.innerHTML;
    this.instrumentosSeleccionados.forEach( (seleccionado, index) => {
    
      if (seleccionado === eliminar) {
        this.instrumentosSeleccionados.splice(index,1);  
        
      }
    });
  }
}
