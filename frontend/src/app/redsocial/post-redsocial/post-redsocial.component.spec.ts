/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PostRedsocialComponent } from './post-redsocial.component';

describe('PostRedsocialComponent', () => {
  let component: PostRedsocialComponent;
  let fixture: ComponentFixture<PostRedsocialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PostRedsocialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PostRedsocialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
