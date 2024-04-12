import { Injectable } from '@angular/core';
import { UserLogin } from "../libs/core/dtos/user-login";
import { Router } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { lastValueFrom } from "rxjs";
import { Api } from "../etc/api"
import { SISTEMA_URL } from "../etc/routes"

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  readonly COOKIE_USUARIO = "apadminUsuarioLogado";

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  public async usuarioLogado(): Promise<UserLogin> {
    let userLogin = JSON.parse(sessionStorage.getItem(this.COOKIE_USUARIO) as string) as UserLogin;
    if (userLogin == null) {
      const userLogin$ = this.http.get<UserLogin>(`${SISTEMA_URL.backendURL}${Api.AUTH}/usuario`);
      userLogin = await lastValueFrom(userLogin$);
      if (userLogin != null) {
        userLogin.perfilAtual = this.nomePerfil(userLogin);
        sessionStorage.setItem(this.COOKIE_USUARIO, JSON.stringify(userLogin));
      }
    }
    if (!this.isPermitido(userLogin))
      await this.router.navigate(["/acesso-negado"]);
    return userLogin;
  }

  public logout(): void {
    sessionStorage.clear();
    window.location.href = '/logout';
  }

  public voltarAoPortal(): void {
    sessionStorage.clear();
    window.location.href = SISTEMA_URL.portalURL;
  }

  nomePerfil(userLogin: UserLogin): string {
    for (const nome of Object.keys(userLogin.perfis)) {
      if (nome.startsWith("ROLE_Controle_"))
        return nome.replace("ROLE_Controle_","");
    }
    return "Sem Perfil";
  }

  isPermitido(userLogin: UserLogin): boolean {
    if (userLogin == null)
      return false;
    if (userLogin.perfis == null || userLogin.perfis.size < 1)
      return false;
    if (this.nomePerfil(userLogin) == "Sem Perfil")
      return false;
    return true;
  }

}
