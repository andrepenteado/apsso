import { PerfilSistema } from "./perfil-sistema";

export class Usuario {

    username: string;
    password: string;
    nome: string;
    dataCadastro: Date;
    dataUltimaModificacao: Date;
    enabled: boolean;
    perfis: PerfilSistema[];
    fotobase64: string;

}
