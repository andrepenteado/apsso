import { Menu } from "@andre.penteado/ngx-apcore";

export const MENU: Menu[] = [
  {
    id: 'empresa', texto: 'Empresas', icone: 'city', path: '/empresa/pesquisar', subMenus: [
      { texto: 'Pesquisar', path: '/empresa/pesquisar', icone: '' },
      { texto: 'Incluir', path: '/empresa/cadastro', icone: '' }
    ]
  },
  {
    id: 'sistema', texto: 'Sistemas', icone: 'desktop', path: '/sistema/pesquisar', subMenus: [
      { texto: 'Pesquisar', path: '/sistema/pesquisar', icone: '' },
      { texto: 'Incluir', path: '/sistema/cadastro', icone: '' }
    ]
  },
  {
    id: 'usuario', texto: 'Usuários', icone: 'person', path: '/usuario/pesquisar', subMenus: [
      { texto: 'Pesquisar', path: '/usuario/pesquisar', icone: '' },
      { texto: 'Incluir', path: '/usuario/cadastro', icone: '' }
    ]
  }
]
