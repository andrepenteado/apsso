import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_EMPRESAS } from "../config/api"
import { environment } from "../../environments/environment";
import { Empresa } from "../domain/entities/empresa";

@Injectable({
  providedIn: 'root'
})
export class EmpresaService {

  constructor(
      private http: HttpClient
  ) { }

  public listar(): Observable<Empresa[]> {
    return this.http.get<Empresa[]>(`${environment.urlBackend}${API_EMPRESAS}`);
  }

  public buscar(id: number): Observable<Empresa> {
    return this.http.get<Empresa>(`${environment.urlBackend}${API_EMPRESAS}/${id}`);
  }

  public incluir(empresa: any): Observable<Empresa> {
    return this.http.post<Empresa>(`${environment.urlBackend}${API_EMPRESAS}`, empresa);
  }

  public alterar(empresa: any): Observable<Empresa> {
    return this.http.put<Empresa>(`${environment.urlBackend}${API_EMPRESAS}/${empresa.id}`, empresa);
  }

  public excluir(id: number): Observable<any> {
    return this.http.delete(`${environment.urlBackend}${API_EMPRESAS}/${id}`);
  }

  public compareFn(e1: Empresa, e2: Empresa): boolean {
    return e1 && e2 ? e1.id === e2.id : e1 === e2;
  }

}
