import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Api} from '../config/api';
import {PerfilUsuario} from '../models/perfil-usuario';

@Injectable({
  providedIn: 'root'
})
export class PerfilUsuarioService {

  constructor(
      private http: HttpClient
  ) { }

  public listarPorUsuario(username: string): Observable<PerfilUsuario[]> {
    return this.http.get<PerfilUsuario[]>(`${environment.backendURL}${Api.USUARIOS}/${username}/perfis`);
  }

  public incluir(perfilUsuario: any): Observable<PerfilUsuario> {
    return this.http.post<PerfilUsuario>(`${environment.backendURL}${Api.USUARIOS}/perfil`, perfilUsuario);
  }

  public excluir(perfilUsuario: PerfilUsuario): Observable<any> {
    return this.http.delete(`${environment.backendURL}${Api.USUARIOS}/${perfilUsuario.username}/${perfilUsuario.authority}`);
  }

}
