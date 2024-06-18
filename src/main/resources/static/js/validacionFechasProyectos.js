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

function validateDates() {
    var anioInicio = parseInt(document.getElementById("anioInicio").value);
    var mesInicio = document.getElementById("mesInicio").selectedIndex;
    var anioFin = parseInt(document.getElementById("anioFin").value);
    var mesFin = document.getElementById("mesFin").selectedIndex;
    var guardarBtn = document.getElementById("guardar");

    if (anioFin < anioInicio || (anioFin === anioInicio && mesFin <= mesInicio)) {
        alert("La fecha de fin debe ser posterior a la fecha de inicio.");
        guardarBtn.disabled = true;
    } else {
        guardarBtn.disabled = false;
    }
}

document.addEventListener("DOMContentLoaded", function() {


    populateYearSelect("anioInicio");
    populateYearSelect("anioFin");

    var anioFinSele = document.getElementById("anioF").getAttribute("data-anioFin");
    var anioInicioSele = document.getElementById("anioIni").getAttribute("data-anioInicio");

    if (anioFinSele) {
        var selectAnio = document.getElementById("anioFin");
        selectAnio.value = anioFinSele;
    }
    if (anioInicioSele) {
        var selectAnio = document.getElementById("anioInicio");
        selectAnio.value = anioInicioSele;
    }

    document.getElementById("anioInicio").addEventListener("change", validateDates);
    document.getElementById("mesInicio").addEventListener("change", validateDates);
    document.getElementById("anioFin").addEventListener("change", validateDates);
    document.getElementById("mesFin").addEventListener("change", validateDates);
});