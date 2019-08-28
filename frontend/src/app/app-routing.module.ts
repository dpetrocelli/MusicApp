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
import { AccesodenegadoComponent } from './accesodenegado/accesodenegado.component';
import { SeguridadService as seguridad } from './servicios/seguridad.service';
import { VerBiografiaComponent } from './redsocial/biografia/ver-biografia.component';
import { NuevoPostComponent } from './redsocial/post/nuevo-post.component';
import { EditarPostComponent } from './redsocial/post/editar-post.component';
import { VerPostsComponent } from './redsocial/post/ver-posts.component';
import { PerfilComponent } from './perfil/perfil.component';
import { PostComponent } from './redsocial/post/post.component';
const routes: Routes = [
  
  // Lista de acceso base para todos 
  {path: '', component: HomeComponent},
  {path: 'home', component: HomeComponent},
  {path: 'crearusuario', component: NuevousuarioComponent},
  {path: 'accesodenegado', component: AccesodenegadoComponent},
  

  // Opciones de Artista 
    {path: 'perfil', component: PerfilComponent, canActivate: [seguridad], data: { expectedRol: ['artista']}},
    {path: 'biografia', component: VerBiografiaComponent, canActivate: [seguridad], data: { expectedRol: ['artista']}},
    {path: 'posts', component: PostComponent, canActivate: [seguridad], data: { expectedRol: ['artista']}},
    {path: 'posts/nuevo', component: NuevoPostComponent, canActivate: [seguridad], data: { expectedRol: ['artista']}},
    {path: 'posts/detalle', component: VerPostsComponent, canActivate: [seguridad], data: { expectedRol: ['artista']}},
    {path: 'posts/editar', component: EditarPostComponent, canActivate: [seguridad], data: { expectedRol: ['artista']}},
  // Opciones de Comercio
    // Promociones
  {path: 'promociones', component: ListarpromocionesComponent, canActivate: [seguridad], data: { expectedRol: ['comercio']}},
  {path: 'promociones/detalle/:id', component: DetallepromocionComponent, canActivate: [seguridad], data: { expectedRol: ['comercio']}},
  {path: 'promociones/nuevo', component: AltapromocionComponent, canActivate: [seguridad], data: { expectedRol: ['comercio']}},
  {path: 'promociones/editar/:id', component: EditarpromocionComponent, canActivate: [seguridad], data: { expectedRol: ['comercio']}},
  {path: 'activarComercio', component: ActivarComercioComponent, canActivate: [seguridad], data: { expectedRol: ['comercio']}},

  // Opciones de Admin
    // DATOS MarketPlace
    {path: 'marketplace', component: VerConfiguracionComponent, canActivate: [seguridad], data: { expectedRol: ['admin']}},
    {path: 'marketplace/nuevo', component: NuevaConfiguracionComponent, canActivate: [seguridad], data: { expectedRol: ['admin']}},
    {path: 'marketplace/editar', component: ModificarConfiguracionComponent, canActivate: [seguridad], data: { expectedRol: ['admin']}},

    // Instrumento
    {path: 'instrumento', component: ListaInstrumentoComponent, canActivate: [seguridad], data: { expectedRol: ['admin']}},
    {path: 'instrumento/detalle/:id', component: DetalleInstrumentoComponent, canActivate: [seguridad], data: { expectedRol: ['admin']}},
    {path: 'instrumento/nuevo', component: NuevoInstrumentoComponent, canActivate: [seguridad], data: { expectedRol: ['admin']}},
    {path: 'instrumento/editar/:id', component: EditarInstrumentoComponent, canActivate: [seguridad], data: { expectedRol: ['admin']}},
  {path: '**', redirectTo: 'home', pathMatch: 'full'}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
