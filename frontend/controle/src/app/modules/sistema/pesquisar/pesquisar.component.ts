import { Component, OnInit } from '@angular/core';
import { SistemaService } from '../../../services/sistema.service';
import { Router } from '@angular/router';
import { Sistema } from "../../../domain/entities/sistema"
import { ngxLoadingAnimationTypes } from "ngx-loading"
import { Datatables, ExibirMensagemService } from "@andre.penteado/ngx-apcore"

@Component({
  selector: 'app-pesquisar',
  templateUrl: './pesquisar.component.html',
  styles: [
  ]
})
export class PesquisarComponent implements OnInit {

  lista: Sistema[];
  aguardar = true;

  constructor(
      private sistemaService: SistemaService,
      private router: Router,
      private exibirMensagem: ExibirMensagemService
  ) { }

  ngOnInit(): void {
    this.pesquisar();
  }

  pesquisar(): void {
    this.sistemaService.listar().subscribe({
      next: listaSistemas => {
        this.lista = listaSistemas;
        this.aguardar = false;
        setTimeout(() => {
          $('#datatable-pesquisar-sistema').DataTable(Datatables.config);
        }, 5);
      }
    });
  }

  incluir(): void {
    this.router.navigate([`/sistema/cadastro`]);
  }

  editar(sistema): void {
    this.router.navigate([`/sistema/cadastro/${sistema.id}`]);
  }

  excluir(sistema): void {
    this.exibirMensagem
      .showConfirm(`Confirma a exclusão do sistema ${sistema.nome}`, "Excluir?")
      .then((resposta) => {
      if (resposta.value) {
        this.sistemaService.excluir(sistema.id).subscribe({
          next: () => window.location.reload()
        });
      }
    });
  }

  protected readonly ngxLoadingAnimationTypes = ngxLoadingAnimationTypes
}
