import { Injectable } from '@angular/core';
import Swal, { SweetAlertIcon, SweetAlertResult } from "sweetalert2"
import { ToastrService } from "ngx-toastr"

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
}

@Injectable({
  providedIn: 'root'
})
export class ExibirMensagemService {

  titulo: string = "Informação";

  mensagem: string = "Mensagem de informação";

  botao: string = "<i class=\"bi bi-check-lg\"></i> Sim";

  decoracao: DecoracaoMensagem = DecoracaoMensagem.PRIMARIO;

  icone: SweetAlertIcon = 'info';

  toastConfig = {
    progressBar: true,
    closeButton: true
  };

  constructor(private toastr: ToastrService) {}

  showMessage(mensagem: string, titulo?: string, decoracao?: DecoracaoMensagem): void {
    this.mensagem = mensagem;

    if (decoracao != null) {
      this.decoracao = decoracao;

      if (decoracao == DecoracaoMensagem.ATENCAO)
        this.toastr.warning(this.mensagem, titulo, this.toastConfig);
      if (decoracao == DecoracaoMensagem.ERRO)
        this.toastr.error(this.mensagem, titulo, this.toastConfig);
      if (decoracao == DecoracaoMensagem.SUCESSO)
        this.toastr.success(this.mensagem, titulo, this.toastConfig);
    }
    else {
      this.toastr.info(this.mensagem, titulo, this.toastConfig);
    }
  }

  showAlert(mensagem: string, titulo?: string, decoracao?: DecoracaoMensagem): void {
    this.mensagem = mensagem;

    if (titulo != null)
      this.titulo = titulo;
    if (decoracao != null) {
      if (decoracao == DecoracaoMensagem.ATENCAO)
        this.icone = 'warning';
      if (decoracao == DecoracaoMensagem.ERRO)
        this.icone = 'error';
      if (decoracao == DecoracaoMensagem.SUCESSO)
        this.icone = 'success';
    }

    Swal.fire({
      title: this.titulo,
      text: this.mensagem,
      icon: this.icone,
      showCloseButton: true,
      showCancelButton: false,
      confirmButtonText: 'OK'
    });
  }

  showConfirm(mensagem: string, titulo?: string, botao?: string): Promise<SweetAlertResult> {
    this.mensagem = mensagem;
    this.titulo = "Confirme";

    if (titulo != null)
      this.titulo = titulo;
    if (botao != null)
      this.botao = botao;

    return Swal.fire({
      title: this.titulo,
      text: this.mensagem,
      icon: 'question',
      showCloseButton: true,
      showCancelButton: true,
      cancelButtonText: '<i class="bi bi-x-lg"></i> Cancelar',
      confirmButtonText: this.botao
    });
  }

}
