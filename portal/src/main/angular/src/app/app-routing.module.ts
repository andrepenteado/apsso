import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContentComponent } from './shared/components/layout/content/content.component';
import { full } from "./shared/routes/full.routes";
import { content } from "./shared/routes/routes";
import { FullComponent } from "./shared/components/layout/full/full.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: 'sample-page',
    pathMatch: 'full'
  },
  {
    path: '',
    component: ContentComponent,
    children: content
  },
  {
    path: '',
    component: FullComponent,
    children: full
  },
  {
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [[RouterModule.forRoot(routes, {
    anchorScrolling: 'enabled',
    scrollPositionRestoration: 'enabled',
    relativeLinkResolution: 'legacy'
})],
],
  exports: [RouterModule]
})
export class AppRoutingModule { }
