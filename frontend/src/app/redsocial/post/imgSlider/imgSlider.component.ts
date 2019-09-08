import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { NgImageSliderComponent } from 'ng-image-slider';

@Component({
  selector: 'app-imgSlider',
  templateUrl: './imgSlider.component.html',
  styleUrls: ['./imgSlider.component.css']
})
export class ImgSliderComponent implements OnInit {
  @Input() packImg;

  @ViewChild('slider', null) slider: NgImageSliderComponent;

  constructor(public activeModal: NgbActiveModal) {  }

  ngOnInit() {
    this.slider.sliderImageHeight = 400;
    this.slider.sliderImageWidth = 400;
    this.slider.imagePopup = true;
  }

}
