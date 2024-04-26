import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DECORATED_ROUTES, NO_DECORATED_ROUTES } from './etc/routes';
import { DecoratedComponent, NoDecoratedComponent } from "@andrepenteado/ngx-apcore"

const routes: Routes = [
  {
    path: '',
    component: DecoratedComponent,
    children: DECORATED_ROUTES
  },
  {
    path: '',
    component: NoDecoratedComponent,
    children: NO_DECORATED_ROUTES
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
