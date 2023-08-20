import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Api } from '../config/api';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(
      private http: HttpClient
  ) { }

  public listar(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${environment.backendURL}${Api.USUARIOS}`);
  }

  public buscar(username: string): Observable<Usuario> {
    return this.http.get<Usuario>(`${environment.backendURL}${Api.USUARIOS}/${username}`);
  }

  public gravar(usuario: any, incluir: boolean): Observable<Usuario> {
    if (incluir) {
      return this.http.post<Usuario>(`${environment.backendURL}${Api.USUARIOS}`, usuario);
    }
    else {
      return this.http.put<Usuario>(`${environment.backendURL}${Api.USUARIOS}/${usuario.username}`, usuario);
    }
  }

  public excluir(id: number): Observable<any> {
    return this.http.delete(`${environment.backendURL}${Api.USUARIOS}/${id}`);
  }

}
