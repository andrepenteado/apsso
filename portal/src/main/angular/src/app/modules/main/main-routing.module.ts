import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListarSistemasComponent } from "./components/listar-sistemas.component";
import { MeusDadosComponent } from "./components/meus-dados/meus-dados.component";

const routes: Routes = [
  { path: 'listar-sistemas', component: ListarSistemasComponent },
  { path: 'meus-dados', component: MeusDadosComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }
