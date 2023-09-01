import {PerfilUsuario} from "./perfil-usuario";

export class Usuario {

    username: string;
    password: string;
    nome: string;
    dataCadastro: Date;
    dataUltimaModificacao: Date;
    enabled: boolean;
    perfis: PerfilUsuario[];

}