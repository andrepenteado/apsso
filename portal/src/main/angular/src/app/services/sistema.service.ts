import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Sistema } from "../model/entities/sistema"
import { Api } from "../etc/api"
import { SISTEMA_URL } from "../etc/routes"

@Injectable({
  providedIn: 'root'
})
export class SistemaService {

  constructor(
      private http: HttpClient
  ) { }

  public listar(): Observable<Sistema[]> {
    return this.http.get<Sistema[]>(`${SISTEMA_URL.backendURL}${Api.SISTEMAS}`);
  }

  public buscar(id: string): Observable<Sistema> {
    return this.http.get<Sistema>(`${SISTEMA_URL.backendURL}${Api.SISTEMAS}/${id}`);
  }

}
