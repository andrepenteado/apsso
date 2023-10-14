import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from '../../environments/environment';
import {Api} from "../config/api";
import {lastValueFrom} from "rxjs";
import {UserLogin} from "../dto/user-login";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
      private http: HttpClient
  ) { }

  public async usuarioLogado(): Promise<UserLogin> {
    let userLogin = JSON.parse(localStorage.getItem("usuarioLogado")) as UserLogin;
    if (userLogin == null) {
      const userLogin$ = this.http.get<UserLogin>(`${environment.backendURL}${Api.AUTH}/usuario`);
      userLogin = await lastValueFrom(userLogin$);
      localStorage.setItem("usuarioLogado", JSON.stringify(userLogin));
      return userLogin;
    }
    return userLogin;
  }

  public logout(): void {
    localStorage.clear();
    window.location.href = '/controle/logout';
  }

}
