import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SistemaRoutingModule } from './sistema-routing.module';
import { PesquisarComponent } from './pesquisar/pesquisar.component';
import { CadastroComponent } from './cadastro/cadastro.component';
import { ReactiveFormsModule } from "@angular/forms"
import { NgxLoadingModule } from "ngx-loading"
import { FloatingButtonComponent } from "@andre.penteado/ngx-apcore";


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
    FloatingButtonComponent
  ]
})
export class SistemaModule { }
