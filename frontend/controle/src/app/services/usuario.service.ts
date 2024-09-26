import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from "../model/entities/usuario"
import { API_USUARIOS } from "../etc/api"
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(
      private http: HttpClient
  ) { }

  public listar(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${environment.urlBackend}${API_USUARIOS}`);
  }

  public buscar(username: string): Observable<Usuario> {
    return this.http.get<Usuario>(`${environment.urlBackend}${API_USUARIOS}/${username}`);
  }

  public gravar(usuario: any, incluir: boolean): Observable<Usuario> {
    if (incluir) {
      return this.http.post<Usuario>(`${environment.urlBackend}${API_USUARIOS}`, usuario);
    }
    else {
      return this.http.put<Usuario>(`${environment.urlBackend}${API_USUARIOS}/${usuario.username}`, usuario);
    }
  }

  public excluir(username: string): Observable<any> {
    return this.http.delete(`${environment.urlBackend}${API_USUARIOS}/${username}`);
  }

}
