import { Routes } from '@angular/router';

export const full: Routes = [
  {
    path: 'pages',
    loadChildren: () => import('../../modules/pages/pages.module').then(m => m.PagesModule)
  }
];
