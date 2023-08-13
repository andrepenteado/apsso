import {Component, OnInit, ViewChild} from '@angular/core';
import {DecoracaoMensagem, ExibeMensagemComponent} from "../../core/components/exibe-mensagem.component";
import {Usuario} from "../../../models/usuario";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {UsuarioService} from "../../../services/usuario.service";
import {PerfilSistema} from "../../../models/perfil-sistema";
import {PerfilSistemaService} from "../../../services/perfil-sistema.service";
import {finalize} from "rxjs";
import {PerfilUsuarioService} from "../../../services/perfil-usuario.service";
import {PerfilUsuario} from "../../../models/perfil-usuario";

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
  incluir: boolean = true;
  formEnviado: boolean = false;
  usuario: Usuario;
  listaPerfis: PerfilSistema[] = [];
  listaPerfisUsuario: PerfilUsuario[] = [];
  dataCadastro: Date = new Date();
  dataUltimaModificacao: Date = new Date();

  // Campos do formulário
  username = new FormControl(null, Validators.required);
  password = new FormControl({value: '', disabled: true});
  nome = new FormControl(null);

  formUsuario = new FormGroup({
    username: this.username,
    password: this.password,
    nome: this.nome
  });
  formPerfis = new FormGroup({
    perfis: new FormArray([])
  });

  arrayPerfis = this.formPerfis.get("perfis") as FormArray;

  constructor(
      private activedRoute: ActivatedRoute,
      private usuarioService: UsuarioService,
      private perfilSistemaService: PerfilSistemaService,
      private perfilUsuarioService: PerfilUsuarioService
  ) { }

  ngOnInit(): void {
    this.activedRoute.params.subscribe(params => {
      const username: string = params.username
      if (username) {
        this.pesquisar(username);
        this.incluir = false;
      }
    });
    this.pesquisarPerfis();
    this.aguardar = false;
  }

  pesquisar(username: string): void {
    this.usuarioService.buscar(username).subscribe(usuario => {
      this.usuario = usuario;
      this.dataCadastro = new Date(usuario.dataCadastro);
      this.dataUltimaModificacao = new Date(usuario.dataUltimaModificacao)
      this.formUsuario.patchValue(usuario);
      this.formUsuario.controls['password'].setValue('');
    });
  }

  pesquisarPerfis(): void {
    this.perfilSistemaService.listar()
      .pipe(finalize(() => {
        for (let i = 0; i < this.listaPerfis.length; i++) {
          let perfilForm = new FormControl(false);
          this.arrayPerfis.push(perfilForm);
        }
      }))
      .subscribe(
        listaPerfis => this.listaPerfis = listaPerfis
      );
    if (this.usuario != null) {
      this.perfilUsuarioService.listarPorUsuario(this.usuario.username)
        .pipe(finalize(() => {
          // @TODO Habilitar perfis já cadastrados
        }))
        .subscribe(
          listaPerfisUsuario => this.listaPerfisUsuario = listaPerfisUsuario
        );
    }
  }

  habilitarSenha(): void {
    this.formUsuario.get('password').enable();
  }

  gravar(): void {
    this.formEnviado = true;
    if (this.formUsuario.valid) {
      this.usuarioService.gravar(this.formUsuario.value, this.incluir).subscribe({
        next: usuario => {
          this.usuario = usuario;
          this.formUsuario.reset();
          this.formUsuario.patchValue(usuario);
          this.formUsuario.controls['password'].setValue('');
          this.formUsuario.controls['password'].disable();
          this.exibeMensagem.show(
              `Dados do usuário ${usuario.nome} gravados com sucesso`,
              DecoracaoMensagem.SUCESSO,
              'Gravar Usuário'
          );
        },
        error: objetoErro => {
          this.exibeMensagem.show(
              `${objetoErro.error.message}`,
              DecoracaoMensagem.ERRO,
              'Erro de processamento'
          );
        }
      })
    }
    else {
      this.exibeMensagem.show(
          'Preencha todos os dados obrigatórios antes de gravar os dados',
          DecoracaoMensagem.PRIMARIO,
          'Dados Obrigatórios'
      );
    }
  }

  gravarPerfis(): void {
    for (let i = 0; i < this.formPerfis.value.perfis.length; i++) {
      let perfilUsuario = new PerfilUsuario();
      perfilUsuario.username = this.usuario.username;
      perfilUsuario.authority = this.listaPerfis[i].authority;
      if (this.formPerfis.value.perfis[i])
        this.perfilUsuarioService.incluir(perfilUsuario).subscribe();
      else
        this.perfilUsuarioService.excluir(perfilUsuario).subscribe();
    }
    this.exibeMensagem.show(
        `Permissões do usuário atualizadas com sucesso`,
        DecoracaoMensagem.SUCESSO,
        'Gravar Permissões'
    );
  }
}
