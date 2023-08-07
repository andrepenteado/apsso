import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { UsuarioService } from "../../../services/usuario.service";
import { Usuario } from "../../../models/usuario";
import { Core } from "../../../config/core";
import { Subject } from "rxjs";
import {DecoracaoMensagem, ExibeMensagemComponent} from "../../core/components/exibe-mensagem.component";
import Swal from 'sweetalert2';

@Component({
  selector: 'app-pesquisar',
  templateUrl: './pesquisar.component.html',
  styles: [
  ]
})
export class PesquisarComponent implements OnInit {

  @ViewChild('exibeMensagem')
  exibeMensagem: ExibeMensagemComponent = new ExibeMensagemComponent();

  aguardar: boolean = true;

  dtOptions: DataTables.Settings = Core.DATATABLES_OPTIONS;
  dtTrigger: Subject<any> = new Subject<any>();

  lista: Usuario[];

  constructor(
      private usuarioService: UsuarioService,
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
    this.usuarioService.listar().subscribe({
      next: listaUsuarios => {
        this.lista = listaUsuarios;
        this.dtTrigger.next(null);
        this.aguardar = false;
      },
      error: objetoErro => {
        this.exibeMensagem.show(
            `${objetoErro.error.message}`,
            DecoracaoMensagem.ERRO,
            'Erro no processamento'
        );
      }
    })
  }

  incluir(): void {
    this.router.navigate([`/usuario/cadastro`]);
  }

  editar(usuario): void {
    this.router.navigate([`/usuario/cadastro/${usuario.username}`]);
  }

  excluir(usuario): void {
    Swal.fire({
      title: 'Excluir?',
      text: `Confirma a exclusão do usuário ${usuario.nome}`,
      icon: 'question',
      showCloseButton: true,
      showCancelButton: true,
      confirmButtonText: '<i class=\'fa fa-trash\'></i> Sim, Excluir',
      cancelButtonText: 'Cancelar'
    }).then((resposta) => {
      if (resposta.value) {
        this.usuarioService.excluir(usuario.username).subscribe({
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
