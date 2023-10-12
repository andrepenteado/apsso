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

  logout(): void {
    this.authService.logout();
  }

}
