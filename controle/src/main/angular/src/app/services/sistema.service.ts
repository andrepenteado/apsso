import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SISTEMA_URL } from "../etc/routes"
import { Sistema } from "../model/entities/sistema"
import { API_SISTEMAS } from "../etc/api"

@Injectable({
  providedIn: 'root'
})
export class SistemaService {

  constructor(
      private http: HttpClient
  ) { }

  public listar(): Observable<Sistema[]> {
    return this.http.get<Sistema[]>(`${SISTEMA_URL.backendURL}${API_SISTEMAS}`);
  }

  public buscar(id: string): Observable<Sistema> {
    return this.http.get<Sistema>(`${SISTEMA_URL.backendURL}${API_SISTEMAS}/${id}`);
  }

  public gravar(sistema: any): Observable<Sistema> {
    return this.http.post<Sistema>(`${SISTEMA_URL.backendURL}${API_SISTEMAS}`, sistema);
  }

  public excluir(id: number): Observable<any> {
    return this.http.delete(`${SISTEMA_URL.backendURL}${API_SISTEMAS}/${id}`);
  }

}
