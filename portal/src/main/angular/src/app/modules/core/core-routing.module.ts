import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home.component';
import { ListarSistemasComponent } from "./components/listar-sistemas.component";
import { AlterarSenhaComponent } from "./components/alterar-senha/alterar-senha.component";

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'listar-sistemas', component: ListarSistemasComponent },
  { path: 'alterar-senha', component: AlterarSenhaComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CoreRoutingModule { }
