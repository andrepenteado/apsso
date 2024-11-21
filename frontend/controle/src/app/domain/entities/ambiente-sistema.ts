import { Sistema } from "./sistema";
import { TipoAmbiente } from "../enums/tipo-ambiente";

export class AmbienteSistema {

  id: string;
  descricao: string;
  urlAcesso: string;
  tipo: TipoAmbiente;
  sistema: Sistema;
  clientId: string;
  clientSecretPlain: string;
  uriProvider: string;
  redirectUris: string;
  postLogoutRedirectUris: string;

}
