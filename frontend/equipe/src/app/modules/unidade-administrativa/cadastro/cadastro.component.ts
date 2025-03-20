import { Component, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { last, Observable } from "rxjs";
import { UnidadeAdministrativaService } from "../../../services/unidade-administrativa.service";
import { EmpresaService } from "../../../services/empresa.service";
import { UnidadeAdministrativa } from "../../../domain/entities/unidade-administrativa";
import { Empresa } from "../../../domain/entities/empresa";
import { TipoUnidadeAdministrativa } from "../../../domain/enums/tipo-unidade-administrativa";
import { DecoracaoMensagem, ExibirMensagemService } from "@andre.penteado/ngx-apcore";
import { Colaborador } from "../../../domain/entities/colaborador";
import { ColaboradorService } from "../../../services/colaborador.service";
import { NgSelectComponent } from "@ng-select/ng-select";

@Component({
  selector: 'apadmin-unidade-administrativa-cadastro',
  templateUrl: './cadastro.component.html',
  styles: ``
})
export class CadastroComponent implements OnInit {

  @ViewChild("comboColaboradores")
  comboColaboradores: NgSelectComponent;

  incluir = true;
  formEnviado = false;
  unidadeAdministrativa = new UnidadeAdministrativa();
  colaboradorSelecionado: Colaborador;
  listaEmpresas: Empresa[] = [];
  listaUnidadesAdministrativas: UnidadeAdministrativa[] = [];
  listaColaboradores: Colaborador[] = [];

  tipos = Object.keys(TipoUnidadeAdministrativa);
  enumTipo: { [key: string]: TipoUnidadeAdministrativa } = TipoUnidadeAdministrativa;

  id = new FormControl(null);
  nome = new FormControl(null, Validators.required);
  tipo = new FormControl(null, Validators.required);
  empresa = new FormControl(this.listaEmpresas[0], Validators.required);
  unidadeAdministrativaSuperior = new FormControl(null);
  colaboradores = new FormArray<FormControl<Colaborador>>([]);
  form = new FormGroup({
    id: this.id,
    nome: this.nome,
    tipo: this.tipo,
    empresa: this.empresa,
    unidadeAdministrativaSuperior: this.unidadeAdministrativaSuperior,
    colaboradores: this.colaboradores
  });

  constructor(
    private activedRoute: ActivatedRoute,
    protected unidadeAdministrativaService: UnidadeAdministrativaService,
    protected empresaService: EmpresaService,
    private colaboradorService: ColaboradorService,
    private exibirMensagem: ExibirMensagemService
  ) { }

  ngOnInit() {
    this.pesquisarEmpresas();
    this.pesquisarColaboradores();
    this.form.get("empresa").valueChanges.subscribe(empresa => {
      this.pesquisarUnidadesAdministrativasPorEmpresa(empresa.id);
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
    this.unidadeAdministrativaService.buscar(id).subscribe(unidadeAdministrativa => {
      this.unidadeAdministrativa = unidadeAdministrativa;
      this.form.patchValue(unidadeAdministrativa);
      this.form.get("empresa").setValue(unidadeAdministrativa.empresa);
      this.form.get("unidadeAdministrativaSuperior").setValue(unidadeAdministrativa.unidadeAdministrativaSuperior);
      this.form.setControl("colaboradores", new FormArray(unidadeAdministrativa.colaboradores.map(c => new FormControl(c))));
    });
  }

  pesquisarUnidadesAdministrativasPorEmpresa(idEmpresa: number): void {
    this.unidadeAdministrativaService.pesquisarPorEmpresa(idEmpresa).subscribe({
      next: listaUnidadesAdministrativas => {
        this.listaUnidadesAdministrativas = listaUnidadesAdministrativas;
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

  pesquisarColaboradores(): void {
    this.colaboradorService.listar().subscribe({
      next: listaColaboradores => {
        this.listaColaboradores = listaColaboradores;
      }
    });
  }

  filtrarColaboradorPorNome(filtro: string, colaborador: Colaborador): boolean {
    return colaborador.nome.toLowerCase().indexOf(filtro.toLowerCase()) >= 0 ||
      colaborador.cargo.empresa.nomeFantasia.toLowerCase().indexOf(filtro.toLowerCase()) >= 0;
  }

  selecionarColaborador(colaborador: Colaborador): void {
    this.colaboradorSelecionado = colaborador;
  }

  get colaboradoresArray() {
    return this.form.get("colaboradores") as FormArray<FormControl<Colaborador>>;
  }

  vincularColaborador() {
    if (!this.colaboradorSelecionado) {
      this.exibirMensagem.showMessage(
        "Colaborador não selecionado",
        "Atenção",
        DecoracaoMensagem.ATENCAO
      );
      return;
    }

    this.colaboradoresArray.push(new FormControl(this.colaboradorSelecionado));
    this.gravar();
    this.colaboradorSelecionado = null;
    this.comboColaboradores.clearModel();
  }

  desvincularColaborador(index: number) {
    if (this.colaboradoresArray && index >= 0 && index < this.colaboradoresArray.length) {
      this.colaboradoresArray.removeAt(index);
      this.gravar();
    }
  }

  gravar() {
    this.formEnviado = true;

    if (this.form.valid) {
      var unidadeAdministrativaAtualizada: Observable<UnidadeAdministrativa>;

      if (this.incluir)
        unidadeAdministrativaAtualizada = this.unidadeAdministrativaService.incluir(this.form.value);
      else
        unidadeAdministrativaAtualizada = this.unidadeAdministrativaService.alterar(this.form.value);

      unidadeAdministrativaAtualizada.subscribe({
        next: unidadeAdministrativa => {
          this.unidadeAdministrativa = unidadeAdministrativa;
          this.incluir = false;
          this.form.reset();
          this.form.patchValue(unidadeAdministrativa);
          this.exibirMensagem.showMessage(
            `Dados da unidade administrativa ${unidadeAdministrativa.nome} gravados com sucesso`,
            "Gravar unidade administrativa",
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

  protected readonly last = last;
}
