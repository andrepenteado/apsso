import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ListarSistemasComponent } from './components/listar-sistemas.component';
import { AlterarSenhaComponent } from './components/alterar-senha/alterar-senha.component';
import { ReactiveFormsModule } from "@angular/forms";
import { NgxLoadingModule } from "ngx-loading"
import { MainRoutingModule } from "./main-routing.module"

@NgModule({
  declarations: [
    ListarSistemasComponent,
    AlterarSenhaComponent
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
