import { Component, OnInit } from "@angular/core";
import { ngxLoadingAnimationTypes, NgxLoadingModule } from "ngx-loading";
import { LayoutPadraoComponent, LoginService } from "@andre.penteado/ngx-apcore";
import { Router } from "@angular/router";
import { EquipeUserLogin } from "./equipe-user-login";
import { environment } from "../../environments/environment";

@Component({
  selector: 'equipe-authorized',
  standalone: true,
  imports: [
    NgxLoadingModule
  ],
  template: `
    <ngx-loading
      [show]="true"
      [config]="{
        primaryColour: '#dd0031',
        secondaryColour: '#fff',
        fullScreenBackdrop: true,
        animationType: ngxLoadingAnimationTypes.doubleBounce
      }">
    </ngx-loading>
  `,
  styles: ``
})
export class AuthorizedComponent implements OnInit {

  constructor(private loginService: LoginService, private router: Router) {}

  async ngOnInit() {
    await this.loginService.login<EquipeUserLogin>(`${environment.urlBackend}/auth/usuario-logado`);
    LayoutPadraoComponent.fotoBase64 = this.loginService.getUserLogin<EquipeUserLogin>().fotoBase64;
    this.router.navigate(["/pagina-inicial"]);
  }

  protected readonly ngxLoadingAnimationTypes = ngxLoadingAnimationTypes;

}
