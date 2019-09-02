import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { UsuarioService } from '../servicios/usuario.service';
import { InstrumentoService } from '../servicios/instrumento.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Instrumento } from '../modelos/instrumento';

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
  
  isChecked = false;
  isArtista = false;
  isComercio = false;
  listaInstrumento : Instrumento[]; 
  instrumentosSeleccionados = [];
  respuesta : [];
  
  constructor(private usuarioService: UsuarioService,
              private instrumentoService: InstrumentoService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    // [STEP 0] - Voy a buscar al backend la lista de los instrumentos que tengo almacenados
    
    this.instrumentoService.lista().subscribe(data => {
      console.log (JSON.stringify(data));
      this.listaInstrumento = data;
    },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
        this.creado = false;
        this.falloCreacion = true;
      }

    );
    
  }

  guardarUsuario() {
    this.form.isArtista = this.isArtista;
    console.log(this.form);
    this.usuarioService.registrar(this.form, this.isArtista, this.instrumentosSeleccionados).subscribe(data => {
      this.msjOK = data.msg ;
      this.creado = true;
      this.falloCreacion = false;
      this.router.navigate(['']);
    },
      (err: any) => {
        this.msjFallo = err.error.mensaje;
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

  

  predictivo (evt){
    
      // [STEP 2] -> en la parte de -> texto.length > 2) voy a buscar para autocompletar
      // [STEP 3] -> en la parte de -> key = 13 -> estoy tomando el enter para agregar a lista
      
      //document.getElementById('instrumento').focus();  
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
