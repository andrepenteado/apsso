import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PerfilSistema } from "../model/entities/perfil-sistema"
import { SISTEMA_URL } from "../etc/routes"
import { API_SISTEMAS } from "../etc/api"

@Injectable({
  providedIn: 'root'
})
export class PerfilSistemaService {

  constructor(
      private http: HttpClient
  ) { }

  public listar(): Observable<PerfilSistema[]> {
    return this.http.get<PerfilSistema[]>(`${SISTEMA_URL.backendURL}${API_SISTEMAS}/perfis`);
  }

  public listarPorSistema(idSistema: string): Observable<PerfilSistema[]> {
    return this.http.get<PerfilSistema[]>(`${SISTEMA_URL.backendURL}${API_SISTEMAS}/${idSistema}/perfis`);
  }

  public incluir(perfilSistema: any): Observable<PerfilSistema> {
    return this.http.post<PerfilSistema>(`${SISTEMA_URL.backendURL}${API_SISTEMAS}/perfil`, perfilSistema);
  }

  public excluir(authority: string): Observable<any> {
    return this.http.delete(`${SISTEMA_URL.backendURL}${API_SISTEMAS}/perfil/${authority}`);
  }

}
