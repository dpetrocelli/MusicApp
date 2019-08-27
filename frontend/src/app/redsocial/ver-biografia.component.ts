import { Component, OnInit } from '@angular/core';
import { PostService } from '../servicios/post.service';
import { LoginDatos } from '../modelos/logindatos';
import { UsuarioService } from '../servicios/usuario.service';
import { Router } from '@angular/router';
import { Post } from '../modelos/post';
import { Elemento } from '../modelos/elemento';

@Component({
  selector: 'app-ver-biografia',
  templateUrl: './ver-biografia.component.html',
  styleUrls: ['./ver-biografia.component.css']
})
export class VerBiografiaComponent implements OnInit {
  posts : Post[] = [];
  elemString : String[];
  elementos : Elemento[] = [];
  userLogged : LoginDatos;
  biografia : String;
  form: any = {};

  constructor(private usuarioService: UsuarioService,
              private router: Router,
              private postService : PostService) { }

  ngOnInit() {
    // buscar en la base de datos la biografia
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.postService.obtenerbiografia(this.userLogged).subscribe(data => {
      this.biografia = data.mensaje;
      
   },
   (err: any) => {
     console.log(err);
     this.router.navigate(['/accesodenegado']);
   });
   // luego voy a buscar los posts
   this.postService.obtenerposts (this.userLogged).subscribe(data => {
    //this.biografia = data.mensaje;
     this.posts = data;
        // ahora voy a buscar todos los recursos
        this.posts.forEach(post => {
          this.postService.obtenerelementos(post.id).subscribe(data => {
            this.elemString = data;
            console.log (this.elemString);
         },
         (err: any) => {
           console.log(err);
           //this.router.navigate(['/accesodenegado']);
         });
        });
    },
    (err: any) => {
      console.log(err);
      //this.router.navigate(['/accesodenegado']);
    });
    
  }

  actualizar(){

    
    this.postService.actualizarbiografia(this.userLogged, this.form.biografia).subscribe(data => {
      console.log (data);         
   },
   (err: any) => {
     console.log(err);
     this.router.navigate(['/accesodenegado']);
   });
  }
 

}


