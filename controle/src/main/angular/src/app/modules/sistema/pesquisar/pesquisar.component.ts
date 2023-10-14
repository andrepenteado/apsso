import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Core } from '../../../config/core';
import { DecoracaoMensagem, ExibeMensagemComponent } from '../../core/components/exibe-mensagem.component';
import { Sistema } from '../../../entities/sistema';
import { Subject } from 'rxjs';
import { SistemaService } from '../../../services/sistema.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-pesquisar',
  templateUrl: './pesquisar.component.html',
  styles: [
  ]
})
export class PesquisarComponent implements OnInit, OnDestroy {

  @ViewChild('exibeMensagem')
  exibeMensagem: ExibeMensagemComponent = new ExibeMensagemComponent();

  aguardar = true;

  dtOptions: DataTables.Settings = Core.DATATABLES_OPTIONS;
  dtTrigger: Subject<any> = new Subject<any>();

  lista: Sistema[];

  constructor(
      private sistemaService: SistemaService,
      private router: Router
  ) { }

  ngOnInit(): void {
    this.pesquisar();
  }

  ngOnDestroy(): void {
    // Do not forget to unsubscribe the event
    this.dtTrigger.unsubscribe();
  }

  pesquisar(): void {
    this.sistemaService.listar().subscribe({
      next: listaSistemas => {
        this.lista = listaSistemas;
        this.dtTrigger.next(null);
        this.aguardar = false;
      },
      error: objetoErro => {
        if (objetoErro.error.status == "403") {
          this.router.navigate(["/pages/acesso-negado"]);
        }
        else {
          this.exibeMensagem.show(
            `${objetoErro.error.message}`,
            DecoracaoMensagem.ERRO,
            'Erro no processamento'
          );
        }
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
    Swal.fire({
      title: 'Excluir?',
      text: `Confirma a exclusão do sistema ${sistema.nome}`,
      icon: 'question',
      showCloseButton: true,
      showCancelButton: true,
      confirmButtonText: '<i class=\'fa fa-trash\'></i> Sim, Excluir',
      cancelButtonText: 'Cancelar'
    }).then((resposta) => {
      if (resposta.value) {
        this.sistemaService.excluir(sistema.id).subscribe({
          next: () => this.pesquisar(),
          error: objetoErro => {
            this.exibeMensagem.show(
              `${objetoErro.error.message}`,
              DecoracaoMensagem.ERRO,
              'Erro de processamento'
            );
          }
        });
      }
    });
  }

}
