import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsuarioRoutingModule } from './usuario-routing.module';
import { CadastroComponent } from './cadastro/cadastro.component';
import { PesquisarComponent } from './pesquisar/pesquisar.component';
import { NgxLoadingModule } from "ngx-loading"
import { ReactiveFormsModule } from "@angular/forms"
import { FloatingButtonComponent } from "@andre.penteado/ngx-apcore";

@NgModule({
  declarations: [
    CadastroComponent,
    PesquisarComponent
  ],
  imports: [
    CommonModule,
    UsuarioRoutingModule,
    ReactiveFormsModule,
    NgxLoadingModule.forRoot({}),
    FloatingButtonComponent
  ]
})
export class UsuarioModule { }
