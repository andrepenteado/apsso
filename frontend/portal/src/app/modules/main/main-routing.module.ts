import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListarSistemasComponent } from "./components/listar-sistemas.component";
import { MeusDadosComponent } from "./components/meus-dados/meus-dados.component";
import { autorizarPerfilGuard } from "@andrepenteado/ngx-apcore"
import { clientId } from '../../etc/oauth2';

const routes: Routes = [
  {
    path: 'listar-sistemas',
    component: ListarSistemasComponent,
    canActivate: [ autorizarPerfilGuard ],
    data: { perfisAutorizados: [`ROLE_${clientId}_USUARIO`] }
  },
  {
    path: 'meus-dados',
    component: MeusDadosComponent,
    canActivate: [ autorizarPerfilGuard ],
    data: { perfisAutorizados: [`ROLE_${clientId}_USUARIO`] }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }
