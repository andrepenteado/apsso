import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioService } from '../../../services/usuario.service';
import { Subject } from 'rxjs';
import { Usuario } from "../../../model/entities/usuario"
import { DataTableDirective } from "angular-datatables"
import { DATATABLES_OPTIONS } from "../../../etc/datatables"
import { DecoracaoMensagem, ExibirMensagemService } from "../../../libs/core/services/exibir-mensagem.service"

@Component({
  selector: 'app-pesquisar',
  templateUrl: './pesquisar.component.html',
  styles: [
  ]
})
export class PesquisarComponent implements AfterViewInit, OnInit, OnDestroy {

  aguardar = true;

  @ViewChild(DataTableDirective, {static: false})
  dtElement: DataTableDirective;

  dtOptions: DataTables.Settings = DATATABLES_OPTIONS;
  dtTrigger: Subject<any> = new Subject<any>();

  lista: Usuario[];

  constructor(
      private usuarioService: UsuarioService,
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
    this.usuarioService.listar().subscribe({
      next: listaUsuarios => {
        this.lista = listaUsuarios;
        this.rerender();
        this.aguardar = false;
      },
      error: objetoErro => {
        if (objetoErro.error.status == "403") {
          this.router.navigate(["/acesso-negado"]);
        }
        else {
          this.exibirMensagem.showMessage(`${objetoErro.error.detail}`, "Erro de processamento", DecoracaoMensagem.ERRO);
        }
      }
    });
  }

  incluir(): void {
    this.router.navigate([`/usuario/cadastro`]);
  }

  editar(usuario): void {
    this.router.navigate([`/usuario/cadastro/${usuario.username}`]);
  }

  excluir(usuario): void {
    this.exibirMensagem
      .showConfirm(`Confirma a exclusão do usuário ${usuario.nome}`, "Excluir?")
      .then((resposta) => {
        if (resposta.value) {
          this.usuarioService.excluir(usuario.username).subscribe({
            next: () => this.pesquisar(),
            error: objetoErro => {
              this.exibirMensagem.showMessage(`${objetoErro.error.detail}`, "Erro de processamento", DecoracaoMensagem.ERRO);
            }
          });
        }
      });
  }

}
