export const DATATABLES_OPTIONS = {
  lengthMenu: [[10, -1], ['Sim', 'Não']],
  pagingType: 'simple_numbers',
  language: {
    lengthMenu: 'Paginar? _MENU_',
    loadingRecords: 'Aguarde, carregando...',
    processing: 'Aguarde, em processamento...',
    info: 'Registro _START_ até _END_. Total de _TOTAL_',
    infoFiltered: '(Máximo de _MAX_)',
    infoEmpty: 'Nenhum dado retornado',
    search: 'Filtrar',
    emptyTable: 'Não existem dados a serem exibidos',
    zeroRecords: 'Nenhum resultado encontrado para o filtro informado',
    paginate: {
      first: '<i class=\'bi bi-chevron-double-left\'></i>',
      last: '<i class=\'bi bi-chevron-double-right\'></i>',
      next: '<i class=\'bi bi-chevron-right\'></i>',
      previous: '<i class=\'bi bi-chevron-left\'></i>',
    }
  },
  responsive: false,
  processing: true,
  colReorder: false,
  select: false,
  dom: '<\'row\'' +
    '<\'d-none d-sm-block col-sm-4 col-md-4\'l>' +
    '<\'d-none d-sm-block col-sm-4 col-md-4\'B>' +
    '<\'col-12 col-sm-4 col-md-4\'f>' +
    '>' +
    '<\'row\'' +
    '<\'col-12 col-sm-12 col-md-12\'rt>' +
    '>' +
    '<\'row\'' +
    '<\'d-none d-sm-block col-sm-6 col-md-6\'i>' +
    '<\'col-12 col-sm-6 col-md-6\'p>' +
    '>'
};
