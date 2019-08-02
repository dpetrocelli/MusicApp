import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';

// módulos para el cliente http y los formularios

import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {ListaInstrumentoComponent} from './instrumentos/lista-instrumento.component';
import {HomeComponent} from './home/home.component';
import {DetalleInstrumentoComponent} from './instrumentos/detalle-instrumento.component';
import {NuevoInstrumentoComponent} from './instrumentos/nuevo-instrumento.component';
import {EditarInstrumentoComponent} from './instrumentos/editar-instrumento.component';

@NgModule({
  declarations: [
    AppComponent,
    ListaInstrumentoComponent,
    HomeComponent,
    DetalleInstrumentoComponent,
    NuevoInstrumentoComponent,
    EditarInstrumentoComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
