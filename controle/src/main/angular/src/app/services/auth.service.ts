import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment } from '../../environments/environment';
import { Api } from "../config/api";
import { lastValueFrom } from "rxjs";
import { UserLogin } from "../models/dto/user-login";
import { Router } from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
      private http: HttpClient,
      private router: Router
  ) { }

  public async usuarioLogado(): Promise<UserLogin> {
    let userLogin = JSON.parse(sessionStorage.getItem("usuarioLogado")) as UserLogin;
    if (userLogin == null) {
      const userLogin$ = this.http.get<UserLogin>(`${environment.backendURL}${Api.AUTH}/usuario`);
      userLogin = await lastValueFrom(userLogin$);
      if (userLogin != null)
        sessionStorage.setItem("usuarioLogado", JSON.stringify(userLogin));
    }
    if (!this.isPermitido(userLogin))
      await this.router.navigate(["/pages/acesso-negado"]);
    return userLogin;
  }

  public logout(): void {
    sessionStorage.clear();
    window.location.href = '/controle/logout';
  }

  public nomePerfil(userLogin: UserLogin): string {
    for (const nome of Object.keys(userLogin.perfis)) {
      if (nome.startsWith("ROLE_APcontrole_"))
        return userLogin.perfis[nome];
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
