import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DecoratedComponent } from './libs/core/layout/decorated/decorated.component';
import { NoDecoratedComponent } from './libs/core/layout/no-decorated/no-decorated.component';
import { DECORATED_ROUTES, NO_DECORATED_ROUTES } from './etc/routes';

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
