import { Routes } from "@angular/router"
import { ErroProcessamentoComponent } from "../libs/core/pages/erro-processamento.component"
import { AcessoNegadoComponent } from "../libs/core/pages/acesso-negado.component"
import { PaginaInicialComponent } from "../libs/core/pages/pagina-inicial.component"

export const DECORATED_ROUTES: Routes = [

  { path: "", component: PaginaInicialComponent },

  { path: "pagina-inicial", component: PaginaInicialComponent },

  {
    path: "",
    loadChildren: () => import("../modules/main/main.module").then((m) => m.MainModule),
  }

]

export const NO_DECORATED_ROUTES: Routes = [

  { path: "erro-processamento", component: ErroProcessamentoComponent },

  { path: "acesso-negado", component: AcessoNegadoComponent }

]

export const SISTEMA_URL = {

  backendURL:  window.location.protocol + '//' + window.location.host,

  portalURL: window.location.protocol + '//' + window.location.host

};
