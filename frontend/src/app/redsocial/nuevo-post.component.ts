import { Component, OnInit } from '@angular/core';
import { Post } from '../modelos/post';
import { PostService } from '../servicios/post.service';

@Component({
  selector: 'app-nuevo-post',
  templateUrl: './nuevo-post.component.html',
  styleUrls: ['./nuevo-post.component.css']
})
export class NuevoPostComponent implements OnInit {
  form: any = {};
  post: Post;
  creado = false;
  falloCreacion = false;
  msjFallo = '';
  msjOK = '';

  constructor(private postService : PostService) { }

  ngOnInit() {
  }

}
