/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PuntuacionRedsocialComponent } from './puntuacion-redsocial.component';

describe('PuntuacionRedsocialComponent', () => {
  let component: PuntuacionRedsocialComponent;
  let fixture: ComponentFixture<PuntuacionRedsocialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PuntuacionRedsocialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PuntuacionRedsocialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
