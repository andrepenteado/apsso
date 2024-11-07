import { Empresa } from "./empresa";

export class Sistema {

  id: number;
  dataCadastro: Date;
  dataUltimaAtualizacao: Date;
  usuarioCadastro: string;
  usuarioUltimaAtualizacao: string;
  identificador: string;
  nome: string;
  descricao: string;
  icone: string;
  empresa: Empresa;

}
