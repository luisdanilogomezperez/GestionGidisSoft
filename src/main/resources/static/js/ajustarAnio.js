function populateYearSelect(id) {
    var select = document.getElementById(id);
    var currentYear = new Date().getFullYear();
    for (var year = currentYear; year >= 1900; year--) {
        var option = document.createElement("option");
        option.value = year;
        option.text = year;
        select.add(option);
    }
}


document.addEventListener("DOMContentLoaded", function() {

    populateYearSelect("anio");

    var anioFinSele = document.getElementById("anioSeleccionado").getAttribute("data-anio");

    if (anioFinSele) {
        var selectAnio = document.getElementById("anio");
        selectAnio.value = anioFinSele;
    }

});