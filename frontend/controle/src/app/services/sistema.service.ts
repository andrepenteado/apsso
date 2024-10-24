import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Sistema } from "../domain/entities/sistema"
import { API_SISTEMAS } from "../config/api"
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class SistemaService {

  constructor(
      private http: HttpClient
  ) { }

  public listar(): Observable<Sistema[]> {
    return this.http.get<Sistema[]>(`${environment.urlBackend}${API_SISTEMAS}`);
  }

  public buscar(id: string): Observable<Sistema> {
    return this.http.get<Sistema>(`${environment.urlBackend}${API_SISTEMAS}/${id}`);
  }

  public gravar(sistema: any): Observable<Sistema> {
    return this.http.post<Sistema>(`${environment.urlBackend}${API_SISTEMAS}`, sistema);
  }

  public excluir(id: number): Observable<any> {
    return this.http.delete(`${environment.urlBackend}${API_SISTEMAS}/${id}`);
  }

}
