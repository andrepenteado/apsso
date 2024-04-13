import { Component, OnInit } from '@angular/core';
import { SistemaService } from "../../../services/sistema.service";
import { Router } from "@angular/router";
import { Sistema } from "../../../model/entities/sistema"

@Component({
  selector: 'app-listar-sistemas',
  template: `
    <nav aria-label="breadcrumb">
      <ol class="breadcrumb" style="margin-top: -23px">
        <li class="breadcrumb-item"><a routerLink="/pagina-inicial"><i class="bi bi-house-door"></i></a></li>
        <li class="breadcrumb-item active">Acessar Sistemas</li>
      </ol>
    </nav>
    <div class="my-gallery card-body row gallery-with-description text-center" itemscope="" gallerize>
      <figure class="col-6 col-md-4" itemprop="associatedMedia" *ngFor="let sistema of this.lista">
        <img class="img-fluid float-right rounded-circle" src="{{ sistema.iconeBase64 }}" width="120" height="120"/>
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
    private router: Router
  ) { }

  ngOnInit(): void {
    this.pesquisar();
  }

  pesquisar(): void {
    this.sistemaService.listar().subscribe({
      next: listaSistemas => {
        this.lista = listaSistemas;
      },
      error: objetoErro => {
        if (objetoErro.error.status == "403") {
          this.router.navigate(["/acesso-negado"]);
        }
        else {
          this.router.navigate(["/erro-processamento"]);
        }
      }
    });
  }

  acessar(url: string): void {
    window.location.href = url;
  }

}
