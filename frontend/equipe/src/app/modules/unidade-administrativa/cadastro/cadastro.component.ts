import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { last, Observable } from "rxjs";
import { UnidadeAdministrativaService } from "../../../services/unidade-administrativa.service";
import { EmpresaService } from "../../../services/empresa.service";
import { UnidadeAdministrativa } from "../../../domain/entities/unidade-administrativa";
import { Empresa } from "../../../domain/entities/empresa";
import { TipoUnidadeAdministrativa } from "../../../domain/enums/tipo-unidade-administrativa";
import { DecoracaoMensagem, ExibirMensagemService } from "@andre.penteado/ngx-apcore";

@Component({
  selector: 'apadmin-unidade-administrativa-cadastro',
  templateUrl: './cadastro.component.html',
  styles: ``
})
export class CadastroComponent implements OnInit {

  incluir = true;
  formEnviado = false;
  unidadeAdministrativa = new UnidadeAdministrativa();
  listaEmpresas: Empresa[] = [];

  tipos = Object.keys(TipoUnidadeAdministrativa);
  enumTipo: { [key: string]: TipoUnidadeAdministrativa } = TipoUnidadeAdministrativa;

  id = new FormControl(null);
  nome = new FormControl(null, Validators.required);
  tipo = new FormControl(null, Validators.required);
  empresa = new FormControl(this.listaEmpresas[0], Validators.required);
  form = new FormGroup({
    id: this.id,
    nome: this.nome,
    tipo: this.tipo,
    empresa: this.empresa
  });

  constructor(
    private activedRoute: ActivatedRoute,
    private unidadeAdministrativaService: UnidadeAdministrativaService,
    protected empresaService: EmpresaService,
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
    this.unidadeAdministrativaService.buscar(id).subscribe(unidadeAdministrativa => {
      this.unidadeAdministrativa = unidadeAdministrativa;
      this.form.patchValue(unidadeAdministrativa);
      this.form.get("empresa").setValue(unidadeAdministrativa.empresa);
    });
  }

  pesquisarEmpresas(): void {
    this.empresaService.listar().subscribe({
      next: listaEmpresas => {
        this.listaEmpresas = listaEmpresas;
      }
    });
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
