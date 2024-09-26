import { Menu } from "@andre.penteado/ngx-apcore";

export const MENU: Menu[] = [
  {
    id: 'sistema', texto: 'Sistemas', icone: 'window-fullscreen', path: '/sistema/pesquisar', subMenus: [
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
