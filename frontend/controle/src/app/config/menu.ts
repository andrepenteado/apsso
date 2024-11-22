import { Menu } from "@andre.penteado/ngx-apcore";

export const MENU: Menu[] = [
  {
    id: 'empresa', texto: 'Empresas', icone: 'city', path: '/empresa/pesquisar', subMenus: [
      { texto: 'Pesquisar', path: '/empresa/pesquisar' },
      { texto: 'Incluir', path: '/empresa/cadastro' }
    ]
  },
  {
    id: 'sistema', texto: 'Sistemas', icone: 'desktop', path: '/sistema/pesquisar', subMenus: [
      { texto: 'Pesquisar', path: '/sistema/pesquisar' },
      { texto: 'Incluir', path: '/sistema/cadastro' }
    ]
  },
  {
    id: 'usuario', texto: 'Usuários', icone: 'person', path: '/usuario/pesquisar', subMenus: [
      { texto: 'Pesquisar', path: '/usuario/pesquisar' },
      { texto: 'Incluir', path: '/usuario/cadastro' }
    ]
  }
]
