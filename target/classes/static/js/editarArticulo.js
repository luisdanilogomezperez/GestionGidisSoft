document.addEventListener("DOMContentLoaded", function() {
    // Obtener elementos select
    var paisSelect = document.getElementById("pais");
    var ciudadSelect = document.getElementById("ciudad");

    ComboAno();
    cargarMeses();

    // Obtener datos del artículo
    var paisArticulo = document.getElementById("paisArticulo").getAttribute("data-paisArticulo");
    var ciudadArticulo = document.getElementById("ciudadArticulo").getAttribute("data-ciudadArticulo");
    var mesArticulo = document.getElementById("mesArticulo").getAttribute("data-mesArticulo");
    var anioArticulo = document.getElementById("anioArticulo").getAttribute("data-anioArticulo");

    // Establecer valores seleccionados de año y mes si están presentes
    if (anioArticulo) {
        var selectAnio = document.getElementById("anio");
        selectAnio.value = anioArticulo;
    }
    if (mesArticulo) {
        var selectMes = document.getElementById("meses");
        selectMes.value = mesArticulo;
    }

    // Cargar la lista de países y seleccionar el país correspondiente al artículo
    cargarPaises(paisArticulo);

    // Agregar evento change al select de país para cargar ciudades cuando cambia la selección
    paisSelect.addEventListener("change", function() {
        var selectedPais = paisSelect.value;
        cargarCiudades(selectedPais);
    });

    // Establecer el valor seleccionado del select de país y cargar las ciudades correspondientes
    if (paisArticulo) {
        paisSelect.value = paisArticulo;
        cargarCiudades(paisArticulo, ciudadArticulo);
    }
});

function ComboAno(){
    var n = (new Date()).getFullYear()
    var select = document.getElementById("anio");
    for(var i = n; i>=1900; i--)select.options.add(new Option(i,i));
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

function cargarPaises(paisSeleccionado = null) {
    var select = document.getElementById("pais");

    // Hacer una solicitud a la API para obtener la lista de países
    fetch("https://restcountries.com/v2/all")
        .then(response => response.json())
        .then(data => {
        // Recorrer los datos y agregar opciones al select
        data.forEach(pais => {
            var option = document.createElement("option");
            option.text = pais.name;
            option.value = pais.alpha2Code; // Usar el código del país como valor
            select.add(option);
        });

        // Establecer el valor seleccionado del país si está presente
        if (paisSeleccionado) {
            select.value = paisSeleccionado;
        }
    })
        .catch(error => console.error("Error al cargar los países:", error));
}

function cargarCiudades(pais, ciudadSeleccionada = null) {
    var ciudadSelect = document.getElementById("ciudad");

    // Limpiar select de ciudades
    ciudadSelect.innerHTML = "";

    // Obtener información de ciudades para el país seleccionado
    fetch(`http://api.geonames.org/searchJSON?country=${pais}&username=TOKIO`)
        .then(response => response.json())
        .then(data => {
        // Agregar opciones de ciudades
        data.geonames.forEach(ciudad => {
            var option = document.createElement("option");
            option.text = ciudad.name;
            option.value = ciudad.name;
            ciudadSelect.add(option);
        });

        // Establecer el valor seleccionado de la ciudad si está presente
        if (ciudadSeleccionada) {
            ciudadSelect.value = ciudadSeleccionada;
        }
    })
        .catch(error => console.error("Error al obtener ciudades:", error));
}
