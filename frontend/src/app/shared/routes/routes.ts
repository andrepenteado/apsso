import { Routes } from '@angular/router';

export const content: Routes = [
  { path: '', loadChildren: () => import('../../modules/core/core.module').then(m => m.CoreModule) },
  { path: 'sistema', loadChildren: () => import('../../modules/sistema/sistema.module').then(m => m.SistemaModule) },
  { path: 'usuario', loadChildren: () => import('../../modules/usuario/usuario.module').then(m => m.UsuarioModule) }
];