import {Menu} from "@andre.penteado/ngx-apcore";

export const MENU: Menu[] = [
  {
    id: 'acessar', texto: 'Acessar', icone: 'door-open', path: '/listar-sistemas', subMenus: [
      { texto: 'Listar Sistemas', path: '/listar-sistemas', icone: '' }
    ]
  },
  {
    id: 'meusdados', texto: 'Meus Dados', icone: 'person', path: '/meus-dados', subMenus: [
      { texto: 'Meus Dados', path: '/meus-dados', icone: '' }
    ]
  }
]
