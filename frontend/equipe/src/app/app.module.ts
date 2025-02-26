import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http"
import { MENU } from "./config/menu"
import { LOGOTIPO, MODULO, PREFIXO_PERFIL_SISTEMA } from "./config/layout";
import { environment } from "../environments/environment";
import { HttpErrorsInterceptor, PARAMS, WithCredentialsInterceptor } from "@andre.penteado/ngx-apcore";
import { registerLocaleData } from "@angular/common";
import localePT from '@angular/common/locales/pt';
import { ToastrModule } from "ngx-toastr";
import { NgxLoadingModule } from "ngx-loading";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

registerLocaleData(localePT);

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    NgxLoadingModule,
    ToastrModule.forRoot()
  ],
  providers: [
    { provide: LOCALE_ID, useValue: "pt-BR" },
    {
      provide: PARAMS, useValue: {
        logotipo: LOGOTIPO,
        menu: MENU,
        sistema: MODULO,
        urlBackend: environment.urlBackend,
        urlLogin: environment.urlLogin,
        urlLogout: environment.urlLogout,
        urlUserLogin: environment.urlUserLogin,
        urlPortal: environment.urlPortal,
        prefixoPerfil: PREFIXO_PERFIL_SISTEMA
      }
    },
    { provide: HTTP_INTERCEPTORS, useClass: HttpErrorsInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: WithCredentialsInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
