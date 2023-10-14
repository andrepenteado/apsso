import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AcessoNegadoComponent } from "./acesso-negado.component";
import { ErroProcessamentoComponent } from "./erro-processamento.component";

const routes: Routes = [
  { path: 'acesso-negado', component: AcessoNegadoComponent },
  { path: 'erro-processamento', component: ErroProcessamentoComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PagesRoutingModule { }
