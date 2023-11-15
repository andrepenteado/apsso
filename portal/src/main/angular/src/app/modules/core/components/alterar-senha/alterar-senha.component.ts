import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { DecoracaoMensagem, ExibeMensagemComponent } from "../exibe-mensagem.component";
import { UsuarioService } from "../../../../services/usuario.service";

@Component({
  selector: 'app-alterar-senha',
  templateUrl: './alterar-senha.component.html',
  styles: [
  ]
})
export class AlterarSenhaComponent implements OnInit {

  @ViewChild('exibeMensagem')
  exibeMensagem: ExibeMensagemComponent = new ExibeMensagemComponent();

  formEnviado = false;
  aguardar = true;

  senha = new FormControl(null, Validators.required);
  repitaSenha = new FormControl(null, Validators.required);

  constructor(
    private service: UsuarioService
  ) { }

  formAlterarSenha = new FormGroup({
    senha: this.senha,
    repitaSenha: this.repitaSenha
  });

  ngOnInit(): void {
    this.aguardar = false;
  }

  gravar(): void {
    this.formEnviado = true;
    if (this.formAlterarSenha.valid) {
      if (this.formAlterarSenha.value.senha != this.formAlterarSenha.value.repitaSenha) {
        this.exibeMensagem.show(
          'Digite senhas iguais em ambos os campos',
          DecoracaoMensagem.PRIMARIO,
          'Senha não confere'
        );
      }
      else {
        this.service.alterarSenha(this.formAlterarSenha.value.senha).subscribe({
          next: obj => {
            this.exibeMensagem.show(
              `Senha alterada com sucesso`,
              DecoracaoMensagem.SUCESSO,
              'Alterar Senha'
            );
          },
          error: objetoErro => {
            this.exibeMensagem.show(
              `${objetoErro.error.message}`,
              DecoracaoMensagem.ERRO,
              'Erro de processamento'
            );
          }
      });
      }
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
