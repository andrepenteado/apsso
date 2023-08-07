import {Component, OnInit, ViewChild} from '@angular/core';
import {DecoracaoMensagem, ExibeMensagemComponent} from "../../core/components/exibe-mensagem.component";
import {Usuario} from "../../../models/usuario";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {UsuarioService} from "../../../services/usuario.service";

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styles: [
  ]
})
export class CadastroComponent implements OnInit {

  @ViewChild('exibeMensagem')
  exibeMensagem: ExibeMensagemComponent = new ExibeMensagemComponent();

  aguardar: boolean = true;
  incluir: boolean = true;
  formEnviado: boolean = false;
  usuario: Usuario;

  // Campos do formulário
  username = new FormControl(null, Validators.required);
  password = new FormControl(null);
  nome = new FormControl(null);

  form = new FormGroup({
    username: this.username,
    password: this.password,
    nome: this.nome
  });

  constructor(
      private activedRoute: ActivatedRoute,
      private usuarioService: UsuarioService
  ) { }

  ngOnInit(): void {
    this.activedRoute.params.subscribe(params => {
      const username: string = params.username
      if (username) {
        this.pesquisar(username);
        this.incluir = false;
      }
    });
    this.aguardar = false;
  }

  pesquisar(username: string): void {
    this.usuarioService.buscar(username).subscribe(usuario => {
      this.usuario = usuario;
      this.form.patchValue(usuario);
    });
  }

  gravar(): void {
    this.formEnviado = true;
    if (this.form.valid) {
      this.usuarioService.gravar(this.form.value, this.incluir).subscribe({
        next: usuario => {
          this.usuario = usuario;
          this.form.reset();
          this.form.patchValue(usuario);
          this.exibeMensagem.show(
              `Dados do usuário ${usuario.nome} gravados com sucesso`,
              DecoracaoMensagem.SUCESSO,
              'Gravar Usuário'
          );
        },
        error: objetoErro => {
          this.exibeMensagem.show(
              `${objetoErro.error.message}`,
              DecoracaoMensagem.ERRO,
              'Erro de processamento'
          );
        }
      })
    }
    else {
      this.exibeMensagem.show(
          'Preencha todos os dados obrigatórios antes de gravar os dados',
          DecoracaoMensagem.PRIMARIO,
          'Dados Obrigatórios'
      );
    }
  }

}
