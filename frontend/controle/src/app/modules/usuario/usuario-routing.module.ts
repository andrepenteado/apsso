import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadastroComponent } from './cadastro/cadastro.component';
import { PesquisarComponent } from './pesquisar/pesquisar.component';
import { autorizarPerfilGuard } from "@andrepenteado/ngx-apcore"

const routes: Routes = [
  {
    path: 'pesquisar',
    component: PesquisarComponent,
    canActivate: [ autorizarPerfilGuard ],
    data: { perfisAutorizados: ['ROLE_com.github.andrepenteado.sso.controle_ARQUITETO'] }
  },
  {
    path: 'cadastro',
    component: CadastroComponent,
    canActivate: [ autorizarPerfilGuard ],
    data: { perfisAutorizados: ['ROLE_com.github.andrepenteado.sso.controle_ARQUITETO'] }
  },
  {
    path: 'cadastro/:username',
    component: CadastroComponent,
    canActivate: [ autorizarPerfilGuard ],
    data: { perfisAutorizados: ['ROLE_com.github.andrepenteado.sso.controle_ARQUITETO'] }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuarioRoutingModule { }
