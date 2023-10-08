import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadastroComponent } from './cadastro/cadastro.component';
import { PesquisarComponent } from './pesquisar/pesquisar.component';

const routes: Routes = [
  { path: 'pesquisar', component: PesquisarComponent },
  { path: 'cadastro', component: CadastroComponent },
  { path: 'cadastro/:id', component: CadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SistemaRoutingModule { }
