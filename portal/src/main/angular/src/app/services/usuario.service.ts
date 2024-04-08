import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { SISTEMA_URL } from "../etc/routes"
import { Api } from "../etc/api"

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(
    private http: HttpClient
  ) { }

  public alterarSenha(senha: string): Observable<any> {
    return this.http.put(`${SISTEMA_URL.backendURL}${Api.USUARIOS}/alterar-senha`, senha);
  }

}
