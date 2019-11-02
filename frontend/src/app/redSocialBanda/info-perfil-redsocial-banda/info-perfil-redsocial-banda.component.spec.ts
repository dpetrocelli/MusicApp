/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { InfoPerfilRedsocialBandaComponent } from './info-perfil-redsocial-banda.component';

describe('InfoPerfilRedsocialBandaComponent', () => {
  let component: InfoPerfilRedsocialBandaComponent;
  let fixture: ComponentFixture<InfoPerfilRedsocialBandaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InfoPerfilRedsocialBandaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InfoPerfilRedsocialBandaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
