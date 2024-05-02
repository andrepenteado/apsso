import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from "@angular/common/http"
import { MENU } from "./etc/menu"
import { SISTEMA_URL } from "./etc/routes"
import { NgxApcoreModule } from "@andrepenteado/ngx-apcore"
import { CLIENT_ID, REDIRECT_URI, SECRET_ID, URL_AUTHORIZATION_SERVER } from "./etc/oauth2";
import { LOGOTIPO, MODULO } from "./etc/layout";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgxApcoreModule.forRoot({
      nomeSistema: MODULO,
      logotipoSistema: LOGOTIPO,
      urlBackendSistema: SISTEMA_URL.backendURL,
      urlPortal: SISTEMA_URL.portalURL,
      prefixoPerfil: "ROLE_Portal_",
      menu: MENU,
      clientId: CLIENT_ID,
      redirectUri: REDIRECT_URI,
      secretId: SECRET_ID,
      urlAuthorizationServer: URL_AUTHORIZATION_SERVER
    })
  ],
  providers: [ ],
  bootstrap: [AppComponent]
})
export class AppModule { }
