import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsuarioRoutingModule } from './usuario-routing.module';
import { CadastroComponent } from './cadastro/cadastro.component';
import { PesquisarComponent } from './pesquisar/pesquisar.component';
import { DataTablesModule } from 'angular-datatables';
import { NgxLoadingModule } from "ngx-loading"
import { CoreModule } from "../../libs/core/core.module"
import { ReactiveFormsModule } from "@angular/forms"


@NgModule({
  declarations: [
    CadastroComponent,
    PesquisarComponent
  ],
  imports: [
    CommonModule,
    UsuarioRoutingModule,
    CoreModule,
    DataTablesModule,
    ReactiveFormsModule,
    NgxLoadingModule.forRoot({})
  ]
})
export class UsuarioModule { }
