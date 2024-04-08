import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from './sections/menu/menu.component';
import { ToolbarComponent } from './sections/toolbar/toolbar.component';
import { FooterComponent } from './sections/footer/footer.component';
import { PaginaInicialComponent } from './pages/pagina-inicial.component';
import { ErroProcessamentoComponent } from './pages/erro-processamento.component';
import { AcessoNegadoComponent } from './pages/acesso-negado.component';
import { DecoratedComponent } from './layout/decorated/decorated.component';
import { NoDecoratedComponent } from './layout/no-decorated/no-decorated.component';
import { RouterModule } from '@angular/router';
import { FloatingButtonComponent } from './widgets/floating-button.component';
import { ToastNoAnimationModule } from "ngx-toastr";
import { TapToTopComponent } from './widgets/tap-to-top.component';
import { LoadingBarModule } from "@ngx-loading-bar/core"
import { LoadingBarHttpClientModule } from "@ngx-loading-bar/http-client"
import { LoadingBarRouterModule } from "@ngx-loading-bar/router"

@NgModule({
  declarations: [
    DecoratedComponent,
    NoDecoratedComponent,
    MenuComponent,
    ToolbarComponent,
    FooterComponent,
    PaginaInicialComponent,
    ErroProcessamentoComponent,
    AcessoNegadoComponent,
    FloatingButtonComponent,
    TapToTopComponent
  ],
  exports: [
    FloatingButtonComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ToastNoAnimationModule.forRoot(),
    LoadingBarHttpClientModule,
    LoadingBarRouterModule,
    LoadingBarModule
  ]
})
export class CoreModule { }
