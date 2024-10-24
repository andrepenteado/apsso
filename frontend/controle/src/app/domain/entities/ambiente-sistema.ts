import { Sistema } from "./sistema";

export class AmbienteSistema {

  id: number;
  descricao: string;
  urlEntrada: string;
  sistema: Sistema;
  clientId: string;
  clientSecret: string;
  redirectUris: string;
  postLogoutRedirectUris: string;
  clientAuthenticationMethods: string;
  authorizationGrantTypes: string;
  scopes: string;
  clientSettings: string;
  tokenSettings: string;

}
