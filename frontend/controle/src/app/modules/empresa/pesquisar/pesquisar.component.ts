import { Component, OnInit } from '@angular/core';
import { EmpresaService } from "../../../services/empresa.service";
import { Router } from "@angular/router";
import { ngxLoadingAnimationTypes } from "ngx-loading"
import { Empresa } from "../../../domain/entities/empresa";
import { Datatables, ExibirMensagemService } from "@andre.penteado/ngx-apcore";

@Component({
  selector: 'admin-empresa-pesquisar',
  templateUrl: './pesquisar.component.html',
  styles: [
  ]
})
export class PesquisarComponent implements OnInit {

  lista: Empresa[] = [];
  aguardar = true;

  constructor(
    private empresaService: EmpresaService,
    private router: Router,
    private exibirMensagem: ExibirMensagemService
  ) { }

  ngOnInit(): void {
    this.pesquisar();
  }

  pesquisar(): void {
    this.empresaService
      .listar()
      .subscribe({
        next: listaEmpresas => {
          this.lista = listaEmpresas;
          this.aguardar = false;
          setTimeout(() => {
            $('#datatable-pesquisar-empresas').DataTable(Datatables.config);
          }, 5);

        }
      }
    );
  }

  incluir(): void {
    this.router.navigate([`/empresa/cadastro`]);
  }

  editar(empresa: Empresa): void {
    this.router.navigate([`/empresa/cadastro/${empresa.id}`]);
  }

  excluir(empresa: Empresa): void {
    this.exibirMensagem
      .showConfirm(`Confirma a exclusão da empresa ${empresa.nomeFantasia}`, "Excluir?")
      .then((resposta) => {
        if (resposta.value) {
          this.empresaService.excluir(empresa.id).subscribe({
            next: () => {
              window.location.reload();
            }
          });
        }
      }
    );
  }

  protected readonly ngxLoadingAnimationTypes = ngxLoadingAnimationTypes
}
