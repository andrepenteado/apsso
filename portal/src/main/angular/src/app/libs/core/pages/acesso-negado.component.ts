import { Component } from '@angular/core';

@Component({
  selector: 'core-page-acesso-negado',
  template: `
    <div class="container-fluid maintenance-bg">
      <br><br><br>
      <div class="text-center">
        <img src="/assets/svg/error-403.svg" width="500px">
        <br><br><br>
        <h1 class="text-warning fw-bolder">ACESSO NEGADO</h1>
        <p class="fs-5 text-dark">Seu usuário não tem permissões suficientes ou não é autorizado a acessar essa página.</p>
        <a href="/" class="btn btn-lg btn-outline-primary mt-3"><i class="bi bi-house"></i> Voltar para página inicial</a>
      </div>
    </div>
  `,
  styles: `
    .maintenance-bg {
      background-image: url(/assets/images/maintenance-bg.jpg);
      min-height: 100vh;
      min-width: 100vh;
      background-size: cover;
      background-repeat: no-repeat;
      background-position: center center;
    }
  `
})
export class AcessoNegadoComponent {

}
