import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { UsuarioService } from "../../../../services/usuario.service";
import { DecoracaoMensagem, ExibirMensagemService } from "../../../../libs/core/services/exibir-mensagem.service"

@Component({
  selector: 'app-alterar-senha',
  templateUrl: './alterar-senha.component.html',
  styles: [
  ]
})
export class AlterarSenhaComponent implements OnInit {

  formEnviado = false;

  senha = new FormControl(null, Validators.required);
  repitaSenha = new FormControl(null, Validators.required);

  constructor(
    private service: UsuarioService,
    private exibirMensagem: ExibirMensagemService
  ) { }

  formAlterarSenha = new FormGroup({
    senha: this.senha,
    repitaSenha: this.repitaSenha
  });

  ngOnInit(): void {
  }

  gravar(): void {
    this.formEnviado = true;
    if (this.formAlterarSenha.valid) {
      if (this.formAlterarSenha.value.senha != this.formAlterarSenha.value.repitaSenha) {
        this.exibirMensagem.showMessage(
          "Digite senhas iguais em ambos os campos",
          "Senha não confere",
          DecoracaoMensagem.ATENCAO
        );
      }
      else {
        this.service.alterarSenha(this.formAlterarSenha.value.senha).subscribe({
          next: obj => {
            this.exibirMensagem.showMessage(
              "Senha alterada com sucesso",
              "Alterar senha",
              DecoracaoMensagem.SUCESSO
            );
          },
          error: objetoErro => {
            this.exibirMensagem.showMessage(
              `${objetoErro.error.detail}`,
              "Erro no processamento",
              DecoracaoMensagem.ERRO
            );
          }
      });
      }
    }
    else {
      this.exibirMensagem.showMessage(
        "Preencha todos os dados obrigatórios antes de gravar os dados",
        "Dados obrigatórios",
        DecoracaoMensagem.ATENCAO
      );
    }
  }
}
