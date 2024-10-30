import { Component, OnInit } from '@angular/core';
import { AmbienteSistemaService } from "../../../services/ambiente-sistema.service";
import { UploadService } from "@andre.penteado/ngx-apcore";
import { lastValueFrom } from "rxjs"
import { AmbienteSistema } from "../../../domain/entities/ambiente-sistema";

@Component({
  selector: 'app-listar-sistemas',
  template: `
    <nav aria-label="breadcrumb">
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a routerLink="/pagina-inicial"><i class="bi bi-house-door"></i></a></li>
        <li class="breadcrumb-item active">Acessar Sistemas</li>
      </ol>
    </nav>
    <div class="my-gallery card-body row gallery-with-description text-center" itemscope="" gallerize>
      <figure class="col-6 col-md-4" itemprop="associatedMedia" *ngFor="let ambiente of this.lista">
        <img *ngIf="ambiente.sistema.icone" class="img-fluid float-right rounded-circle" id="image-{{ ambiente.sistema.icone }}" style="width: 120px; height: 120px;"/>
        <img *ngIf="!ambiente.sistema.icone" class="img-fluid float-right rounded-circle" src="/assets/images/sem-imagem.gif" style="width: 120px; height: 120px;"/>
        <div class="caption">
          <h3>{{ ambiente.sistema.nome }}</h3>
          <p class="form-text">{{ ambiente.sistema.descricao }}</p>
        </div>
        <div *ngFor="let url of ambiente.urlEntrada.split(';')">
          <a href="javascript:void(0)" (click)="acessar(url)">{{ ambiente.descricao }}</a><br>
        </div>
      </figure>
    </div>
  `,
  styles: [
  ]
})
export class ListarSistemasComponent implements OnInit {

  lista: AmbienteSistema[] = [];

  constructor(
    private service: AmbienteSistemaService,
    private uploadService: UploadService
  ) { }

  ngOnInit(): void {
    this.pesquisar();
  }

  async pesquisar() {
    this.lista = await lastValueFrom(this.service.listar());
    this.lista.forEach((ambiente: AmbienteSistema) => {
      if (ambiente.sistema.icone) {
        this.uploadService.buscar(ambiente.sistema.icone).subscribe(upload => {
          document
            .getElementById(`image-${ambiente.sistema.icone}`)
            .setAttribute('src', upload.base64);
        });
      }
    });
  }

  acessar(url: string): void {
    window.location.href = url;
  }

}
