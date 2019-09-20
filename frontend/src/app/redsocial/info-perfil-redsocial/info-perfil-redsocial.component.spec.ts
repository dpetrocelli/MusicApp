/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { InfoPerfilRedsocialComponent } from './info-perfil-redsocial.component';

describe('InfoPerfilRedsocialComponent', () => {
  let component: InfoPerfilRedsocialComponent;
  let fixture: ComponentFixture<InfoPerfilRedsocialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InfoPerfilRedsocialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InfoPerfilRedsocialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
