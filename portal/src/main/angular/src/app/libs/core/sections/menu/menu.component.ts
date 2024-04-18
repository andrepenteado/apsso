import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../../../services/auth.service";
import { UserLogin } from "../../dtos/user-login";
import { MENU } from "../../../../etc/menu";
import { TemaService } from "../../services/tema.service"
import { UploadService } from "../../services/upload.service"
import { Upload } from "../../dtos/upload"

@Component({
  selector: 'core-section-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit {

  public static upload = new Upload();

  userLogin: UserLogin;

  constructor(
    private authService: AuthService,
    private temaService: TemaService,
    private uploadService: UploadService
  ) { }

  async ngOnInit() {
    this.userLogin = await this.authService.usuarioLogado();
    if (this.userLogin.uuidFoto) {
      this.uploadService.buscar(this.userLogin.uuidFoto).subscribe(upload => {
        MenuComponent.upload = upload
      });
    }
  }

  isDark(): boolean {
    return this.temaService.isDark();
  }

  alterarTema(event: Event): void {
    const isDark = (<HTMLInputElement>event.target).checked;
    this.temaService.setDark(isDark);
  }

  getMenu() {
    return MENU;
  }

  getUpload() {
    return MenuComponent.upload
  }

  logout(): void {
    this.authService.logout();
  }

  voltarAoPortal(): void {
    this.authService.voltarAoPortal();
  }

}
