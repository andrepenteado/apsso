import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Sistema } from '../models/sistema';
import { Api } from '../config/api';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SistemaService {

  constructor(
      private http: HttpClient
  ) { }

  public listar(): Observable<Sistema[]> {
    return this.http.get<Sistema[]>(`${environment.backendURL}${Api.SISTEMAS}`);
  }

  public buscar(id: string): Observable<Sistema> {
    return this.http.get<Sistema>(`${environment.backendURL}${Api.SISTEMAS}/${id}`);
  }

}
