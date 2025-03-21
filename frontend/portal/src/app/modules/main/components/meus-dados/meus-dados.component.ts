import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from "@angular/forms";
import { UsuarioService } from "../../../../services/usuario.service";
import { lastValueFrom, Observable } from "rxjs"
import {
  DecoracaoMensagem,
  ExibirMensagemService,
  LoginService,
  Upload,
  UploadService,
  UserLogin
} from "@andre.penteado/ngx-apcore"

@Component({
  selector: 'app-meus-dados',
  templateUrl: './meus-dados.component.html',
  styles: [
  ]
})
export class MeusDadosComponent implements OnInit {

  formEnviado = false;

  senha = new FormControl(null);
  repitaSenha = new FormControl(null);

  userLogin: UserLogin;
  foto: Upload = new Upload();

  constructor(
    private service: UsuarioService,
    private loginService: LoginService,
    private uploadService: UploadService,
    private exibirMensagem: ExibirMensagemService
  ) { }

  form = new FormGroup({
    senha: this.senha,
    repitaSenha: this.repitaSenha
  });

  async ngOnInit() {
    this.userLogin = await this.loginService.getUserLogin();
    this.uploadService.buscar(this.userLogin.uuidFoto).subscribe({
      next: uploadFoto => this.foto = uploadFoto
    });
  }

  descricaoPerfis(): string {
    return this.loginService.getDescricaoPerfis();
  }

  atualizarFoto(event: any): void {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onloadend = () => {
      this.foto.base64 = reader.result as string;
      this.foto.nome = file.name;
      this.foto.descricao = "Foto usuário " + this.userLogin.nome;
      this.foto.tipoMime = file.type;
      this.foto.tamanho = file.size;
    };

    if (file)
      reader.readAsDataURL(file);
  }

  alterarSenha(): void {
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

  async gravarFoto() {
    let upload$: Observable<Upload>;
    if (this.userLogin.uuidFoto)
      upload$ = this.uploadService.alterar(this.foto)
    else
      upload$ = this.uploadService.incluir(this.foto);
    let upload = await lastValueFrom(upload$);

    this.service.atualizarFoto(upload.uuid).subscribe({
      next: obj => {
        this.userLogin.uuidFoto = upload.uuid;
        this.exibirMensagem.showMessage(
          "Foto atualizada com sucesso",
          "Atualizar Foto",
          DecoracaoMensagem.SUCESSO
        );
      }
    });
  }

}
