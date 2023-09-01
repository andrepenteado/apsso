import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { environment } from '../../environments/environment';
import { Api } from "../config/api";
import {Usuario} from "../models/usuario";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
      private http: HttpClient
  ) { }

  public usuarioLogado(): Observable<Usuario> {
    return this.http.get<Usuario>(`${environment.backendURL}${Api.AUTH}/usuario`);
  }

  logout() {
    let headers = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'});

    this.http.post('auth/refresh/revoke', {}, { headers: headers })
      .subscribe({
        next: data => {
          window.location.href = `${environment.backendURL}`;
        }
      });
  }
}
