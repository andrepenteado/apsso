import { Component } from '@angular/core';
import { Layout } from "../../../etc/layout"

@Component({
  selector: 'core-page-pagina-inicial',
  template: `
    <div class="text-center">
      <br><br>
      <h1><i class="bi bi-house-door" style="font-size: 10rem;"></i></h1>
      <h1>Página Inicial</h1>
      <p class="lead">Você está conectado em <strong>{{ getLayout().MODULO }}</strong></p>
    </div>
  `,
  styles: ``
})
export class PaginaInicialComponent {

  getLayout() {
    return Layout;
  }

}
