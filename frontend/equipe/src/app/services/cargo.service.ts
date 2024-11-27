import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment"
import { Cargo } from "../domain/entities/cargo";
import { API_CARGOS } from "../config/api";

@Injectable({
  providedIn: 'root'
})
export class CargoService {

  constructor(
    private http: HttpClient
  ) { }

  public listar(): Observable<Cargo[]> {
    return this.http.get<Cargo[]>(`${environment.urlBackend}${API_CARGOS}`);
  }

  public listarPorEmpresa(idEmpresa: number): Observable<Cargo[]> {
    return this.http.get<Cargo[]>(`${environment.urlBackend}${API_CARGOS}/empresa/${idEmpresa}`);
  }

  public buscar(id: number): Observable<Cargo> {
    return this.http.get<Cargo>(`${environment.urlBackend}${API_CARGOS}/${id}`);
  }

  public incluir(cargo: any): Observable<Cargo> {
    return this.http.post<Cargo>(`${environment.urlBackend}${API_CARGOS}`, cargo);
  }

  public alterar(cargo: any): Observable<Cargo> {
    return this.http.put<Cargo>(`${environment.urlBackend}${API_CARGOS}/${cargo.id}`, cargo);
  }

  public excluir(id: number): Observable<any> {
    return this.http.delete(`${environment.urlBackend}${API_CARGOS}/${id}`);
  }

  public compareFn(c1: Cargo, c2: Cargo): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

}
