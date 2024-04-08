import { Injectable } from '@angular/core';
import { Endereco } from "../dtos/endereco";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ViaCepService {

  constructor(
    private http: HttpClient
  ) { }

  public consultarCep(cep: string): Observable<Endereco> {
    return this.http.get<Endereco>(`https://viacep.com.br/ws/${cep}/json/`);
  }

}
