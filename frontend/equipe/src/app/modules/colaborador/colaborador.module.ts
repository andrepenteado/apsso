import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ColaboradorRoutingModule } from './colaborador-routing.module';
import { PesquisarComponent } from './pesquisar/pesquisar.component';
import { CadastroComponent } from './cadastro/cadastro.component';
import { ReactiveFormsModule } from "@angular/forms";
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from "ngx-mask";
import { NgxLoadingModule } from "ngx-loading"
import { FloatingButtonComponent } from "@andre.penteado/ngx-apcore";

@NgModule({
  declarations: [
    PesquisarComponent,
    CadastroComponent
  ],
  imports: [
    CommonModule,
    ColaboradorRoutingModule,
    ReactiveFormsModule,
    NgxMaskDirective,
    NgxMaskPipe,
    NgxLoadingModule.forRoot({}),
    FloatingButtonComponent
  ],
  providers: [provideNgxMask()]
})
export class ColaboradorModule { }
