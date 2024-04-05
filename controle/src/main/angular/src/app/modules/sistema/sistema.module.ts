import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SistemaRoutingModule } from './sistema-routing.module';
import { PesquisarComponent } from './pesquisar/pesquisar.component';
import { CadastroComponent } from './cadastro/cadastro.component';
import { DataTablesModule } from 'angular-datatables';
import { CoreModule } from "../../libs/core/core.module"
import { ReactiveFormsModule } from "@angular/forms"
import { NgxLoadingModule } from "ngx-loading"


@NgModule({
  declarations: [
    PesquisarComponent,
    CadastroComponent
  ],
  imports: [
    CommonModule,
    SistemaRoutingModule,
    CoreModule,
    DataTablesModule,
    ReactiveFormsModule,
    NgxLoadingModule.forRoot({})
  ]
})
export class SistemaModule { }
