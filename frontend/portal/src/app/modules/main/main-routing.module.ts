import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListarSistemasComponent } from "./components/listar-sistemas.component";
import { MeusDadosComponent } from "./components/meus-dados/meus-dados.component";
import { autorizarPerfilGuard } from "@andre.penteado/ngx-apcore"
import { PREFIXO_PERFIL_SISTEMA } from "../../etc/layout";

const routes: Routes = [
  {
    path: 'listar-sistemas',
    component: ListarSistemasComponent,
    canActivate: [ autorizarPerfilGuard ],
    data: { perfisAutorizados: [ `${PREFIXO_PERFIL_SISTEMA}USUARIO` ] }
  },
  {
    path: 'meus-dados',
    component: MeusDadosComponent,
    canActivate: [ autorizarPerfilGuard ],
    data: { perfisAutorizados: [ `${PREFIXO_PERFIL_SISTEMA}USUARIO` ] }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }
