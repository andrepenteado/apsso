import { AcessoNegadoComponent, ErroProcessamentoComponent, PaginaInicialComponent } from "@andre.penteado/ngx-apcore"
import { Routes } from "@angular/router"

export const DECORATED_ROUTES: Routes = [

  { path: "", component: PaginaInicialComponent },

  {
    path: "pagina-inicial",
    component: PaginaInicialComponent
  },

  {
    path: "empresa",
    loadChildren: () => import("../modules/empresa/empresa.module").then((m) => m.EmpresaModule)
  },
  {
    path: "sistema",
    loadChildren: () => import("../modules/sistema/sistema.module").then((m) => m.SistemaModule)
  },
  {
    path: "usuario",
    loadChildren: () => import("../modules/usuario/usuario.module").then((m) => m.UsuarioModule)
  }

]

export const NO_DECORATED_ROUTES: Routes = [

  { path: "erro-processamento", component: ErroProcessamentoComponent },

  { path: "acesso-negado", component: AcessoNegadoComponent }

]
