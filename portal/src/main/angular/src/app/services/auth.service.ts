import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from '../../environments/environment';
import {Api} from "../config/api";
import {Usuario} from "../models/usuario";
import {lastValueFrom} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
      private http: HttpClient
  ) { }

  public async usuarioLogado(): Promise<Usuario> {
    let usuario = JSON.parse(localStorage.getItem("usuarioLogado")) as Usuario;
    if (usuario == null) {
      const usuario$ = this.http.get<Usuario>(`${environment.backendURL}${Api.AUTH}/usuario`);
      usuario = await lastValueFrom(usuario$);
      localStorage.setItem("usuarioLogado", JSON.stringify(usuario));
      return usuario;
    }
    return usuario;
  }

  public logout(): void {
    localStorage.clear();
    window.location.href = '/portal/logout';
  }

}
