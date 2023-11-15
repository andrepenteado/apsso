import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CoreRoutingModule } from './core-routing.module';
import { HomeComponent } from './components/home.component';
import { NgbToastModule } from '@ng-bootstrap/ng-bootstrap';
import { ListarSistemasComponent } from './components/listar-sistemas.component';
import { AlterarSenhaComponent } from './components/alterar-senha/alterar-senha.component';
import { ReactiveFormsModule } from "@angular/forms";
import { SharedModule } from "../../shared/shared.module";
import { ExibeMensagemComponent} from "./components/exibe-mensagem.component";

@NgModule({
  declarations: [
    HomeComponent,
    ListarSistemasComponent,
    AlterarSenhaComponent,
    ExibeMensagemComponent
  ],
  imports: [
    CommonModule,
    CoreRoutingModule,
    NgbToastModule,
    ReactiveFormsModule,
    SharedModule
  ],
  exports: [
  ]
})
export class CoreModule { }
