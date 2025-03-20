import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmpresaRoutingModule } from './empresa-routing.module';
import { PesquisarComponent } from './pesquisar/pesquisar.component';
import { CadastroComponent } from './cadastro/cadastro.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms"
import { NgxLoadingModule } from "ngx-loading"
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from "ngx-mask"
import { FloatingButtonComponent } from "@andre.penteado/ngx-apcore";

@NgModule({
  declarations: [
    PesquisarComponent,
    CadastroComponent
  ],
  imports: [
    CommonModule,
    EmpresaRoutingModule,
    ReactiveFormsModule,
    NgxMaskDirective,
    NgxMaskPipe,
    NgxLoadingModule.forRoot({}),
    FloatingButtonComponent,
    FormsModule
  ],
  providers: [provideNgxMask()]
})
export class EmpresaModule { }
