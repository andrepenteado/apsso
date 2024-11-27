import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { ngxLoadingAnimationTypes } from "ngx-loading"
import { UnidadeAdministrativaService } from "../../../services/unidade-administrativa.service"
import { UnidadeAdministrativa } from "../../../domain/entities/unidade-administrativa";
import { Datatables, ExibirMensagemService } from "@andre.penteado/ngx-apcore";

@Component({
  selector: 'apadmin-unidade-administrativa-pesquisar',
  templateUrl: './pesquisar.component.html',
  styles: ``
})
export class PesquisarComponent implements OnInit {

  lista: UnidadeAdministrativa[] = [];
  aguardar = true;

  constructor(
    private unidadeAdministrativaService: UnidadeAdministrativaService,
    private router: Router,
    private exibirMensagem: ExibirMensagemService
  ) { }

  ngOnInit(): void {
    this.pesquisar();
  }

  pesquisar(): void {
    this.unidadeAdministrativaService
      .listar()
      .subscribe({
          next: listaUnidadesAdministrativas => {
            this.lista = listaUnidadesAdministrativas;
            this.aguardar = false;
            setTimeout(() => {
              $('#datatable-pesquisar-unidades-administrativas').DataTable(Datatables.config);
            }, 5);
          }
        }
      );
  }

  incluir(): void {
    this.router.navigate([`/unidade-administrativa/cadastro`]);
  }

  editar(unidadeAdministrativa: UnidadeAdministrativa): void {
    this.router.navigate([`/unidade-administrativa/cadastro/${unidadeAdministrativa.id}`]);
  }

  excluir(unidadeAdministrativa: UnidadeAdministrativa): void {
    this.exibirMensagem
      .showConfirm(`Confirma a exclusão da unidade administrativa ${unidadeAdministrativa.nome}`, "Excluir?")
      .then((resposta) => {
          if (resposta.value) {
            this.unidadeAdministrativaService.excluir(unidadeAdministrativa.id).subscribe({
              next: () => {
                this.pesquisar()
              }
            });
          }
        }
      );
  }

  protected readonly ngxLoadingAnimationTypes = ngxLoadingAnimationTypes
}
