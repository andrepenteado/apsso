import { UserLogin } from "@andre.penteado/ngx-apcore";
import { Empresa } from "../domain/entities/empresa";

export class EquipeUserLogin extends UserLogin {

  empresas: Empresa[];

  fotoBase64!: string;

}
