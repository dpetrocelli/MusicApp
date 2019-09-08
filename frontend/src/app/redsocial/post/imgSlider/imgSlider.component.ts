import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-imgSlider',
  templateUrl: './imgSlider.component.html',
  styleUrls: ['./imgSlider.component.css']
})
export class ImgSliderComponent implements OnInit {
  @Input() packImg;

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit() {

  }

}
