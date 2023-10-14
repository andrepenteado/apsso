import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../services/auth.service";

@Component({
  selector: 'app-acesso-negado',
  template: `
      <div class="error-wrapper maintenance-bg">
        <div class="container">
          <ul class="maintenance-icons">
            <li><i class="fa fa-exclamation-triangle"></i></li>
            <li><i class="fa fa-exclamation-triangle"></i></li>
            <li><i class="fa fa-exclamation-triangle"></i></li>
          </ul>
          <div class="maintenance-heading">
            <h2 class="headline">ACESSO NEGADO</h2>
          </div>
          <h4 class="sub-content">Você não tem privilégios suficientes para completar a operação</h4>
          <div><a class="btn btn-primary-gradien btn-lg text-light" (click)="voltarAoPortal()">VOLTAR PARA O INÍCIO</a></div>
        </div>
      </div>
  `,
  styles: [
  ]
})
export class AcessoNegadoComponent implements OnInit {

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit(): void {
  }

  voltarAoPortal(): void {
    this.authService.voltarAoPortal();
  }

}
