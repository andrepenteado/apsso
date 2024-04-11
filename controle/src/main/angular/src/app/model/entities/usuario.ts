import { PerfilSistema } from "./perfil-sistema";

export class Usuario {

  dataCadastro: Date;
  dataUltimaAtualizacao: Date;
  usuarioCadastro: string;
  usuarioUltimaAtualizacao: string;
  username: string;
  password: string;
  nome: string;
  enabled: boolean;
  perfis: PerfilSistema[];

}
