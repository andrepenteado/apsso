import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from "@angular/common/http"
import { registerLocaleData } from '@angular/common';
import localePT from '@angular/common/locales/pt';
import { NgxApcoreModule } from "@andrepenteado/ngx-apcore"
import { Layout } from "./etc/layout"
import { SISTEMA_URL } from "./etc/routes"
import { MENU } from "./etc/menu"

registerLocaleData(localePT);

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgxApcoreModule.forRoot({
      NOME_SISTEMA: Layout.MODULO,
      LOGOTIPO_SISTEMA: Layout.LOGOTIPO,
      URL_BACKEND_SISTEMA: SISTEMA_URL.backendURL,
      URL_PORTAL: SISTEMA_URL.portalURL,
      PREFIXO_PERFIL: "ROLE_Controle_",
      MENU: MENU
    })
  ],
  providers: [
    { provide: LOCALE_ID, useValue: "pt-BR" }
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
