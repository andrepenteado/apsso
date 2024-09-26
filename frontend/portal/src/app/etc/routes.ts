import { Routes } from "@angular/router"
import {
  AcessoNegadoComponent,
  ErroProcessamentoComponent,
  PaginaInicialComponent
} from "@andre.penteado/ngx-apcore"

export const DECORATED_ROUTES: Routes = [

  { path: "", component: PaginaInicialComponent },

  {
    path: "pagina-inicial",
    component: PaginaInicialComponent
  },

  {
    path: "",
    loadChildren: () => import("../modules/main/main.module").then((m) => m.MainModule),
  }

]

export const NO_DECORATED_ROUTES: Routes = [

  { path: "erro-processamento", component: ErroProcessamentoComponent },

  { path: "acesso-negado", component: AcessoNegadoComponent }

]
