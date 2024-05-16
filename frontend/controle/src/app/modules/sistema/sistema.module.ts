import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SistemaRoutingModule } from './sistema-routing.module';
import { PesquisarComponent } from './pesquisar/pesquisar.component';
import { CadastroComponent } from './cadastro/cadastro.component';
import { ReactiveFormsModule } from "@angular/forms"
import { NgxApcoreModule } from "@andrepenteado/ngx-apcore"
import { NgxLoadingModule } from "ngx-loading"


@NgModule({
  declarations: [
    PesquisarComponent,
    CadastroComponent
  ],
  imports: [
    CommonModule,
    SistemaRoutingModule,
    ReactiveFormsModule,
    NgxLoadingModule.forRoot({}),
    NgxApcoreModule
  ]
})
export class SistemaModule { }
