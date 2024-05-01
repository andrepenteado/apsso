import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { SISTEMA_URL } from "../etc/routes"
import { API_USUARIOS } from "../etc/api";
import { LoginService } from "@andrepenteado/ngx-apcore";

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  readonly bearer = new HttpHeaders()
    .set("Content-type", "application/x-www-form-urlencoded")
    .set("Authorization", "Bearer " + this.loginService.getBearerToken());

  constructor(
    private http: HttpClient,
    private loginService: LoginService
  ) { }

  public alterarSenha(senha: string): Observable<any> {
    return this.http.put(`${SISTEMA_URL.backendURL}${API_USUARIOS}/alterar-senha`, senha, { headers: this.bearer });
  }

  public atualizarFoto(uuidFoto: string): Observable<any> {
    return this.http.put(`${SISTEMA_URL.backendURL}${API_USUARIOS}/atualizar-foto`, uuidFoto, { headers: this.bearer });
  }

}
