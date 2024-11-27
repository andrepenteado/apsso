import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment"
import { Colaborador } from "../domain/entities/colaborador";
import { API_COLABORADORES } from "../config/api";

@Injectable({
  providedIn: 'root'
})
export class ColaboradorService {

  constructor(
    private http: HttpClient
  ) { }

  public listar(): Observable<Colaborador[]> {
    return this.http.get<Colaborador[]>(`${environment.urlBackend}${API_COLABORADORES}`);
  }

  public buscar(id: number): Observable<Colaborador> {
    return this.http.get<Colaborador>(`${environment.urlBackend}${API_COLABORADORES}/${id}`);
  }

  public incluir(colaborador: any): Observable<Colaborador> {
    return this.http.post<Colaborador>(`${environment.urlBackend}${API_COLABORADORES}`, colaborador);
  }

  public alterar(colaborador: any): Observable<Colaborador> {
    return this.http.put<Colaborador>(`${environment.urlBackend}${API_COLABORADORES}/${colaborador.id}`, colaborador);
  }

  public excluir(id: number): Observable<any> {
    return this.http.delete(`${environment.urlBackend}${API_COLABORADORES}/${id}`);
  }

  public compareFn(c1: Colaborador, c2: Colaborador): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

}
