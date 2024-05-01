import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Sistema } from "../model/entities/sistema"
import { SISTEMA_URL } from "../etc/routes"
import { API_SISTEMAS } from "../etc/api";
import { LoginService } from "@andrepenteado/ngx-apcore";

@Injectable({
  providedIn: 'root'
})
export class SistemaService {

  readonly bearer = new HttpHeaders()
    .set("Content-type", "application/x-www-form-urlencoded")
    .set("Authorization", "Bearer " + this.loginService.getBearerToken());

  constructor(
      private http: HttpClient,
      private loginService: LoginService
  ) { }

  public listar(): Observable<Sistema[]> {
    return this.http.get<Sistema[]>(`${SISTEMA_URL.backendURL}${API_SISTEMAS}`, { headers: this.bearer });
  }

  public buscar(id: string): Observable<Sistema> {
    return this.http.get<Sistema>(`${SISTEMA_URL.backendURL}${API_SISTEMAS}/${id}`, { headers: this.bearer });
  }

}
