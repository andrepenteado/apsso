import { Injectable } from '@angular/core';
import { Router } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { AbstractAuthService } from "../libs/core/services/abstract-auth.service"

@Injectable({
  providedIn: 'root'
})
export class AuthService extends AbstractAuthService {

  constructor(http: HttpClient, router: Router) {
    super(http, router);
  }

  readonly COOKIE_USUARIO: string = "CONTROLE_USER_COOKIE";
  readonly PREFIXO_PERFIL: string = "ROLE_Controle_";

}
