import { Component, OnInit } from '@angular/core';
import  {Promocion } from '../modelos/promocion';
import { PromocionService } from '../servicios/promocion.service';
import { LoginDatos } from '../modelos/logindatos';
import { UsuarioService } from '../servicios/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import {formatDate, DatePipe} from '@angular/common';
import Swal from 'sweetalert2';
import { FormBuilder, FormGroup, Validators, FormControl, AbstractControl } from '@angular/forms';


@Component({
  selector: 'app-altapromocion',
  templateUrl: './altapromocion.component.html',
  styleUrls: ['./altapromocion.component.css']
})
export class AltapromocionComponent implements OnInit {
  form: any = {};
  submitted = false;
  formIsValid = true;
  fecha : Date;
  registerForm: FormGroup;
  promocion: Promocion;
  userLogged : LoginDatos;
  creado = false;
  falloCreacion = false;
  
  msjFallo = '';
  msjOK = '';
  
  constructor(private promocionService: PromocionService,
              private usuarioService: UsuarioService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.fecha = new Date();
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.registerForm = this.formBuilder.group({
      titulo: ['', Validators.required],
      descripcion: ['', Validators.required],
      tipomoneda: ['', Validators.required],
      valorpromocion: ['',  Validators.min(0)],
      importe: ['',  Validators.min(0)],
      vigencia: ['', Validators.required]
    });

  
  }

  get fval() {
    return this.registerForm.controls;
  }

 
  onCreate(): void {
    
    this.promocion = new Promocion();
    this.submitted = true;
    this.formIsValid = true;

    if (! (this.fval.titulo.valid)) this.formIsValid = false;
    this.promocion.titulo = String (this.fval.titulo.value);
    
    if (! (this.fval.descripcion.valid)) this.formIsValid = false;
    this.promocion.descripcion = String (this.fval.descripcion.value);
    
    if (! (this.fval.valorpromocion.valid)) this.formIsValid = false;
    this.promocion.valorpromocion = Number (this.fval.valorpromocion.value);

    if (! (this.fval.importe.valid)) this.formIsValid = false;
    this.promocion.importe = Number (this.fval.importe.value);
    
    
    let wrongDate = this.compareTwoDates(this.fval.vigencia.value);
    if (!wrongDate){
      //this.fval.vigencia.invalid = true;
      this.fval.vigencia.setErrors({'incorrect': true});
      this.formIsValid = false;
          
    }else{
      this.promocion.vigencia = this.fval.vigencia.value;
    }
    console.log ("estado fecha", wrongDate);
    
    
    if (!this.formIsValid){
      Swal.fire({
        type: 'error',
        title: 'Oops...',
        text: "Revise los campos alertados"        
      });
      return;

    }else{

        console.log(" PROMOCION", this.promocion);
        
        this.promocionService.crear(this.promocion, this.userLogged).subscribe(data => {
        this.msjOK = " Creado con éxito";
        this.creado = true;
        this.falloCreacion = false;
        Swal.fire({
          type: 'success',
          title: 'Buenísimo...',
          text: "se creó la promoción con éxito"        
        });
        setTimeout(() => {
          this.router.navigate(['/promociones']);
          },3000);
        },
        (err: any) => {
            this.msjFallo = "Ocurrio un error: "+err.error;
            this.creado = false;
            this.falloCreacion = true;
          }
        );
        }
   
     
  }
      
compareTwoDates(vigencia: Date){
  
  try{
    let currentDate = formatDate(new Date(), 'yyyy-MM-dd', 'en');
    let vigenciaDate = formatDate(vigencia, 'yyyy-MM-dd', 'en');
     if(vigenciaDate < currentDate ){
      return false;
     
      
     }else{
       return true;
     }
  }catch{
    return false;
  }
  
    
  
  
}

  volver(): void {
    window.history.back();
  }
}
