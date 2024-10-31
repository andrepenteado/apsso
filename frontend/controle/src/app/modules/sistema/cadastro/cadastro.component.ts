import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { SistemaService } from '../../../services/sistema.service';
import { PerfilSistemaService } from '../../../services/perfil-sistema.service';
import { Sistema } from "../../../domain/entities/sistema"
import { PerfilSistema } from "../../../domain/entities/perfil-sistema"
import { lastValueFrom, Observable } from "rxjs"
import { DecoracaoMensagem, ExibirMensagemService, Upload, UploadService } from "@andre.penteado/ngx-apcore"
import { AmbienteSistema } from "../../../domain/entities/ambiente-sistema";
import { AmbienteSistemaService } from "../../../services/ambiente-sistema.service";
import { Empresa } from "../../../domain/entities/empresa";
import { EmpresaService } from "../../../services/empresa.service";

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
  formAmbienteEnviado = false;
  sistema = new Sistema();
  perfis: PerfilSistema[] = [];
  ambientes: AmbienteSistema[] = [];
  listaEmpresas: Empresa[] = [];
  dataCadastro: Date = new Date();
  dataUltimaAtualizacao: Date = new Date();
  iconeUpload: Upload = new Upload();

  // Campos do formulário
  id = new FormControl(null);
  codigo = new FormControl(null, Validators.required);
  nome = new FormControl(null, Validators.required);
  descricao = new FormControl(null);
  icone = new FormControl(null);
  empresa = new FormControl(null, Validators.required);
  form = new FormGroup({
    id: this.id,
    codigo: this.codigo,
    nome: this.nome,
    descricao: this.descricao,
    icone: this.icone,
    empresa: this.empresa
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

  idAmbiente = new FormControl(null);
  sistemaAmbiente = new FormControl(null);
  descricaoAmbiente = new FormControl(null, Validators.required);
  urlEntrada = new FormControl(null, Validators.required);
  clientId = new FormControl(null);
  clientSecret = new FormControl(null);
  formAmbiente = new FormGroup({
    id: this.idAmbiente,
    sistema: this.sistemaAmbiente,
    descricao: this.descricaoAmbiente,
    urlEntrada: this.urlEntrada,
    clientId: this.clientId,
    clientSecret: this.clientSecret
  });

  constructor(
    private activedRoute: ActivatedRoute,
    private sistemaService: SistemaService,
    private perfilSistemaService: PerfilSistemaService,
    private ambienteSistemaService: AmbienteSistemaService,
    protected empresaService: EmpresaService,
    private exibirMensagem: ExibirMensagemService,
    private uploadService: UploadService
  ) { }

  ngOnInit(): void {
    this.pesquisarEmpresas();
    this.activedRoute.params.subscribe(params => {
      const id: string = params.id;
      if (id) {
        this.incluir = false;
        this.pesquisar(id);
      }
    });
  }

  pesquisarEmpresas(): void {
    this.empresaService.listar().subscribe({
      next: listaEmpresas => {
        this.listaEmpresas = listaEmpresas;
      }
    });
  }

  pesquisar(id: string): void {
    this.sistemaService.buscar(id).subscribe(sistema => {
      this.sistema = sistema;
      this.dataCadastro = new Date(sistema.dataCadastro);
      this.dataUltimaAtualizacao = new Date(sistema.dataUltimaAtualizacao);
      this.form.patchValue(sistema);
      this.form.get("empresa").setValue(this.sistema.empresa);
      if (sistema.icone) {
        this.uploadService.buscar(this.sistema.icone).subscribe(upload => {
          this.iconeUpload = upload;
        });
      }
      this.perfilSistemaService.listarPorSistema(id).subscribe(
        perfis => this.perfis = perfis
      );
      this.ambienteSistemaService.listarPorSistema(id).subscribe(
        ambientes => this.ambientes = ambientes
      );
    });
  }

  atualizarIcone(event: any): void {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onloadend = () => {
      this.iconeUpload.base64 = reader.result as string;
      this.iconeUpload.nome = file.name;
      this.iconeUpload.descricao = "Ícone do sistema " + this.sistema.descricao;
      this.iconeUpload.tipoMime = file.type;
      this.iconeUpload.tamanho = file.size;
    };

    if (file)
      reader.readAsDataURL(file);
  }

  async gravar() {
    this.formEnviado = true;

    if (this.form.valid) {
      if (this.iconeUpload.nome) {
        let upload$: Observable<Upload>;

        if (this.sistema.icone)
          upload$ = this.uploadService.alterar(this.iconeUpload)
        else
          upload$ = this.uploadService.incluir(this.iconeUpload);

        let upload = await lastValueFrom(upload$);
        this.form.controls.icone.setValue(upload.uuid);
      }

      this.sistemaService.gravar(this.form.value).subscribe({
        next: sistema => {
          this.sistema = sistema;
          this.incluir = false;
          this.form.reset();
          this.form.patchValue(sistema);
          this.exibirMensagem.showMessage(
            `Dados do sistema ${sistema.nome} gravados com sucesso`,
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
      this.formPerfil.controls.authority.setValue('ROLE_' + this.sistema.codigo + '_' + this.formPerfil.controls.authority.value.toUpperCase());
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

  gravarAmbiente(): void {
    this.formAmbienteEnviado = true;
    if (this.formAmbiente.valid) {
      this.formAmbiente.controls.sistema.setValue(this.sistema);
      this.ambienteSistemaService.incluir(this.formAmbiente.value).subscribe({
        next: ambiente => {
          this.ambientes.unshift(ambiente);
          this.formAmbiente.reset();
          this.formAmbienteEnviado = false;
          this.exibirMensagem.showMessage(
            "Ambiente de sistema incluído com sucesso",
            "Gravar ambiente",
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

  excluirAmbiente(ambiente: AmbienteSistema): void {
    this.exibirMensagem
      .showConfirm(`Confirma a exclusão do ambiente ${ambiente.descricao}`, "Excluir?")
      .then((resposta) => {
        if (resposta.value) {
          this.ambienteSistemaService.excluir(ambiente.id).subscribe({
            next: () => this.pesquisar(ambiente.sistema.id)
          });
        }
      });
  }

}
