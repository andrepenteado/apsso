import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PagesRoutingModule } from './pages-routing.module';
import { AcessoNegadoComponent } from './acesso-negado.component';
import { ErroProcessamentoComponent } from './erro-processamento.component';


@NgModule({
  declarations: [
    AcessoNegadoComponent,
    ErroProcessamentoComponent
  ],
  imports: [
    CommonModule,
    PagesRoutingModule
  ]
})
export class PagesModule { }
