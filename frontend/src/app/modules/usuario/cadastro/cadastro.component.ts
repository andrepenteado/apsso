import {Component, OnInit, ViewChild} from '@angular/core';
import {DecoracaoMensagem, ExibeMensagemComponent} from '../../core/components/exibe-mensagem.component';
import {Usuario} from '../../../models/usuario';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {UsuarioService} from '../../../services/usuario.service';
import {PerfilSistema} from '../../../models/perfil-sistema';
import {PerfilSistemaService} from '../../../services/perfil-sistema.service';
import {finalize, lastValueFrom} from 'rxjs';
import {PerfilUsuarioService} from '../../../services/perfil-usuario.service';
import {PerfilUsuario} from '../../../models/perfil-usuario';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styles: [
  ]
})
export class CadastroComponent implements OnInit {

  @ViewChild('exibeMensagem')
  exibeMensagem: ExibeMensagemComponent = new ExibeMensagemComponent();

  aguardar = true;
  incluir = true;
  formEnviado = false;
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

  arrayPerfis = this.formPerfis.get('perfis') as FormArray;

  constructor(
      private activedRoute: ActivatedRoute,
      private usuarioService: UsuarioService,
      private perfilSistemaService: PerfilSistemaService,
      private perfilUsuarioService: PerfilUsuarioService
  ) { }

  ngOnInit(): void {
    this.activedRoute.params.subscribe(params => {
      const username: string = params.username;
      this.pesquisar(username);
    });
    this.aguardar = false;
  }

  async pesquisar(username: string): Promise<void> {
    if (username) {
      // Sync
      const usuario$ = this.usuarioService.buscar(username);
      this.usuario = await lastValueFrom(usuario$);
      this.incluir = false;
    }
    else {
      this.usuario = new Usuario();
    }

    this.dataCadastro = new Date(this.usuario.dataCadastro);
    this.dataUltimaModificacao = new Date(this.usuario.dataUltimaModificacao);
    this.formUsuario.patchValue(this.usuario);
    this.formUsuario.controls.password.setValue('');

    // Sync
    const perfisUsuario$ = this.perfilUsuarioService.listarPorUsuario(this.usuario.username);
    this.listaPerfisUsuario = await lastValueFrom(perfisUsuario$);

    // Sync
    const perfisSistema$ = this.perfilSistemaService.listar();
    this.listaPerfis = await lastValueFrom(perfisSistema$);

    for (const perfilSistema of this.listaPerfis) {
      let ativo = false;
      for (const perfilUsuario of this.listaPerfisUsuario) {
        if (perfilUsuario.authority === perfilSistema.authority) {
          ativo = true;
          break;
        }
      }
      const perfilForm = new FormControl(ativo);
      this.arrayPerfis.push(perfilForm);
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
          this.dataCadastro = new Date(this.usuario.dataCadastro);
          this.dataUltimaModificacao = new Date(this.usuario.dataUltimaModificacao);
          this.formUsuario.patchValue(usuario);
          this.formUsuario.controls.password.setValue('');
          this.formUsuario.controls.password.disable();
          this.incluir = false;
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

  gravarPerfis(): void {
    for (let i = 0; i < this.formPerfis.value.perfis.length; i++) {
      const perfilUsuario = new PerfilUsuario();
      perfilUsuario.username = this.usuario.username;
      perfilUsuario.authority = this.listaPerfis[i].authority;
      if (this.formPerfis.value.perfis[i]) {
        this.perfilUsuarioService.incluir(perfilUsuario).subscribe();
      }
      else {
        this.perfilUsuarioService.excluir(perfilUsuario).subscribe();
      }
    }
    this.exibeMensagem.show(
        `Permissões do usuário atualizadas com sucesso`,
        DecoracaoMensagem.SUCESSO,
        'Gravar Permissões'
    );
  }
}
