import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SistemaRoutingModule } from './sistema-routing.module';
import { PesquisarComponent } from './pesquisar/pesquisar.component';
import { CadastroComponent } from './cadastro/cadastro.component';
import { DataTablesModule } from 'angular-datatables';
import { ReactiveFormsModule } from "@angular/forms"
import { NgxLoadingModule } from "ngx-loading"
import { NgxApcoreModule } from "@andrepenteado/ngx-apcore"


@NgModule({
  declarations: [
    PesquisarComponent,
    CadastroComponent
  ],
  imports: [
    CommonModule,
    SistemaRoutingModule,
    DataTablesModule,
    ReactiveFormsModule,
    NgxLoadingModule.forRoot({}),
    NgxApcoreModule
  ]
})
export class SistemaModule { }
