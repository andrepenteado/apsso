import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SistemaRoutingModule } from './sistema-routing.module';
import { PesquisarComponent } from './pesquisar/pesquisar.component';
import { CadastroComponent } from './cadastro/cadastro.component';
import { SharedModule } from "../../shared/shared.module";
import { CoreModule } from "../core/core.module";
import { DataTablesModule } from "angular-datatables";


@NgModule({
  declarations: [
    PesquisarComponent,
    CadastroComponent
  ],
  imports: [
    CommonModule,
    SistemaRoutingModule,
    SharedModule,
    CoreModule,
    DataTablesModule
  ]
})
export class SistemaModule { }
