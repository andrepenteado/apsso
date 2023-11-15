import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";
import { Api } from "../config/api";

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(
    private http: HttpClient
  ) { }

  public alterarSenha(senha: string): Observable<any> {
    return this.http.put(`${environment.backendURL}${Api.USUARIOS}/alterar-senha`, senha);
  }

}
