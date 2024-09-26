import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { API_USUARIOS } from "../etc/api";
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(
    private http: HttpClient,
  ) { }

  public alterarSenha(senha: string): Observable<any> {
    return this.http.put(`${environment.urlBackend}${API_USUARIOS}/alterar-senha`, senha);
  }

  public atualizarFoto(uuidFoto: string): Observable<any> {
    return this.http.put(`${environment.urlBackend}${API_USUARIOS}/atualizar-foto`, uuidFoto);
  }

}
