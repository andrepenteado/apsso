import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { UsuarioService } from "../../../../services/usuario.service";
import { DecoracaoMensagem, ExibirMensagemService } from "../../../../libs/core/services/exibir-mensagem.service"
import { UserLogin } from "../../../../libs/core/dtos/user-login"
import { AuthService } from "../../../../services/auth.service"
import { UploadWidgetConfig, UploadWidgetResult } from '@bytescale/upload-widget';

@Component({
  selector: 'app-meus-dados',
  templateUrl: './meus-dados.component.html',
  styles: [
  ]
})
export class MeusDadosComponent implements OnInit {

  formEnviado = false;

  senha = new FormControl(null, Validators.required);
  repitaSenha = new FormControl(null, Validators.required);

  userLogin: UserLogin;

  options: UploadWidgetConfig = {
    apiKey: 'public_12a1yuu3cRCh66K6TZPqK1ePAFGG',
    multi: false,
    mimeTypes: ["image/*"]
  };
  onComplete = (files: UploadWidgetResult[]) => {
    this.toDataURL(files[0]?.fileUrl)
      .then(dataUrl => {
        this.uploadedFile = dataUrl;
      })
  };
  uploadedFile = undefined;

  toDataURL = url => fetch(url)
    .then(response => response.blob())
    .then(blob => new Promise((resolve, reject) => {
      const reader = new FileReader()
      reader.onloadend = () => resolve(reader.result)
      reader.onerror = reject
      reader.readAsDataURL(blob)
  }));

  constructor(
    private service: UsuarioService,
    private authService: AuthService,
    private exibirMensagem: ExibirMensagemService
  ) { }

  form = new FormGroup({
    senha: this.senha,
    repitaSenha: this.repitaSenha
  });

  async ngOnInit() {
    this.userLogin = await this.authService.usuarioLogado();
  }

  gravar(): void {
    this.formEnviado = true;
    if (this.form.valid) {
      if (this.form.value.senha != this.form.value.repitaSenha) {
        this.exibirMensagem.showMessage(
          "Digite senhas iguais em ambos os campos",
          "Senha não confere",
          DecoracaoMensagem.ATENCAO
        );
      }
      else {
        this.service.alterarSenha(this.form.value.senha).subscribe({
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
