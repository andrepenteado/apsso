import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../services/auth.service";

@Component({
  selector: 'app-erro-processamento',
  template: `
      <div class="error-wrapper maintenance-bg">
          <div class="container">
              <ul class="maintenance-icons">
                  <li><i class="fa fa-bug"></i></li>
                  <li><i class="fa fa-bug"></i></li>
                  <li><i class="fa fa-bug"></i></li>
              </ul>
              <div class="maintenance-heading">
                  <h2 class="headline">ERRO NO PROCESSAMENTO</h2>
              </div>
              <h4 class="sub-content">Sua solicitação não foi corretamente processada pelo servidor</h4>
              <div><a class="btn btn-primary-gradien btn-lg text-light" (click)="logout()">VOLTAR PARA O INÍCIO</a></div>
          </div>
      </div>
  `,
  styles: [
  ]
})
export class ErroProcessamentoComponent implements OnInit {

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit(): void {
  }

  logout(): void {
    this.authService.logout();
  }

}
