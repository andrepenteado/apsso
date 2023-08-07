export class Core {

    static readonly DATATABLES_OPTIONS = {
        lengthMenu: [ [10, -1], ['Sim', 'Não'] ],
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
            paginate: {
                first: '<i class=\'fa fa-step-backward\'></i>',
                last: '<i class=\'fa fa-step-forward\'></i>',
                next: '<i class=\'fa fa-forward\'></i>',
                previous: '<i class=\'fa fa-backward\'></i>',
            }
        },
        responsive: true,
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

}
