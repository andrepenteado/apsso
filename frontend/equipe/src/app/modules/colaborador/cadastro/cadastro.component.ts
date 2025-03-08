import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { EmpresaService } from "../../../services/empresa.service";
import { Observable } from "rxjs";
import { ColaboradorService } from "../../../services/colaborador.service";
import { UnidadeAdministrativaService } from "../../../services/unidade-administrativa.service";
import { CargoService } from "../../../services/cargo.service";
import { Colaborador } from "../../../domain/entities/colaborador";
import { Empresa } from "../../../domain/entities/empresa";
import { UnidadeAdministrativa } from "../../../domain/entities/unidade-administrativa";
import { Cargo } from "../../../domain/entities/cargo";
import { DecoracaoMensagem, ExibirMensagemService, ViaCepService } from "@andre.penteado/ngx-apcore";

@Component({
  selector: 'admin-colaborador-cadastro',
  templateUrl: './cadastro.component.html',
  styles: ``
})
export class CadastroComponent implements OnInit {

  incluir = true;
  formEnviado = false;
  colaborador = new Colaborador();

  listaEmpresas: Empresa[] = []
  listaCargos: Cargo[] = [];

  id = new FormControl(null);
  dataCadastro = new FormControl(null);
  usuarioCadastro = new FormControl(null);
  dataUltimaAtualizacao = new FormControl(null);
  usuarioUltimaAtualizacao = new FormControl(null);
  nome = new FormControl(null, Validators.required);
  empresa = new FormControl(null);
  cargo = new FormControl(null);
  cpf = new FormControl(null, Validators.required);
  telefone = new FormControl(null, Validators.required);
  email = new FormControl(null);
  cep = new FormControl(null);
  logradouro = new FormControl(null);
  complemento = new FormControl(null);
  numeroLogradouro = new FormControl(null);
  bairro = new FormControl(null);
  cidade = new FormControl(null);
  estado = new FormControl(null);
  form = new FormGroup({
    id: this.id,
    dataCadastro: this.dataCadastro,
    usuarioCadastro: this.usuarioCadastro,
    dataUltimaAtualizacao: this.dataUltimaAtualizacao,
    usuarioUltimaAtualizacao: this.usuarioUltimaAtualizacao,
    nome: this.nome,
    empresa: this.empresa,
    cargo: this.cargo,
    cpf: this.cpf,
    telefone: this.telefone,
    email: this.email,
    cep: this.cep,
    logradouro: this.logradouro,
    complemento: this.complemento,
    numeroLogradouro: this.numeroLogradouro,
    bairro: this.bairro,
    cidade: this.cidade,
    estado: this.estado
  });

  constructor(
    private activedRoute: ActivatedRoute,
    private colaboradorService: ColaboradorService,
    protected empresaService: EmpresaService,
    protected unidadeAdministrativaService: UnidadeAdministrativaService,
    protected cargoService: CargoService,
    private viaCepService: ViaCepService,
    private exibirMensagem: ExibirMensagemService
  ) { }

  ngOnInit() {
    this.pesquisarEmpresas();
    this.form.get("empresa").valueChanges.subscribe(empresa => {
      this.pesquisarCargosPorEmpresa(empresa.id);
    });
    this.activedRoute.params.subscribe(params => {
      const id: number = params.id;
      if (id) {
        this.incluir = false;
        this.pesquisar(id);
      }
    });
  }

  pesquisar(id: number) {
    this.colaboradorService.buscar(id).subscribe(colaborador => {
      this.colaborador = colaborador;
      this.form.patchValue(colaborador);
      this.form.get("empresa").setValue(this.colaborador.cargo.empresa);
      this.form.get("cargo").setValue(this.colaborador.cargo);
    });
  }

  pesquisarEmpresas(): void {
    this.empresaService.listar().subscribe({
      next: listaEmpresas => {
        this.listaEmpresas = listaEmpresas;
      }
    });
  }

  pesquisarCargosPorEmpresa(idEmpresa: number): void {
    this.cargoService.listarPorEmpresa(idEmpresa).subscribe({
      next: listaCargos => {
        this.listaCargos = listaCargos;
      }
    });
  }

  gravar() {
    this.formEnviado = true;

    if (this.form.valid) {
      var colaboradorAtualizada: Observable<Colaborador>;

      if (this.incluir)
        colaboradorAtualizada = this.colaboradorService.incluir(this.form.value);
      else
        colaboradorAtualizada = this.colaboradorService.alterar(this.form.value);

      colaboradorAtualizada.subscribe({
        next: colaborador => {
          this.colaborador = colaborador;
          this.incluir = false;
          this.form.reset();
          this.form.patchValue(colaborador);
          this.form.get("empresa").setValue(colaborador.cargo.empresa);
          this.exibirMensagem.showMessage(
            `Dados do colaborador ${colaborador.nome} gravados com sucesso`,
            "Gravar colaborador",
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

