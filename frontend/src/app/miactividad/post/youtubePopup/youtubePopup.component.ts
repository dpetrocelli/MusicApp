import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-youtubePopup',
  templateUrl: './youtubePopup.component.html',
  styleUrls: ['./youtubePopup.component.css']
})
export class YoutubePopupComponent implements OnInit {

  @Input() url;


  constructor(public activeModal: NgbActiveModal) {  }

  ngOnInit() {

  }

}
