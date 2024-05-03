import { Routes } from "@angular/router"
import {
  AcessoNegadoComponent, autorizarPerfilGuard,
  ErroProcessamentoComponent,
  PaginaInicialComponent
} from "@andrepenteado/ngx-apcore"

export const DECORATED_ROUTES: Routes = [

  {
    path: "pagina-inicial",
    component: PaginaInicialComponent,
    canActivate: [ autorizarPerfilGuard ],
    data: { perfilAutorizado: 'ROLE_Portal_USUARIO' }
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

export const SISTEMA_URL = {

  backendURL:  "http://localhost:30002",

  portalURL: "http://localhost:30002"

};
