import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PerfilSistema } from "../domain/entities/perfil-sistema"
import { API_SISTEMAS } from "../config/api"
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class PerfilSistemaService {

  constructor(
      private http: HttpClient
  ) { }

  public listar(): Observable<PerfilSistema[]> {
    return this.http.get<PerfilSistema[]>(`${environment.urlBackend}${API_SISTEMAS}/perfis`);
  }

  public listarPorSistema(idSistema: string): Observable<PerfilSistema[]> {
    return this.http.get<PerfilSistema[]>(`${environment.urlBackend}${API_SISTEMAS}/${idSistema}/perfis`);
  }

  public incluir(perfilSistema: any): Observable<PerfilSistema> {
    return this.http.post<PerfilSistema>(`${environment.urlBackend}${API_SISTEMAS}/perfil`, perfilSistema);
  }

  public excluir(authority: string): Observable<any> {
    return this.http.delete(`${environment.urlBackend}${API_SISTEMAS}/perfil/${authority}`);
  }

}
