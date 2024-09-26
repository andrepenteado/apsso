import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ListarSistemasComponent } from './components/listar-sistemas.component';
import { MeusDadosComponent } from './components/meus-dados/meus-dados.component';
import { ReactiveFormsModule } from "@angular/forms";
import { MainRoutingModule } from "./main-routing.module"
import { NgxLoadingModule } from "ngx-loading";

@NgModule({
  declarations: [
    ListarSistemasComponent,
    MeusDadosComponent
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    ReactiveFormsModule,
    NgxLoadingModule
  ],
  exports: [
  ]
})
export class MainModule { }
