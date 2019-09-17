import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-detallepromocion',
  templateUrl: './detallepromocion.component.html',
  styleUrls: ['./detallepromocion.component.css']
})
export class DetallepromocionComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  volver(): void {
    window.history.back();
  }
  
}
