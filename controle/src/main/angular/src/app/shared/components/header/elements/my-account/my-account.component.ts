import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../../services/auth.service";
import {environment} from "../../../../../../environments/environment";
import {UserLogin} from "../../../../../dto/user-login";

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.scss']
})
export class MyAccountComponent implements OnInit {

  userLogin: UserLogin;

  constructor(
      private authService: AuthService
  ) { }

  async ngOnInit() {
    this.userLogin = await this.authService.usuarioLogado();
  }

  nomePerfil(): string {
    for (const nome of Object.keys(this.userLogin.perfis)) {
      if (nome.startsWith("ROLE_APcontrole_"))
        return this.userLogin.perfis[nome];
    }
    return "Usuário";
  }

  logout(): void {
    this.authService.logout();
  }

  public voltarAoPortal(): void {
    localStorage.clear();
    window.location.href = environment.portalURL;
  }

}
