import { Routes } from '@angular/router';

export const content: Routes = [
  { path: '', loadChildren: () => import('../../modules/core/core.module').then(m => m.CoreModule) },
];
