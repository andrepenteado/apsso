import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../../../services/auth.service";
import { UserLogin } from "../../dtos/user-login";
import { MENU } from "../../../../etc/menu";
import { TemaService } from "../../services/tema.service"

@Component({
  selector: 'core-section-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit {

  userLogin: UserLogin;

  constructor(
    private authService: AuthService,
    protected temaService: TemaService
  ) { }

  async ngOnInit() {
    this.userLogin = await this.authService.usuarioLogado();
  }

  logout(): void {
    this.authService.logout();
  }

  voltarAoPortal(): void {
    this.authService.voltarAoPortal();
  }

  alterarTema(event: Event): void {
    const isDark = (<HTMLInputElement>event.target).checked;
    this.temaService.setDark(isDark);
  }

  protected readonly MENU = MENU;

}
