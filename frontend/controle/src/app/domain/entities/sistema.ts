import { Empresa } from "./empresa";

export class Sistema {

  id: string;
  dataCadastro: Date;
  dataUltimaAtualizacao: Date;
  usuarioCadastro: string;
  usuarioUltimaAtualizacao: string;
  codigo: string;
  nome: string;
  descricao: string;
  icone: string;
  empresa: Empresa;

}
