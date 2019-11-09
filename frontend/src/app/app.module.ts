import { BrowserModule } from '@angular/platform-browser';
import { LOCALE_ID, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// módulos para el cliente http y los formularios

import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';
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
import { ComercioSiteComponent } from './comerciosite/comerciosite.component';

import { RedSocialComponent } from './redsocial/redsocial.component';
import {ImagenPerfilRedsocialComponent} from './redsocial/imagen-perfil-redsocial/imagen-perfil-redsocial.component';
import {InfoPerfilRedsocialComponent} from './redsocial/info-perfil-redsocial/info-perfil-redsocial.component';
import {PostRedsocialComponent} from './redsocial/post-redsocial/post-redsocial.component';
import {PuntuacionRedsocialComponent} from './redsocial/puntuacion-redsocial/puntuacion-redsocial.component';
import { ListaPagoComponent } from './pagos/lista-pago.component';
import { ListaNotificacionComponent } from './notificaciones/lista-notificacion.component';
import { EditarUsuarioComponent } from './usuarios/editar-usuario.component';
import { ListaGeneroMusicalComponent } from './generosMusicales/lista-genero-musical.component';
import { NuevoGeneroMusicalComponent } from './generosMusicales/nuevo-genero-musical.component';
import { EditarGeneroMusicalComponent } from './generosMusicales/editar-genero-musical.component';
import { ListaZonaComponent } from './zonas/lista-zona.component';
import { NuevaZonaComponent } from './zonas/nueva-zona.component';
import { EditarZonaComponent } from './zonas/editar-zona.component';
import { ListaLugarComponent } from './lugares/lista-lugar.component';
import { NuevoLugarComponent } from './lugares/nuevo-lugar.component';
import { EditarLugarComponent } from './lugares/editar-lugar.component';
import { BandaRedSocialComponent } from './redSocialBanda/redsocial-banda.component';
import { NuevaBandaComponent} from './redSocialBanda/crearBanda/nueva-banda.component';
import { ModificarBandaComponent} from './redSocialBanda/modificarBanda/modificar-banda.component';
import { ImagenPerfilComponentBanda} from './miactividadBanda/imagen-perfil-banda/imagen-perfil-banda.component';
import { InfoPerfilBandaComponent} from './miactividadBanda/info-perfil-banda/info-perfil-banda.component';
import { PuntuacionBandaComponent} from './miactividadBanda/puntuacion/puntuacion-banda.component';

import {PerfilBandaComponent} from './miactividadBanda/perfil-banda.component';
import localeEsAr from '@angular/common/locales/es-AR';
import { registerLocaleData } from '@angular/common';
import { ImagenPerfilRedsocialBandaComponent } from './redSocialBanda/imagen-perfil-redsocial-banda/imagen-perfil-redsocial-banda.component';
import { InfoPerfilRedsocialBandaComponent } from './redSocialBanda/info-perfil-redsocial-banda/info-perfil-redsocial-banda.component';
import { PuntuacionRedsocialBandaComponent } from './redSocialBanda/puntuacion-redsocial-banda/puntuacion-redsocial-banda.component';
import { CrearPuntuacionBandaComponent } from './puntuacionBanda/crear-puntuacion-banda.component';
import { VideoComponentComponent } from './miactividadBanda/videoComponent/videoComponent.component';

registerLocaleData(localeEsAr, 'es-Ar');

@NgModule({
   declarations: [
      AppComponent,
      ListaInstrumentoComponent,
      VideoComponentComponent,
      HomeComponent,
      EditarLugarComponent,
      DetalleInstrumentoComponent,
      NuevoInstrumentoComponent,
      InfoPerfilBandaComponent,
      EditarInstrumentoComponent,
      NuevaConfiguracionComponent,
      VerConfiguracionComponent,
      NuevaBandaComponent, 
      PerfilBandaComponent,
      ModificarConfiguracionComponent,
      EliminarConfiguracionComponent,
      ActivarComercioComponent,
      NuevousuarioComponent,
      NuevousuarioComponent,
      AltapromocionComponent,
      ListarpromocionesComponent,
      EditarpromocionComponent,
      AccesodenegadoComponent,
      HomesiteComponent,
      ComercioSiteComponent,
      NuevoPostComponent,
      EditarPostComponent,
      PerfilComponent,
      PostComponent,
      BandaRedSocialComponent,
      PuntuacionBandaComponent,
      ImagenPerfilComponent,
      ListaLugarComponent,
      ModificarBandaComponent,
      InfoPerfilComponent,
      ImagenPerfilComponentBanda,
      PuntuacionComponent,
      ImgSliderComponent,
      YoutubePopupComponent,
      ComprarPromocionesComponent,
      CrearPuntuacionComponent,
      CrearPuntuacionBandaComponent,
      EditarLugarComponent,
      PuntuacionRedsocialComponent,
      PostRedsocialComponent,
      InfoPerfilRedsocialComponent,
      ImagenPerfilRedsocialComponent,
      RedSocialComponent,
      ListaPagoComponent,
      ListaNotificacionComponent,
      EditarUsuarioComponent,
      ListaGeneroMusicalComponent,
      NuevoGeneroMusicalComponent,
      EditarGeneroMusicalComponent,
      ListaZonaComponent,
      NuevoLugarComponent,
      NuevaZonaComponent,
      EditarZonaComponent,
      ImagenPerfilRedsocialBandaComponent,
      InfoPerfilRedsocialBandaComponent,
      PuntuacionRedsocialBandaComponent,
   ],
   imports: [
      BrowserModule,
      HttpClientModule,
      FormsModule,
      ReactiveFormsModule,
      AppRoutingModule,
      NgbModule,
      NgImageSliderModule
   ],
   providers: [ { provide: LOCALE_ID, useValue: 'es-Ar' } ],
   bootstrap: [
      AppComponent
   ],
   entryComponents: [
      ImgSliderComponent,
      YoutubePopupComponent
   ]
})
export class AppModule { }
