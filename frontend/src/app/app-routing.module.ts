import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './usuarios/home.component';
import { ActivarComercioComponent } from './activarComercio/activarComercio.component';
import { ListaInstrumentoComponent } from './instrumentos/lista-instrumento.component';
import {DetalleInstrumentoComponent} from './instrumentos/detalle-instrumento.component';
import {NuevoInstrumentoComponent} from './instrumentos/nuevo-instrumento.component';
import {EditarInstrumentoComponent} from './instrumentos/editar-instrumento.component';
import {VerConfiguracionComponent} from './marketplace/ver-configuracion.component';
import {NuevaConfiguracionComponent} from './marketplace/nueva-configuracion.component';
import {ModificarConfiguracionComponent} from './marketplace/modificar-configuracion.component';
import { NuevousuarioComponent } from './usuarios/nuevousuario.component';
import { ListarpromocionesComponent } from './promociones/listarpromociones.component';
import { AltapromocionComponent } from './promociones/altapromocion.component';
import { EditarpromocionComponent } from './promociones/editarpromocion.component';
import { DetallepromocionComponent } from './promociones/detallepromocion.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'home', component: HomeComponent},
  {path: 'crearusuario', component: NuevousuarioComponent},
  {path: 'lista', component: ListaInstrumentoComponent},
  {path: 'promociones', component: ListarpromocionesComponent},
  {path: 'marketplace', component: VerConfiguracionComponent},
  {path: 'activarComercio', component: ActivarComercioComponent},
  {path: 'nuevaConfiguracionMarketplace', component: NuevaConfiguracionComponent},
  {path: 'editarConfiguracionMarketplace', component: ModificarConfiguracionComponent},

  {path: 'instrumento/detalle/:id', component: DetalleInstrumentoComponent},
  {path: 'instrumento/nuevo', component: NuevoInstrumentoComponent},
  {path: 'instrumento/editar/:id', component: EditarInstrumentoComponent},

  {path: 'promociones/detalle/:id', component: DetallepromocionComponent},
  {path: 'promociones/nuevo', component: AltapromocionComponent},
  {path: 'promociones/editar/:id', component: EditarpromocionComponent},

  {path: '**', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
