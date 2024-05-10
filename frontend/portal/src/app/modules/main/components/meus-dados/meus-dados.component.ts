import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from "@angular/forms";
import { UsuarioService } from "../../../../services/usuario.service";
import { lastValueFrom, Observable } from "rxjs"
import { DecoracaoMensagem, ExibirMensagemService, LoginService, MenuComponent, Upload, UploadService, UserLogin } from "@andrepenteado/ngx-apcore"
import { clientId } from "../../../../etc/oauth2";

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
  descricaoPerfil: string = "";

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
    if (this.userLogin.uuidFoto) {
      this.uploadService.buscar(this.userLogin.uuidFoto).subscribe(upload => {
        this.foto = upload
      });
    }
    this.descricaoPerfis();
  }

  descricaoPerfis(): void {
    let result: string = "";
    for (const [ nome, descricao ] of Object.entries(this.userLogin.perfis)) {
      if (nome.startsWith(`ROLE_${clientId}_`)) {
        result = result + descricao + ",";
      }
    }
    this.descricaoPerfil = result.substring(0, result.length - 1);
  }

  atualizarFoto(event: any): void {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onloadend = () => {
      this.foto.base64 = reader.result as string;
      this.foto.nome = file.name;
      this.foto.descricao = "Foto usuário " + this.userLogin.nome;
      this.foto.tipo = file.type;
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
        MenuComponent.upload = upload;
        this.exibirMensagem.showMessage(
          "Foto atualizada com sucesso",
          "Atualizar Foto",
          DecoracaoMensagem.SUCESSO
        );
      }
    });
  }

}
