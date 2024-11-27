import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { Observable } from "rxjs";
import { CargoService } from "../../../services/cargo.service";
import { Cargo } from "../../../domain/entities/cargo";
import { Empresa } from "../../../domain/entities/empresa";
import { DecoracaoMensagem, ExibirMensagemService } from "@andre.penteado/ngx-apcore";
import { EmpresaService } from "../../../services/empresa.service";

@Component({
  selector: 'admin-cargo-cadastro',
  templateUrl: './cadastro.component.html',
  styles: ``
})
export class CadastroComponent implements OnInit {

  incluir = true;
  formEnviado = false;
  cargo = new Cargo();
  listaEmpresas: Empresa[] = [];

  id = new FormControl(null);
  nome = new FormControl(null, Validators.required);
  empresa = new FormControl(this.listaEmpresas[0], Validators.required);
  form = new FormGroup({
    id: this.id,
    nome: this.nome,
    empresa: this.empresa
  });

  constructor(
    private activedRoute: ActivatedRoute,
    private cargoService: CargoService,
    protected empresaService: EmpresaService,
    private exibirMensagem: ExibirMensagemService
  ) { }

  ngOnInit() {
    this.activedRoute.params.subscribe(params => {
      const id: number = params.id;
      if (id) {
        this.incluir = false;
        this.pesquisar(id);
      }
    });
    this.pesquisarEmpresas();
    this.form.get("empresa").setValue(this.cargo.empresa);
  }

  pesquisar(id: number) {
    this.cargoService.buscar(id).subscribe(cargo => {
      this.cargo = cargo;
      this.form.patchValue(cargo);
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
      var cargoAtualizado: Observable<Cargo>;

      if (this.incluir)
        cargoAtualizado = this.cargoService.incluir(this.form.value);
      else
        cargoAtualizado = this.cargoService.alterar(this.form.value);

      cargoAtualizado.subscribe({
        next: cargo => {
          this.cargo = cargo;
          this.incluir = false;
          this.form.reset();
          this.form.patchValue(cargo);
          this.exibirMensagem.showMessage(
            `Dados do cargo ${cargo.nome} gravados com sucesso`,
            "Gravar cargo",
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

}
