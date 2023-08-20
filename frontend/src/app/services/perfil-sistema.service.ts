import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {PerfilSistema} from '../models/perfil-sistema';
import {environment} from '../../environments/environment';
import {Api} from '../config/api';

@Injectable({
  providedIn: 'root'
})
export class PerfilSistemaService {

  constructor(
      private http: HttpClient
  ) { }

  public listar(): Observable<PerfilSistema[]> {
    return this.http.get<PerfilSistema[]>(`${environment.backendURL}${Api.SISTEMAS}/perfis`);
  }

  public listarPorSistema(idSistema: string): Observable<PerfilSistema[]> {
    return this.http.get<PerfilSistema[]>(`${environment.backendURL}${Api.SISTEMAS}/${idSistema}/perfis`);
  }

  public incluir(perfilSistema: any): Observable<PerfilSistema> {
    return this.http.post<PerfilSistema>(`${environment.backendURL}${Api.SISTEMAS}/perfil`, perfilSistema);
  }

  public excluir(id: number): Observable<any> {
    return this.http.delete(`${environment.backendURL}${Api.SISTEMAS}/perfil/${id}`);
  }

}
