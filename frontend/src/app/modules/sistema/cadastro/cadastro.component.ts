import {Component, OnInit, ViewChild} from '@angular/core';
import {DecoracaoMensagem, ExibeMensagemComponent} from "../../core/components/exibe-mensagem.component";
import {Sistema} from "../../../models/sistema";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {SistemaService} from "../../../services/sistema.service";
import {PerfilSistemaService} from "../../../services/perfil-sistema.service";
import {PerfilSistema} from "../../../models/perfil-sistema";
import Swal from "sweetalert2";

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
  formEnviado: boolean = false;
  formPerfilEnviado: boolean = false;
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
  form = new FormGroup({
    id: this.id,
    descricao: this.descricao,
    urlEntrada: this.urlEntrada,
    clientId: this.clientId,
    clientSecret: this.clientSecret
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
      private perfilSistemaService: PerfilSistemaService
  ) { }

  ngOnInit(): void {
    this.activedRoute.params.subscribe(params => {
      const id: string = params.id
      if (id) {
        this.pesquisar(id);
      }
    });
    this.aguardar = false;
  }

  pesquisar(id: string): void {
    this.sistemaService.buscar(id).subscribe(sistema => {
      this.sistema = sistema;
      this.dataCadastro = new Date(sistema.dataCadastro);
      this.dataUltimaModificacao = new Date(sistema.dataUltimaModificacao)
      this.form.patchValue(sistema);
      this.form.controls['clientSecret'].setValue('');
      this.perfilSistemaService.listarPorSistema(id).subscribe(
        perfis => this.perfis = perfis
      );
    });
  }

  habilitarClientSecret(): void {
    this.form.get('clientSecret').enable();
  }

  gravar(): void {
    this.formEnviado = true;
    if (this.form.valid) {
      this.sistemaService.gravar(this.form.value).subscribe({
        next: sistema => {
          this.sistema = sistema;
          this.form.reset();
          this.form.patchValue(sistema);
          this.form.controls['clientSecret'].setValue('');
          this.form.controls['clientSecret'].disable();
          this.exibeMensagem.show(
              `Dados do sistema ${sistema.id} gravados com sucesso`,
              DecoracaoMensagem.SUCESSO,
              'Gravar Sistema'
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
    else {
      this.exibeMensagem.show(
          'Preencha todos os dados obrigatórios antes de gravar os dados',
          DecoracaoMensagem.PRIMARIO,
          'Dados Obrigatórios'
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
          this.exibeMensagem.show(
              'Perfil incluído com sucesso',
              DecoracaoMensagem.SUCESSO,
              'Gravar Perfil'
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
    else {
      this.exibeMensagem.show(
          'Preencha todos os dados obrigatórios antes de gravar os dados',
          DecoracaoMensagem.PRIMARIO,
          'Dados Obrigatórios'
      );
    }
  }

  excluirPerfil(perfil: PerfilSistema): void {
    Swal.fire({
      title: 'Excluir?',
      text: `Confirma a exclusão do perfil ${perfil.descricao}`,
      icon: 'question',
      showCloseButton: true,
      showCancelButton: true,
      confirmButtonText: '<i class=\'fa fa-trash\'></i> Sim, Excluir',
      cancelButtonText: 'Cancelar'
    }).then((resposta) => {
      if (resposta.value) {
        this.perfilSistemaService.excluir(perfil.id).subscribe({
          next: () => this.pesquisar(perfil.sistema.id),
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
