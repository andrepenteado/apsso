import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../../../../services/auth.service";
import {Usuario} from "../../../../../models/usuario";
import {PerfilUsuario} from "../../../../../models/perfil-usuario";

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

  ngOnInit() {
    this.authService.usuarioLogado().subscribe({
      next: usuario => this.usuario = usuario
    });
  }

  nomePerfil(): string {
    for (let i=0; i < this.usuario.perfis.length; i++) {
      let perfilUsuario = this.usuario.perfis[i];
      if (perfilUsuario.authority.startsWith('ROLE_APsso_'))
        return perfilUsuario.perfilSistema.descricao;
    }
  }

  logout() {
    this.authService.logout();
  }
}
