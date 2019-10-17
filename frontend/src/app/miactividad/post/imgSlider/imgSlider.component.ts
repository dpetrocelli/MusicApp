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
    this.slider.sliderImageHeight = 200;
    this.slider.sliderImageWidth = 200;
    this.slider.imagePopup = true;
    this.slider.autoSlide = true;
    this.slider.showArrow = false;
  }

  prevImageClick() {
    this.slider.prev();
}

nextImageClick() {
  alert (" HOLA");
    this.slider.next();
}

}
