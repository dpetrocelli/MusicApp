/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PuntuacionRedsocialBandaComponent } from './puntuacion-redsocial-banda.component';

describe('PuntuacionRedsocialBandaComponent', () => {
  let component: PuntuacionRedsocialBandaComponent;
  let fixture: ComponentFixture<PuntuacionRedsocialBandaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PuntuacionRedsocialBandaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PuntuacionRedsocialBandaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
