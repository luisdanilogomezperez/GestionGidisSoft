document.addEventListener("DOMContentLoaded", function() {
    cargarPaises()
    ComboAno()
    cargarMeses()

    // Obtén el año del Libro desde el atributo de datos
    var lugarLibro = document.getElementById("lugarLibro").getAttribute("data-lugarLibro");
    var anoLibro = document.getElementById("anioLibro").getAttribute("data-anioLibro");
    var mesLibro = document.getElementById("mesLibro").getAttribute("data-mesLibro");

    // Si hay un año de libro, establece ese año como seleccionado

    if (lugarLibro) {
        var select = document.getElementById("lugarPublicacion");
        select.value = lugarLibro;
    }
    if (anoLibro) {
        var select = document.getElementById("anio");
        select.value = anoLibro;
    }
    if (mesLibro) {
        var select = document.getElementById("meses");
        select.value = mesLibro;
    }

});
function ComboAno() {
    var n = (new Date()).getFullYear();
    var select = document.getElementById("anio");
    for (var i = n; i >= 1900; i--) {
        select.options.add(new Option(i, i));
    }
}

function cargarMeses() {
    var select = document.getElementById("meses");
    var meses = [
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    ]

    // Recorremos el array de meses y agregamos cada uno como una opción
    for (var i = 0; i < meses.length; i++) {
        var option = document.createElement("option");
        option.text = meses[i];
        option.value = meses[i]; // El valor será el nombre del mes
        select.add(option);
    }
}
function cargarPaises(paisPublicacion) {
    var select = document.getElementById("lugarPublicacion");

    // Hacer una solicitud a la API
    fetch("https://restcountries.com/v2/all")
        .then(function(response) {
        return response.json();
    })
        .then(function(data) {
        // Recorrer los datos y agregar opciones al select
        data.forEach(function(pais) {
            var option = document.createElement("option");
            option.text = pais.name;
            option.value = pais.name; // Puedes usar el código del país como valor si lo deseas

            // Establecer el atributo 'selected' si es el país de publicación
            if (pais.name === paisPublicacion) {
                option.selected = true;
            }

            select.add(option);
        });
    })
        .catch(function(error) {
        console.error("Error al cargar los países:", error);
    });
}

// Obtén el país de publicación desde el atributo de datos
var paisPublicacion = document.getElementById("lugarLibro").getAttribute("data-lugarLibro");

// Llama a la función para cargar los países y seleccionar automáticamente el país de publicación
cargarPaises(paisPublicacion);

setTimeout(function() {
    $('#mensajeAlerta').fadeOut('slow');
}, 5000);
