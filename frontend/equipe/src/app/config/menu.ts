import { Menu } from "@andre.penteado/ngx-apcore";

export const MENU: Menu[] = [
  {
    id: 'unidadeadministrativa', texto: 'Unidades Administrativas', icone: 'building', path: '/unidade-administrativa/pesquisar', subMenus: [
      { texto: 'Pesquisar', path: '/unidade-administrativa/pesquisar', icone: '' },
      { texto: 'Incluir', path: '/unidade-administrativa/cadastro', icone: '' }
    ]
  },
  {
    id: 'cargo', texto: 'Cargos', icone: 'briefcase', path: '/cargo/pesquisar', subMenus: [
      { texto: 'Pesquisar', path: '/cargo/pesquisar', icone: '' },
      { texto: 'Incluir', path: '/cargo/cadastro', icone: '' }
    ]
  },
  {
    id: 'colaborador', texto: 'Colaboradores', icone: 'address-card', path: '/colaborador/pesquisar', subMenus: [
      { texto: 'Pesquisar', path: '/colaborador/pesquisar', icone: '' },
      { texto: 'Incluir', path: '/colaborador/cadastro', icone: '' }
    ]
  }
]
