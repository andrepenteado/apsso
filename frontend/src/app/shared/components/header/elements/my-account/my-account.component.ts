import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../../../../services/auth.service";
import { Usuario } from "../../../../../models/usuario";

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.scss']
})
export class MyAccountComponent implements OnInit {

  usuario: Usuario;

  constructor(
      private authService: AuthService
  ) { }

  async ngOnInit() {
    this.usuario = await this.authService.usuarioLogado();
  }

  nomePerfil(): string {
    for (let i=0; i < this.usuario.perfis.length; i++) {
      let perfilSistema = this.usuario.perfis[i];
      if (perfilSistema.authority.startsWith('ROLE_APsso_'))
        return perfilSistema.descricao;
    }
  }

  logout(): void {
    localStorage.clear();
    window.location.href = '/logout';
  }

}
