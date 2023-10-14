import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  template: `
    <br>
    <br>
    <br>
    <br>
    <div class="text-center">
      <h2 class="display-4"><i class="fa fa-home"></i> Página Inicial</h2>
      <p class="lead">Você está conectado em <strong>Módulo de Controle</strong></p>
    </div>
  `,
  styles: [
  ]
})
export class HomeComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
