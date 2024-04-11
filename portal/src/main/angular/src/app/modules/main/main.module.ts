import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ListarSistemasComponent } from './components/listar-sistemas.component';
import { MeusDadosComponent } from './components/meus-dados/meus-dados.component';
import { ReactiveFormsModule } from "@angular/forms";
import { NgxLoadingModule } from "ngx-loading"
import { MainRoutingModule } from "./main-routing.module"

@NgModule({
  declarations: [
    ListarSistemasComponent,
    MeusDadosComponent
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    ReactiveFormsModule,
    NgxLoadingModule.forRoot({})
  ],
  exports: [
  ]
})
export class MainModule { }
