import { TipoUnidadeAdministrativa } from "../enums/tipo-unidade-administrativa";
import { Empresa } from "./empresa";
import { Colaborador } from "./colaborador";

export class UnidadeAdministrativa {

  id: number;
  nome: string;
  tipo: TipoUnidadeAdministrativa;
  empresa: Empresa;
  unidadeAdministrativaSuperior: UnidadeAdministrativa;
  colaboradores: Colaborador[];

}
