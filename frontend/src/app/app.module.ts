import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// módulos para el cliente http y los formularios

import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ListaInstrumentoComponent } from './instrumentos/lista-instrumento.component';
import { HomeComponent } from './usuarios/home.component';
import { DetalleInstrumentoComponent } from './instrumentos/detalle-instrumento.component';
import { NuevoInstrumentoComponent } from './instrumentos/nuevo-instrumento.component';
import { EditarInstrumentoComponent } from './instrumentos/editar-instrumento.component';
import { NuevaConfiguracionComponent } from './marketplace/nueva-configuracion.component';
import { VerConfiguracionComponent } from './marketplace/ver-configuracion.component';
import { ModificarConfiguracionComponent } from './marketplace/modificar-configuracion.component';
import { EliminarConfiguracionComponent } from './marketplace/eliminar-configuracion.component';
import { ActivarComercioComponent } from './activarComercio/activarComercio.component';
import { NuevousuarioComponent } from './usuarios/nuevousuario.component';
import { ListarpromocionesComponent } from './promociones/listarpromociones.component';
import { AltapromocionComponent } from './promociones/altapromocion.component';
import { EditarpromocionComponent } from './promociones/editarpromocion.component';
import { DetallepromocionComponent } from './promociones/detallepromocion.component';

@NgModule({
   declarations: [
      AppComponent,
      ListaInstrumentoComponent,
      HomeComponent,
      DetalleInstrumentoComponent,
      NuevoInstrumentoComponent,
      EditarInstrumentoComponent,
      NuevaConfiguracionComponent,
      VerConfiguracionComponent,
      ModificarConfiguracionComponent,
      EliminarConfiguracionComponent,
      ActivarComercioComponent,
      NuevousuarioComponent,
      NuevousuarioComponent,
      AltapromocionComponent,
      ListarpromocionesComponent,
      EditarpromocionComponent,
      DetallepromocionComponent
   ],
   imports: [
      BrowserModule,
      HttpClientModule,
      FormsModule,
      AppRoutingModule
   ],
   providers: [],
   bootstrap: [
      AppComponent
   ]
})
export class AppModule { }
