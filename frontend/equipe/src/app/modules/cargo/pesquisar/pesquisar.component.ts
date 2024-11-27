import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { ngxLoadingAnimationTypes } from "ngx-loading"
import { CargoService } from "../../../services/cargo.service";
import { Datatables, ExibirMensagemService } from "@andre.penteado/ngx-apcore";
import { Cargo } from "../../../domain/entities/cargo";

@Component({
  selector: 'admin-cargo-pesquisar',
  templateUrl: './pesquisar.component.html',
  styles: ``
})
export class PesquisarComponent implements OnInit {

  lista: Cargo[] = [];
  aguardar = true;

  constructor(
    private cargoService: CargoService,
    private router: Router,
    private exibirMensagem: ExibirMensagemService
  ) { }

  ngOnInit(): void {
    this.pesquisar();
  }

  pesquisar(): void {
    this.cargoService
      .listar()
      .subscribe({
          next: listaCargos => {
            this.lista = listaCargos;
            this.aguardar = false;
            setTimeout(() => {
              $('#datatable-pesquisar-cargos').DataTable(Datatables.config);
            }, 5);
          }
        }
      );
  }

  incluir(): void {
    this.router.navigate([`/cargo/cadastro`]);
  }

  editar(cargo: Cargo): void {
    this.router.navigate([`/cargo/cadastro/${cargo.id}`]);
  }

  excluir(cargo: Cargo): void {
    this.exibirMensagem
      .showConfirm(`Confirma a exclusão do cargo ${cargo.nome}`, "Excluir?")
      .then((resposta) => {
        if (resposta.value) {
          this.cargoService.excluir(cargo.id).subscribe({
            next: () => window.location.reload()
          });
        }
      });
  }

  protected readonly ngxLoadingAnimationTypes = ngxLoadingAnimationTypes
}
