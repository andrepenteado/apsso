import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http"
import { registerLocaleData } from '@angular/common';
import localePT from '@angular/common/locales/pt';
import { HttpErrorsInterceptor, PARAMS, WithCredentialsInterceptor } from "@andre.penteado/ngx-apcore"
import { LOGOTIPO, MODULO } from "./etc/layout"
import { MENU } from "./etc/menu"
import { environment } from "../environments/environment";
import { NgxLoadingModule } from "ngx-loading";
import { ToastrModule } from "ngx-toastr";

registerLocaleData(localePT);

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgxLoadingModule,
    ToastrModule.forRoot()
  ],
  providers: [
    { provide: LOCALE_ID, useValue: "pt-BR" },
    {
      provide: PARAMS, useValue: {
        logotipoSistema: LOGOTIPO,
        menu: MENU,
        nomeSistema: MODULO,
        urlBackend: environment.urlBackend,
        urlLogin: environment.urlLogin,
        urlLogout: environment.urlLogout,
        urlUserLogin: environment.urlUserLogin
      }
    },
    { provide: HTTP_INTERCEPTORS, useClass: HttpErrorsInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: WithCredentialsInterceptor, multi: true }
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
