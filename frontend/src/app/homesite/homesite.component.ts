import { Component, OnInit, ComponentFactoryResolver } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { LoginDatos } from '../modelos/logindatos';
import { HomeSiteService } from '../servicios/homesite.service';
import { UsuarioService } from '../servicios/usuario.service';
import { Post } from '../modelos/post';

@Component({
  selector: 'app-homesite',
  templateUrl: './homesite.component.html',
  styleUrls: ['./homesite.component.css']
})
export class HomesiteComponent implements OnInit {
  userLogged : LoginDatos;
  hayInformacion : boolean = false;
  listaPosts : Post[];
  constructor(private usuarioService: UsuarioService,
              private homeSiteService: HomeSiteService,
              private router: Router,
              private sanitizer: DomSanitizer,
              private componentFactoryResolver: ComponentFactoryResolver,
              private modalService: NgbModal) { }

  ngOnInit() {
    this.userLogged = this.usuarioService.getUserLoggedIn();
    this.loadInfo();

  }

  loadInfo (){
    let inicio = 0;
    let fin = 10;
    this.homeSiteService.obtener(this.userLogged, inicio, fin).subscribe(data => {
      this.listaPosts = data;
      console.log ('[APP-POST] -> Biografía obtenida');
      this.hayInformacion = true;
    },
    (err: any) => {
      console.log( err );
      //this.router.navigate(['/accesodenegado']);
    });
  }

}
