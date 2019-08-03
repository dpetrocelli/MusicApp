import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ListaInstrumentoComponent } from './instrumentos/lista-instrumento.component';
import {DetalleInstrumentoComponent} from './instrumentos/detalle-instrumento.component';
import {NuevoInstrumentoComponent} from './instrumentos/nuevo-instrumento.component';
import {EditarInstrumentoComponent} from './instrumentos/editar-instrumento.component';
import {VerConfiguracionComponent} from './marketplace/ver-configuracion.component';
import {NuevaConfiguracionComponent} from './marketplace/nueva-configuracion.component';
import {ModificarConfiguracionComponent} from './marketplace/modificar-configuracion.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'home', component: HomeComponent},
  {path: 'lista', component: ListaInstrumentoComponent},
  {path: 'marketplace', component: VerConfiguracionComponent},
  {path: 'nuevaConfiguracionMarketplace', component: NuevaConfiguracionComponent},
  {path: 'editarConfiguracionMarketplace', component: ModificarConfiguracionComponent},

  {path: 'detalle/:id', component: DetalleInstrumentoComponent},
  {path: 'nuevo', component: NuevoInstrumentoComponent},
  {path: 'editar/:id', component: EditarInstrumentoComponent},
  {path: '**', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
