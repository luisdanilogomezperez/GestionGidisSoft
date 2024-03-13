
function onLoad(){
    ComboAno();
    cargarMeses();
//    cargarIdiomas();
}

window.onload = onLoad;

// Función para cargar los meses en el select
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

function ComboAno(){
    var n = (new Date()).getFullYear()
    var select = document.getElementById("anio");
    for(var i = n; i>=1900; i--)select.options.add(new Option(i,i));
}

function cargarPaises() {
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
            select.add(option);
        });
    })
        .catch(function(error) {
        console.error("Error al cargar los países:", error);
    });
}

function validarDOI(doi) {
    // Expresión regular para validar el formato del DOI
    const doiRegex = /^10\.\d{4,9}\/[-._;()/:A-Z0-9]+$/i;

    // Limpiar cualquier espacio o guión del DOI
    doi = doi.replace(/[-\s]/g, "");

    if (doiRegex.test(doi)) {
        return true;
    }

    return false;
}

const doiInput = document.getElementById("digitalObjectIdentifierDOI");
const mensaje = document.getElementById("mensaje");

doiInput.addEventListener("input", function() {
    const doi = doiInput.value;
    const esValido = validarDOI(doi);
    const submitBtn = document.getElementById("guardar");

    if (doi === "") {
        mensaje.textContent = ""; // Ocultar mensaje cuando el campo está vacío
    } else {
        mensaje.textContent = esValido ? "DOI válido." : "DOI no válido.";
        mensaje.style.color = esValido ? "green" : "red";
        // Deshabilitar o habilitar el botón según la validez del DOI
        submitBtn.disabled = !esValido;
    }
});
//
//function cargarIdiomas() {
//    var select = document.getElementById("idioma"); // Asegúrate de que el ID coincida con tu select
//
//    // Hacer una solicitud a la API de ISO 639-2
//    fetch("https://restcountries.com/v3.1/all")
//        .then(function(response) {
//        return response.json();
//    })
//        .then(function(data) {
//        var idiomasUnicos = new Set();
//
//        // Recorrer los datos y agregar idiomas al conjunto
//        data.forEach(function(infoPais) {
//            if (infoPais.languages) {
//                infoPais.languages.forEach(function(idioma) {
//                    idiomasUnicos.add(idioma);
//                });
//            }
//        });
//
//        // Convertir el conjunto a un array y ordenar alfabéticamente
//        var arrayIdiomas = Array.from(idiomasUnicos).sort();
//
//        // Agregar opciones al select
//        arrayIdiomas.forEach(function(idioma) {
//            var option = document.createElement("option");
//            option.text = idioma;
//            option.value = idioma; // Puedes ajustar el valor según tus necesidades
//            select.add(option);
//        });
//    })
//        .catch(function(error) {
//        console.error("Error al cargar los idiomas:", error);
//    });
//}
//
// JavaScript
// JavaScript
document.addEventListener("DOMContentLoaded", function() {
    var paisSelect = document.getElementById("pais");
    var ciudadSelect = document.getElementById("ciudad");

    // Obtener información de países al cargar la página
    fetch("https://restcountries.com/v2/all")
        .then(response => response.json())
        .then(data => {
        // Agregar opciones de países
        data.forEach(pais => {
            var option = document.createElement("option");
            option.text = pais.name;
            option.value = pais.alpha2Code; // Usamos el código del país como valor
            paisSelect.add(option);
        });
    })
        .catch(error => console.error("Error al obtener países:", error));

    // Agregar evento change al select de país
    paisSelect.addEventListener("change", function() {
        var selectedPaisCode = paisSelect.value;

        // Limpiar select de ciudades
        ciudadSelect.innerHTML = "<option value=''>Seleccione una ciudad</option>";

        // Obtener información de ciudades para el país seleccionado
        fetch(`http://api.geonames.org/searchJSON?country=${selectedPaisCode}&username=TOKIO`)
            .then(response => response.json())
            .then(data => {
            // Agregar opciones de ciudades
            data.geonames.forEach(ciudad => {
                var option = document.createElement("option");
                option.text = ciudad.name;
                option.value = ciudad.name;
                ciudadSelect.add(option);
            });
        })
            .catch(error => console.error("Error al obtener ciudades:", error));
    });
});
