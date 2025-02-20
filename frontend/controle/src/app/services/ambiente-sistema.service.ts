import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_SISTEMAS } from "../config/api"
import { environment } from "../../environments/environment";
import { AmbienteSistema } from "../domain/entities/ambiente-sistema";

@Injectable({
  providedIn: 'root'
})
export class AmbienteSistemaService {

  constructor(
      private http: HttpClient
  ) { }

  public listarPorSistema(idSistema: number): Observable<AmbienteSistema[]> {
    return this.http.get<AmbienteSistema[]>(`${environment.urlBackend}${API_SISTEMAS}/${idSistema}/ambientes`);
  }

  public incluirOuAlterar(ambienteSistema: any): Observable<AmbienteSistema> {
    return this.http.post<AmbienteSistema>(`${environment.urlBackend}${API_SISTEMAS}/ambiente`, ambienteSistema);
  }

  public excluir(id: string): Observable<any> {
    return this.http.delete(`${environment.urlBackend}${API_SISTEMAS}/ambiente/${id}`);
  }

}
