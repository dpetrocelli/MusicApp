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
import { AccesodenegadoComponent } from './accesodenegado/accesodenegado.component';
import { NuevoPostComponent } from './miactividad/post/nuevo-post.component';
import { EditarPostComponent } from './miactividad/post/editar-post.component';
import { PerfilComponent } from './perfil/perfil.component';
import { PostComponent } from './miactividad/post/post.component';
import { ImagenPerfilComponent } from './miactividad/imagen-perfil/imagen-perfil.component';
import { InfoPerfilComponent } from './miactividad/info-perfil/info-perfil.component';
import { PuntuacionComponent } from './miactividad/puntuacion/puntuacion.component';
import { ComprarPromocionesComponent } from './promociones/comprar-promociones.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgImageSliderModule } from 'ng-image-slider';
import { CrearPuntuacionComponent } from './puntuacion/crear-puntuacion.component';
import { ImgSliderComponent } from './miactividad/post/imgSlider/imgSlider.component';
import { YoutubePopupComponent } from './miactividad/post/youtubePopup/youtubePopup.component';

import { HomesiteComponent } from './homesite/homesite.component';
import { RedSocialComponent } from './redsocial/redsocial.component';
import {ImagenPerfilRedsocialComponent} from './redsocial/imagen-perfil-redsocial/imagen-perfil-redsocial.component';
import {InfoPerfilRedsocialComponent} from './redsocial/info-perfil-redsocial/info-perfil-redsocial.component';
import {PostRedsocialComponent} from './redsocial/post-redsocial/post-redsocial.component';
import {PuntuacionRedsocialComponent} from './redsocial/puntuacion-redsocial/puntuacion-redsocial.component';



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
      DetallepromocionComponent,
      AccesodenegadoComponent,
      HomesiteComponent,
      NuevoPostComponent,
      EditarPostComponent,
      PerfilComponent,
      PostComponent,
      ImagenPerfilComponent,
      InfoPerfilComponent,
      PuntuacionComponent,
      ImgSliderComponent,
      YoutubePopupComponent,
      ComprarPromocionesComponent,
      CrearPuntuacionComponent,
      PuntuacionRedsocialComponent,
      PostRedsocialComponent,
      InfoPerfilRedsocialComponent,
      ImagenPerfilRedsocialComponent,
      RedSocialComponent
   ],
   imports: [
      BrowserModule,
      HttpClientModule,
      FormsModule,
      AppRoutingModule,
      NgbModule,
      NgImageSliderModule
   ],
   providers: [],
   bootstrap: [
      AppComponent
   ],
   entryComponents: [
      ImgSliderComponent,
      YoutubePopupComponent
   ]
})
export class AppModule { }
