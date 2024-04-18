import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http"
import { Observable } from "rxjs"
import { Upload } from "../dtos/upload"
import { SISTEMA_URL } from "../../../etc/routes"

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  constructor(
    private http: HttpClient
  ) { }

  public buscar(uuid: string): Observable<Upload> {
    return this.http.get<Upload>(`${SISTEMA_URL.backendURL}/upload/${uuid}`);
  }

  public incluir(upload: Upload): Observable<Upload> {
    return this.http.post<Upload>(`${SISTEMA_URL.backendURL}/upload`, upload);
  }

  public alterar(upload: Upload): Observable<Upload> {
    return this.http.put<Upload>(`${SISTEMA_URL.backendURL}/upload/${upload.uuid}`, upload);
  }

}
