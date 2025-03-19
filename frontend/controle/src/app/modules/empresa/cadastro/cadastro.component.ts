import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { EmpresaService } from "../../../services/empresa.service";
import { lastValueFrom, Observable } from "rxjs"
import { Empresa } from "../../../domain/entities/empresa";
import {
  DecoracaoMensagem,
  ExibirMensagemService,
  Upload,
  UploadService,
  ViaCepService
} from "@andre.penteado/ngx-apcore";

@Component({
  selector: 'admin-empresa-cadastro',
  templateUrl: './cadastro.component.html',
  styles: [
  ]
})
export class CadastroComponent implements OnInit {

  incluir = true;
  formEnviado = false;
  empresa = new Empresa();
  listaEmpresas: Empresa[] = [];
  logotipoUpload: Upload = new Upload();

  id = new FormControl(null);
  dataCadastro = new FormControl(null);
  usuarioCadastro = new FormControl(null);
  dataUltimaAtualizacao = new FormControl(null);
  usuarioUltimaAtualizacao = new FormControl(null);
  nomeFantasia = new FormControl(null, Validators.required);
  razaoSocial = new FormControl(null, Validators.required);
  cnpj = new FormControl(null, Validators.required);
  telefone = new FormControl(null, Validators.required);
  email = new FormControl(null);
  cep = new FormControl(null);
  logradouro = new FormControl(null);
  complemento = new FormControl(null);
  numeroLogradouro = new FormControl(null);
  bairro = new FormControl(null);
  cidade = new FormControl(null);
  estado = new FormControl(null);
  urlLogin = new FormControl(null);
  logotipo = new FormControl(null);
  matriz = new FormControl(null);
  form = new FormGroup({
    id: this.id,
    dataCadastro: this.dataCadastro,
    usuarioCadastro: this.usuarioCadastro,
    dataUltimaAtualizacao: this.dataUltimaAtualizacao,
    usuarioUltimaAtualizacao: this.usuarioUltimaAtualizacao,
    nomeFantasia: this.nomeFantasia,
    razaoSocial: this.razaoSocial,
    cnpj: this.cnpj,
    telefone: this.telefone,
    email: this.email,
    cep: this.cep,
    logradouro: this.logradouro,
    complemento: this.complemento,
    numeroLogradouro: this.numeroLogradouro,
    bairro: this.bairro,
    cidade: this.cidade,
    estado: this.estado,
    urlLogin: this.urlLogin,
    logotipo: this.logotipo,
    matriz: this.matriz
  });

  constructor(
    private activedRoute: ActivatedRoute,
    protected empresaService: EmpresaService,
    private uploadService: UploadService,
    private viaCepService: ViaCepService,
    private exibirMensagem: ExibirMensagemService
  ) { }

  ngOnInit() {
    this.pesquisarEmpresas();
    this.activedRoute.params.subscribe(params => {
      const id: number = params.id;
      if (id) {
        this.incluir = false;
        this.pesquisar(id);
      }
    });
  }

  pesquisar(id: number) {
    this.empresaService.buscar(id).subscribe(empresa => {
      this.empresa = empresa;
      this.form.patchValue(empresa);
      this.form.get("matriz").setValue(empresa.matriz);
      if (empresa.logotipo) {
        this.uploadService.buscar(this.empresa.logotipo).subscribe(upload => {
          this.logotipoUpload = upload;
        });
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

  atualizarLogotipo(event: any): void {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onloadend = () => {
      this.logotipoUpload.base64 = reader.result as string;
      this.logotipoUpload.nome = file.name;
      this.logotipoUpload.descricao = "Logotipo da empresa " + this.empresa.nomeFantasia;
      this.logotipoUpload.tipoMime = file.type;
      this.logotipoUpload.tamanho = file.size;
    };

    if (file)
      reader.readAsDataURL(file);
  }

  async gravar() {
    this.formEnviado = true;

    if (this.form.valid) {
      if (this.logotipoUpload.nome) {
        let upload$: Observable<Upload>;

        if (this.empresa.logotipo)
          upload$ = this.uploadService.alterar(this.logotipoUpload)
        else
          upload$ = this.uploadService.incluir(this.logotipoUpload);

        let upload = await lastValueFrom(upload$);
        this.form.controls.logotipo.setValue(upload.uuid);
      }

      var empresaAtualizada: Observable<Empresa>;

      if (this.incluir)
        empresaAtualizada = this.empresaService.incluir(this.form.value);
      else
        empresaAtualizada = this.empresaService.alterar(this.form.value);

      empresaAtualizada.subscribe({
        next: empresa => {
          this.empresa = empresa;
          this.incluir = false;
          this.form.reset();
          this.form.patchValue(empresa);
          this.exibirMensagem.showMessage(
            `Dados da empresa ${empresa.nomeFantasia} gravados com sucesso`,
            "Gravar empresa",
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

  consultaCep = (cep: string) => {
    cep = cep.replace('-', '');
    this.logradouro.setValue('');
    this.bairro.setValue('');
    this.cidade.setValue('');
    this.estado.setValue('');
    this.viaCepService.consultarCep(cep).subscribe({
      next: endereco => {
        if (endereco.erro) {
          this.exibirMensagem.showMessage(
            'CEP não encontrado ou incorreto',
            'Pesquisar CEP',
            DecoracaoMensagem.INFO
          );
        }
        else {
          this.logradouro.setValue(endereco.logradouro);
          this.bairro.setValue(endereco.bairro);
          this.cidade.setValue(endereco.localidade);
          this.estado.setValue(endereco.uf);
        }
      },
      error: () => {
        this.exibirMensagem.showMessage(
          'Não foi possível consultar o CEP',
          'Erro de processamento',
          DecoracaoMensagem.ERRO,
        );
      }
    });
  }

}
