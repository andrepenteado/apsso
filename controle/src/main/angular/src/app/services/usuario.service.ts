import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SISTEMA_URL } from "../etc/routes"
import { Api } from "../etc/api"
import { Usuario } from "../model/entities/usuario"

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(
      private http: HttpClient
  ) { }

  public listar(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${SISTEMA_URL.backendURL}${Api.USUARIOS}`);
  }

  public buscar(username: string): Observable<Usuario> {
    return this.http.get<Usuario>(`${SISTEMA_URL.backendURL}${Api.USUARIOS}/${username}`);
  }

  public gravar(usuario: any, incluir: boolean): Observable<Usuario> {
    if (incluir) {
      return this.http.post<Usuario>(`${SISTEMA_URL.backendURL}${Api.USUARIOS}`, usuario);
    }
    else {
      return this.http.put<Usuario>(`${SISTEMA_URL.backendURL}${Api.USUARIOS}/${usuario.username}`, usuario);
    }
  }

  public excluir(username: string): Observable<any> {
    return this.http.delete(`${SISTEMA_URL.backendURL}${Api.USUARIOS}/${username}`);
  }

}
