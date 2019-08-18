import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-accesodenegado',
  templateUrl: './accesodenegado.component.html',
  styleUrls: ['./accesodenegado.component.css']
})
export class AccesodenegadoComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
    setTimeout(() => {
      this.router.navigate(['']);
    },5000);
  }

}
