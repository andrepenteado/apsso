import { Component, OnInit } from '@angular/core';
import {SistemaService} from "../../../services/sistema.service";
import {Sistema} from "../../../models/sistema";

@Component({
  selector: 'app-home',
  template: `
    <div class="my-gallery card-body row gallery-with-description text-center" itemscope="" gallerize>
      <figure class="col-6 col-md-4" itemprop="associatedMedia" *ngFor="let sistema of this.lista" >
        <a href="javascript:void(0)" (click)="acessar(sistema.urlEntrada)" itemprop="contentUrl">
          <img src="assets/images/sistema.png" class="img-fluid" />
          <div class="caption">
            <h4>{{ sistema.id }}</h4>
            <p>{{ sistema.descricao }}</p>
          </div>
        </a>
      </figure>
    </div>  `,
  styles: [
  ]
})
export class HomeComponent implements OnInit {

  lista: Sistema[];

  constructor(
    private sistemaService: SistemaService,
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
        console.log("ERRO AO LISTAR SISTEMAS");
      }
    });
  }

  acessar(url: string): void {
    window.location.href = url;
  }

}
