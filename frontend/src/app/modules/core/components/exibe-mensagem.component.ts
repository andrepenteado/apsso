import {Component, Input, OnInit} from '@angular/core';

export enum DecoracaoMensagem {
  SUCESSO = "bg-success",
  INFO = "bg-info",
  ERRO = "bg-danger",
  ATENCAO = "bg-warning",
  PRIMARIO = "bg-primary",
  SECUNDARIO = "bg-secondary",
  DIA = "bg-light",
  NOITE = "bg-dark",
  SIMPLES = ""
};

@Component({
  selector: 'ap-exibe-mensagem',
  template: `
      <ngb-toast header="{{ titulo }}" *ngIf="exibir" (hidden)="exibir = false" class="{{ decoracao }} toast-container position-fixed top-0 end-0 mt-4 me-4">
          {{ mensagem }}<br><br>
      </ngb-toast>
  `,
  styles: [
  ]
})
export class ExibeMensagemComponent implements OnInit {

  titulo: string = "Informação";

  exibir: boolean = false;

  mensagem: string = "";

  decoracao: DecoracaoMensagem;

  constructor() { }

  ngOnInit(): void {
  }

  hide(): void {
    this.exibir = false;
  }

  //show(mensagem: string): void;
  //show(mensagem: string, titulo: string): void;
  show(mensagem: string, decoracao?: DecoracaoMensagem, titulo?: string): void {
    this.mensagem = mensagem;
    this.exibir = true;
    if (decoracao != null)
      this.decoracao = decoracao;
    if (titulo != null)
      this.titulo = titulo;
    console.log(this.decoracao);
  }

}
