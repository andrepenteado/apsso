import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PesquisarComponent } from "./pesquisar/pesquisar.component";
import { CadastroComponent } from "./cadastro/cadastro.component";
import { autorizarPerfilGuard } from "@andre.penteado/ngx-apcore";
import { PREFIXO_PERFIL_SISTEMA } from "../../config/layout";

const routes: Routes = [

  {
    path: 'pesquisar',
    component: PesquisarComponent,
    canActivate: [ autorizarPerfilGuard ],
    data: { perfisAutorizados: [`${PREFIXO_PERFIL_SISTEMA}GESTOR`] }
  },

  {
    path: 'cadastro',
    component: CadastroComponent,
    canActivate: [ autorizarPerfilGuard ],
    data: { perfisAutorizados: [`${PREFIXO_PERFIL_SISTEMA}GESTOR`] }
  },

  {
    path: 'cadastro/:id',
    component: CadastroComponent,
    canActivate: [ autorizarPerfilGuard ],
    data: { perfisAutorizados: [`${PREFIXO_PERFIL_SISTEMA}GESTOR`] }
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ColaboradorRoutingModule { }
