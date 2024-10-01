import { Component, OnInit } from '@angular/core';
import { SistemaService } from "../../../services/sistema.service";
import { Sistema } from "../../../model/entities/sistema"
import { UploadService } from "@andre.penteado/ngx-apcore";
import { lastValueFrom } from "rxjs"

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
      <figure class="col-6 col-md-4" itemprop="associatedMedia" *ngFor="let sistema of this.lista">
        <img *ngIf="sistema.icone" class="img-fluid float-right rounded-circle" id="image-{{ sistema.icone }}" style="width: 120px; height: 120px;"/>
        <img *ngIf="!sistema.icone" class="img-fluid float-right rounded-circle" src="/assets/images/sem-imagem.gif" style="width: 120px; height: 120px;"/>
        <div class="caption">
          <h3>{{ sistema.id }}</h3>
          <p class="form-text">{{ sistema.descricao }}</p>
        </div>
        <div *ngFor="let url of sistema.urlEntrada.split(';')">
          <a href="javascript:void(0)" (click)="acessar(url)">{{ url }}</a><br>
        </div>
      </figure>
    </div>
  `,
  styles: [
  ]
})
export class ListarSistemasComponent implements OnInit {

  lista: Sistema[];

  constructor(
    private sistemaService: SistemaService,
    private uploadService: UploadService
  ) { }

  ngOnInit(): void {
    this.pesquisar();
  }

  async pesquisar() {
    this.lista = await lastValueFrom(this.sistemaService.listar());
    this.lista.forEach((sistema: Sistema) => {
      if (sistema.icone) {
        this.uploadService.buscar(sistema.icone).subscribe(upload => {
          document
            .getElementById(`image-${sistema.icone}`)
            .setAttribute('src', upload.base64);
        });
      }
    });
  }

  acessar(url: string): void {
    window.location.href = url;
  }

}
