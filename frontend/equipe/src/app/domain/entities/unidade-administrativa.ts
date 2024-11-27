import { TipoUnidadeAdministrativa } from "../enums/tipo-unidade-administrativa";
import { Empresa } from "./empresa";

export class UnidadeAdministrativa {

  id: number;
  nome: string;
  tipo: TipoUnidadeAdministrativa;
  empresa: Empresa;
  unidadeAdministrativaSuperior: UnidadeAdministrativa;

}
