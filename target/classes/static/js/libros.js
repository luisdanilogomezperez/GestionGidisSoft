function onLoad(){
    ComboAno();
    cargarMeses();
    cargarPaises();

}
window.onload = onLoad;

$(document).ready(function () {
    $("table.display").Datatable();
});

async function mostrarMes(){
    var select = document.getElementById("meses").value;
}

function eliminarLibro(libroId) {
    if (confirm("¿Estás seguro de que deseas eliminar este libro "+ libroId +"?")) {
        // Realizar la solicitud AJAX
        $.ajax({
            type: "DELETE",
            url: "/libros/eliminar/" + libroId,
            success: function (response) {
                alert(response);

                // Eliminar la fila de la tabla
                $("#fila-" + libroId).remove();
            },
            error: function (error) {
                alert("Error al intentar eliminar el libro");
            }
        });
    }
}



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

function validarISBN(isbn) {
    isbn = isbn.replace(/[-\s]/g, "").replace(/\D/g, "");

    if (isbn.length === 10 || isbn.length === 13) {
        const digitos = isbn.split("").map(Number);

        if (isbn.length === 10) {
            // Validar ISBN-10
            const suma = digitos.reduce((acc, val, index) => acc + val * (index === 9 ? 10 : index + 1), 0);
            return suma % 11 === 0;
        } else {
            // Validar ISBN-13
            const suma = digitos.reduce((acc, val, index) => acc + val * (index % 2 === 0 ? 1 : 3), 0);
            return suma % 10 === 0;
        }
    }

    return false;
}

const isbnInput = document.getElementById("isbn");
const mensaje = document.getElementById("mensaje");

isbnInput.addEventListener("input", function() {
    const isbn = isbnInput.value;
    const esValido = validarISBN(isbn);
    const submitBtn = document.getElementById("guardar");

    if (isbn === "") {
        mensaje.textContent = ""; // Ocultar mensaje cuando el campo está vacío
    } else {
        mensaje.textContent = esValido ? "ISBN válido." : "ISBN no válido.";
        mensaje.style.color = esValido ? "green" : "red";
        // Deshabilitar o habilitar el botón según la validez del ISBN
        submitBtn.disabled = !esValido;
    }
});

function borrarDocumento(archivo) {
    var fileInput = document.getElementById(archivo);
    fileInput.value = "";
}

function validarDocumento(id) {
    var fileInput = document.getElementById(id);
    if (fileInput.files[0].size > 20097152) {
        alert("El tamaño del documento excede los 20MB");
        fileInput.value = "";
    }
    if (fileInput.files[0].type != "application/pdf") {
        alert("El tipo de archivo no es válido");
        fileInput.value = "";
    }
}
function validarSelect(select) {
    return select.value !== "seleccione";
}

function actualizarEstadoBoton() {
    var selects = document.querySelectorAll('#miFormulario select');
    var submitBtn = document.getElementById("guardar");

    // Verifica si todos los selects tienen una opción válida
    var todosValidos = Array.from(selects).every(validarSelect);

    // Habilita o deshabilita el botón según la validación
    submitBtn.disabled = !todosValidos;
}

// Agrega un eventListener a cada select
var selects = document.querySelectorAll('#miFormulario select');
selects.forEach(function (select) {
    select.addEventListener('change', function () {
        // Actualiza el estado del botón cuando cambia un select
        actualizarEstadoBoton();
    });
});

$('#registroModal').on('show.bs.modal', function (event) {
    var modal = $(this);
    // Carga el contenido del formulario desde otro archivo HTML
    modal.find('.modal-body').load('formulario_registro_coautor.html');
});

// Función para mostrar el popup
function mostrarPopup() {
    document.getElementById("popup").style.display = "flex";
}

// Función para cerrar el popup
function cerrarPopup() {
    document.getElementById("popup").style.display = "none";
}



