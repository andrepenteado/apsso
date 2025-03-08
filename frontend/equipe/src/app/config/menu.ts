import { Menu } from "@andre.penteado/ngx-apcore";

export const MENU: Menu[] = [
  {
    id: 'unidadeadministrativa', texto: 'Unidades Administrativas', icone: 'building', path: '/unidade-administrativa/pesquisar', subMenus: []
  },
  {
    id: 'cargo', texto: 'Cargos', icone: 'briefcase', path: '/cargo/pesquisar', subMenus: []
  },
  {
    id: 'colaborador', texto: 'Colaboradores', icone: 'address-card', path: '/colaborador/pesquisar', subMenus: []
  }
]
