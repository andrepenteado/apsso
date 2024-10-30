import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_AMBIENTE_SISTEMAS } from "../config/api";
import { environment } from "../../environments/environment";
import { AmbienteSistema } from "../domain/entities/ambiente-sistema";

@Injectable({
  providedIn: 'root'
})
export class AmbienteSistemaService {

  constructor(
      private http: HttpClient
  ) { }

  public listar(): Observable<AmbienteSistema[]> {
    return this.http.get<AmbienteSistema[]>(`${environment.urlBackend}${API_AMBIENTE_SISTEMAS}`);
  }

  public buscar(id: string): Observable<AmbienteSistema> {
    return this.http.get<AmbienteSistema>(`${environment.urlBackend}${API_AMBIENTE_SISTEMAS}/${id}`);
  }

}
