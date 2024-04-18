import { Injectable } from '@angular/core';
import { Router } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { lastValueFrom } from "rxjs";
import { UserLogin } from "../dtos/user-login"
import { SISTEMA_URL } from "../../../etc/routes"
import { TemaService } from "./tema.service"

@Injectable({
  providedIn: 'root'
})
export abstract class AbstractAuthService {

  abstract readonly COOKIE_USUARIO: string;

  abstract readonly PREFIXO_PERFIL: string;

  userLogin: UserLogin;

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  async usuarioLogado(): Promise<UserLogin> {
    let userLogin = JSON.parse(sessionStorage.getItem(this.COOKIE_USUARIO) as string) as UserLogin;
    if (userLogin == null) {
      const userLogin$ = this.http.get<UserLogin>(`${SISTEMA_URL.backendURL}/auth/usuario`);
      userLogin = await lastValueFrom(userLogin$);
    }
    if (userLogin != null) {
      this.userLogin = userLogin;
      userLogin.perfilAtual = this.nomePerfil();
      sessionStorage.setItem(this.COOKIE_USUARIO, JSON.stringify(userLogin));
    }
    if (!this.isPermitido())
      await this.router.navigate(["/acesso-negado"]);
    return userLogin;
  }

  nomePerfil(): string {
    for (const nome of Object.keys(this.userLogin.perfis)) {
      if (nome.startsWith(this.PREFIXO_PERFIL))
        return nome.replace(this.PREFIXO_PERFIL,"");
    }
    return "Sem Perfil";
  }

  isPermitido(): boolean {
    if (this.userLogin == null) {
      return false;
    }
    if (this.userLogin.perfis == null || this.userLogin.perfis.size < 1) {
      return false;
    }
    if (this.nomePerfil() == "Sem Perfil") {
      return false;
    }
    return true;
  }

  logout(): void {
    this.limparSessao();
    window.location.href = '/logout';
  }

  voltarAoPortal(): void {
    this.limparSessao();
    window.location.href = SISTEMA_URL.portalURL;
  }

  private limparSessao() {
    let tema = sessionStorage.getItem(TemaService.TEMA_COOKIE);
    sessionStorage.clear();
    localStorage.clear();
    sessionStorage.setItem(TemaService.TEMA_COOKIE, tema);
  }

}
