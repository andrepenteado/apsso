import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioService } from '../../../services/usuario.service';
import { Usuario } from "../../../domain/entities/usuario"
import { ngxLoadingAnimationTypes } from "ngx-loading"
import { Datatables, ExibirMensagemService } from "@andre.penteado/ngx-apcore"

@Component({
  selector: 'app-pesquisar',
  templateUrl: './pesquisar.component.html',
  styles: [
  ]
})
export class PesquisarComponent implements OnInit {

  aguardar = true;
  lista: Usuario[];

  constructor(
      private usuarioService: UsuarioService,
      private router: Router,
      private exibirMensagem: ExibirMensagemService
  ) { }

  ngOnInit(): void {
    this.pesquisar();
  }

  pesquisar(): void {
    this.usuarioService.listar().subscribe({
      next: listaUsuarios => {
        this.lista = listaUsuarios;
        this.aguardar = false;
        setTimeout(() => {
          $('#datatable-pesquisar-usuario').DataTable(Datatables.config);
        }, 5);
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
            next: () => window.location.reload()
          });
        }
      });
  }

  protected readonly ngxLoadingAnimationTypes = ngxLoadingAnimationTypes
}
