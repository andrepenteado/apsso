import { Injectable } from '@angular/core';
import { AbstractAuthService } from "../libs/core/services/abstract-auth.service"
import { HttpClient } from "@angular/common/http"
import { Router } from "@angular/router"

@Injectable({
  providedIn: 'root'
})
export class AuthService extends AbstractAuthService {

  constructor(http: HttpClient, router: Router) {
    super(http, router);
  }

  readonly COOKIE_USUARIO: string = "PORTAL_USER_COOKIE";
  readonly PREFIXO_PERFIL: string = "ROLE_Portal_";

}
