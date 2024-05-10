import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { SistemaService } from '../../../services/sistema.service';
import { PerfilSistemaService } from '../../../services/perfil-sistema.service';
import { Sistema } from "../../../model/entities/sistema"
import { PerfilSistema } from "../../../model/entities/perfil-sistema"
import { lastValueFrom, Observable } from "rxjs"
import { DecoracaoMensagem, ExibirMensagemService, Upload, UploadService } from "@andrepenteado/ngx-apcore"

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styles: [
  ]
})
export class CadastroComponent implements OnInit {

  incluir = true;
  formEnviado = false;
  formPerfilEnviado = false;
  sistema: Sistema;
  perfis: PerfilSistema[] = [];
  dataCadastro: Date = new Date();
  dataUltimaAtualizacao: Date = new Date();
  iconeUpload: Upload = new Upload();

  // Campos do formulário
  id = new FormControl(null, Validators.required);
  descricao = new FormControl(null, Validators.required);
  urlEntrada = new FormControl(null);
  clientId = new FormControl(null);
  clientSecret = new FormControl({value: '', disabled: true});
  icone = new FormControl(null);
  form = new FormGroup({
    id: this.id,
    descricao: this.descricao,
    urlEntrada: this.urlEntrada,
    clientId: this.clientId,
    clientSecret: this.clientSecret,
    icone: this.icone,
  });

  idPerfil = new FormControl(null);
  sistemaPerfil = new FormControl(null);
  nomePerfil = new FormControl(null, Validators.required);
  descricaoPerfil = new FormControl(null, Validators.required);
  formPerfil = new FormGroup({
    id: this.idPerfil,
    sistema: this.sistemaPerfil,
    authority: this.nomePerfil,
    descricao: this.descricaoPerfil
  });

  constructor(
    private activedRoute: ActivatedRoute,
    private sistemaService: SistemaService,
    private perfilSistemaService: PerfilSistemaService,
    private exibirMensagem: ExibirMensagemService,
    private uploadService: UploadService
  ) { }

  ngOnInit(): void {
    this.activedRoute.params.subscribe(params => {
      const id: string = params.id;
      if (id) {
        this.pesquisar(id);
        this.incluir = false;
      }
    });
  }

  pesquisar(id: string): void {
    this.sistemaService.buscar(id).subscribe(sistema => {
      this.sistema = sistema;
      this.dataCadastro = new Date(sistema.dataCadastro);
      this.dataUltimaAtualizacao = new Date(sistema.dataUltimaAtualizacao);
      this.form.patchValue(sistema);
      this.form.controls.clientSecret.setValue('');
      if (sistema.icone) {
        this.uploadService.buscar(this.sistema.icone).subscribe(upload => {
          this.iconeUpload = upload;
        });
      }
      this.perfilSistemaService.listarPorSistema(id).subscribe(
        perfis => this.perfis = perfis
      );
    });
  }

  habilitarClientSecret(): void {
    this.form.get('clientSecret').enable();
  }

  atualizarIcone(event: any): void {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onloadend = () => {
      this.iconeUpload.base64 = reader.result as string;
      this.iconeUpload.nome = file.name;
      this.iconeUpload.descricao = "Ícone do sistema " + this.sistema.descricao;
      this.iconeUpload.tipo = file.type;
    };

    if (file)
      reader.readAsDataURL(file);
  }

  async gravar() {
    this.formEnviado = true;
    if (this.form.valid) {
      let upload$: Observable<Upload>;
      if (this.sistema.icone)
        upload$ = this.uploadService.alterar(this.iconeUpload)
      else
        upload$ = this.uploadService.incluir(this.iconeUpload);
      let upload = await lastValueFrom(upload$);

      this.form.controls.icone.setValue(upload.uuid);
      this.sistemaService.gravar(this.form.value).subscribe({
        next: sistema => {
          this.sistema = sistema;
          this.form.reset();
          this.form.patchValue(sistema);
          this.form.controls.clientSecret.setValue('');
          this.form.controls.clientSecret.disable();
          this.exibirMensagem.showMessage(
            `Dados do sistema ${sistema.id} gravados com sucesso`,
            "Gravar sistema",
            DecoracaoMensagem.SUCESSO
          );
        }
      });
    }
    else {
      this.exibirMensagem.showMessage(
        "Preencha todos os dados obrigatórios antes de gravar os dados",
        "Dados obrigatórios",
        DecoracaoMensagem.ATENCAO
      );
    }
  }

  gravarPerfil(): void {
    this.formPerfilEnviado = true;
    if (this.formPerfil.valid) {
      this.formPerfil.controls.authority.setValue('ROLE_' + this.sistema.id + '_' + this.formPerfil.controls.authority.value.toUpperCase());
      this.formPerfil.controls.sistema.setValue(this.sistema);
      this.perfilSistemaService.incluir(this.formPerfil.value).subscribe({
        next: perfil => {
          this.perfis.unshift(perfil);
          this.formPerfil.reset();
          this.formPerfilEnviado = false;
          this.exibirMensagem.showMessage(
            "Perfil incluído com sucesso",
            "Gravar perfil",
            DecoracaoMensagem.SUCESSO
          );
        }
      });
    }
    else {
      this.exibirMensagem.showMessage(
        "Preencha todos os dados obrigatórios antes de gravar os dados",
        "Dados obrigatórios",
        DecoracaoMensagem.ATENCAO
      );
    }
  }

  excluirPerfil(perfil: PerfilSistema): void {
    this.exibirMensagem
      .showConfirm(`Confirma a exclusão do perfil ${perfil.descricao}`, "Excluir?")
      .then((resposta) => {
        if (resposta.value) {
          this.perfilSistemaService.excluir(perfil.authority).subscribe({
            next: () => this.pesquisar(perfil.sistema.id)
          });
        }
      });
  }

}
