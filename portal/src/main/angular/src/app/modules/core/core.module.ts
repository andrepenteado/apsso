import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CoreRoutingModule } from './core-routing.module';
import { HomeComponent } from './components/home.component';
import {NgbToastModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    CommonModule,
    CoreRoutingModule,
    NgbToastModule
  ],
  exports: [
  ]
})
export class CoreModule { }
