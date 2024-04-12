import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { SistemaService } from '../../../services/sistema.service';
import { PerfilSistemaService } from '../../../services/perfil-sistema.service';
import { Sistema } from "../../../model/entities/sistema"
import { PerfilSistema } from "../../../model/entities/perfil-sistema"
import { DecoracaoMensagem, ExibirMensagemService } from "../../../libs/core/services/exibir-mensagem.service"

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
  dataUltimaModificacao: Date = new Date();

  // Campos do formulário
  id = new FormControl(null, Validators.required);
  descricao = new FormControl(null, Validators.required);
  urlEntrada = new FormControl(null);
  clientId = new FormControl(null);
  clientSecret = new FormControl({value: '', disabled: true});
  iconeBase64 = new FormControl(null);
  form = new FormGroup({
    id: this.id,
    descricao: this.descricao,
    urlEntrada: this.urlEntrada,
    clientId: this.clientId,
    clientSecret: this.clientSecret,
    iconeBase64: this.iconeBase64
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
      private exibirMensagem: ExibirMensagemService
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
      this.dataUltimaModificacao = new Date(sistema.dataUltimaAtualizacao);
      this.form.patchValue(sistema);
      this.form.controls.clientSecret.setValue('');
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
      this.form.controls.iconeBase64.setValue(reader.result as string);
    };

    if (file)
      reader.readAsDataURL(file);
  }

  gravar(): void {
    this.formEnviado = true;
    if (this.form.valid) {
      console.log(this.form.value);
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
            next: () => this.pesquisar(perfil.sistema.id),
            error: objetoErro => {
              this.exibirMensagem.showMessage(
                `${objetoErro.error.detail}`,
                "Erro no processamento",
                DecoracaoMensagem.ERRO
              );
            }
          });
        }
      });
  }

}
