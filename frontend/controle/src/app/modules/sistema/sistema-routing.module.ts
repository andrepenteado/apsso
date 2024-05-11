import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadastroComponent } from './cadastro/cadastro.component';
import { PesquisarComponent } from './pesquisar/pesquisar.component';
import { autorizarPerfilGuard } from "@andrepenteado/ngx-apcore"
import { clientId } from '../../etc/oauth2';

const routes: Routes = [

  {
    path: 'pesquisar',
    component: PesquisarComponent,
    canActivate: [ autorizarPerfilGuard ],
    data: { perfisAutorizados: [`ROLE_${clientId}_ARQUITETO`] }
  },

  {
    path: 'cadastro',
    component: CadastroComponent,
    canActivate: [ autorizarPerfilGuard ],
    data: { perfisAutorizados: [`ROLE_${clientId}_ARQUITETO`] }
  },

  {
    path: 'cadastro/:id',
    component: CadastroComponent,
    canActivate: [ autorizarPerfilGuard ],
    data: { perfisAutorizados: [`ROLE_${clientId}_ARQUITETO`] }
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SistemaRoutingModule { }
