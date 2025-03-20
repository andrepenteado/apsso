import { Cargo } from "./cargo";

export class Colaborador {

  id: number;
  dataCadastro: Date;
  dataUltimaAtualizacao: Date;
  usuarioCadastro: string;
  usuarioUltimaAtualizacao: string;
  nome: string;
  cpf: number;
  telefone: string;
  email: string;
  cep: number;
  logradouro: string;
  complemento: string;
  numeroLogradouro: number;
  bairro: string;
  cidade: string;
  estado: string;
  cargo: Cargo;

}
