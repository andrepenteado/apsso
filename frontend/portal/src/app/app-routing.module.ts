import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DECORATED_ROUTES, NO_DECORATED_ROUTES } from './config/routes';
import {
  AuthorizedComponent,
  LayoutSimplesComponent,
  LoginComponent,
  LogoutComponent,
  SemLayoutComponent
} from "@andre.penteado/ngx-apcore"

const routes: Routes = [

  { path: '', redirectTo: '/pagina-inicial', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },

  { path: 'logout', component: LogoutComponent },

  { path: 'authorized', component: AuthorizedComponent },

  {
    path: "",
    component: LayoutSimplesComponent,
    children: DECORATED_ROUTES
  },

  {
    path: "",
    component: SemLayoutComponent,
    children: NO_DECORATED_ROUTES
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
