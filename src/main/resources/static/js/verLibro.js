$(document).ready(function () {
    $('#tab_logic').DataTable();
});

//
//var $rows = $('#tab_logic tbody tr');
//$('#busquedaInput').keyup(function() {
//
//    var val = '^(?=.*\\b' + $.trim($(this).val()).split(/\s+/).join('\\b)(?=.*\\b') + ').*$',
//    reg = RegExp(val, 'i'),
//    text;
//
//    $rows.show().filter(function() {
//        text = $(this).text().replace(/\s+/g, ' ');
//        return !reg.test(text);
//    }).hide();
//});
