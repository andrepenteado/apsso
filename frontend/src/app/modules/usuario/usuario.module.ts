import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsuarioRoutingModule } from './usuario-routing.module';
import { CadastroComponent } from './cadastro/cadastro.component';
import { PesquisarComponent } from './pesquisar/pesquisar.component';
import { SharedModule } from '../../shared/shared.module';
import { CoreModule } from '../core/core.module';
import { DataTablesModule } from 'angular-datatables';


@NgModule({
  declarations: [
    CadastroComponent,
    PesquisarComponent
  ],
  imports: [
    CommonModule,
    UsuarioRoutingModule,
    SharedModule,
    CoreModule,
    DataTablesModule
  ]
})
export class UsuarioModule { }
