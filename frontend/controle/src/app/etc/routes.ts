import { Routes } from "@angular/router"
import { AcessoNegadoComponent, autorizarPerfilGuard, ErroProcessamentoComponent, PaginaInicialComponent } from "@andrepenteado/ngx-apcore"

export const DECORATED_ROUTES: Routes = [

  { path: "", component: PaginaInicialComponent },

  {
    path: "pagina-inicial",
    component: PaginaInicialComponent,
    canActivate: [ autorizarPerfilGuard ],
    data: { perfilAutorizado: 'ROLE_Controle_ARQUITETO' }
  },

  {
    path: "sistema",
    loadChildren: () => import("../modules/sistema/sistema.module").then((m) => m.SistemaModule),
  },
  {
    path: "usuario",
    loadChildren: () => import("../modules/usuario/usuario.module").then((m) => m.UsuarioModule),
  }

]

export const NO_DECORATED_ROUTES: Routes = [

  { path: "erro-processamento", component: ErroProcessamentoComponent },

  { path: "acesso-negado", component: AcessoNegadoComponent }

]
