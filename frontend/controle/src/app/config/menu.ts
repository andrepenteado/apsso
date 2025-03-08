import { Menu } from "@andre.penteado/ngx-apcore";

export const MENU: Menu[] = [
  {
    id: 'empresa', texto: 'Empresas', icone: 'city', path: '/empresa/pesquisar', subMenus: []
  },
  {
    id: 'sistema', texto: 'Sistemas', icone: 'desktop', path: '/sistema/pesquisar', subMenus: []
  },
  {
    id: 'usuario', texto: 'Usuários', icone: 'person', path: '/usuario/pesquisar', subMenus: []
  }
]
