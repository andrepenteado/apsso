import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { SistemaService } from '../../../services/sistema.service';
import { Router } from '@angular/router';
import { Sistema } from "../../../model/entities/sistema"
import { DataTableDirective } from "angular-datatables"
import { ngxLoadingAnimationTypes } from "ngx-loading"
import { DATATABLES_OPTIONS, ExibirMensagemService } from "@andrepenteado/ngx-apcore"

@Component({
  selector: 'app-pesquisar',
  templateUrl: './pesquisar.component.html',
  styles: [
  ]
})
export class PesquisarComponent implements AfterViewInit, OnInit, OnDestroy {

  @ViewChild(DataTableDirective, {static: false})
  dtElement: DataTableDirective;

  dtOptions: DataTables.Settings = DATATABLES_OPTIONS;
  dtTrigger: Subject<any> = new Subject<any>();

  lista: Sistema[];
  aguardar = true;

  constructor(
      private sistemaService: SistemaService,
      private router: Router,
      private exibirMensagem: ExibirMensagemService
  ) { }

  ngAfterViewInit(): void {
    this.dtTrigger.next(null);
  }

  ngOnInit(): void {
    this.pesquisar();
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.destroy();
      this.dtTrigger.next(null);
    });
  }

  pesquisar(): void {
    this.sistemaService.listar().subscribe({
      next: listaSistemas => {
        this.lista = listaSistemas;
        this.rerender();
        this.aguardar = false;
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
          next: () => this.pesquisar()
        });
      }
    });
  }

  protected readonly ngxLoadingAnimationTypes = ngxLoadingAnimationTypes
}
