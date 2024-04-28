function onLoad(){
    ComboAno();
    cargarMeses();
//    cargarPaises();

    var tipoProyecto = document.getElementById("idTipoProyecto").getAttribute("data-tipoProyecto");
    var anioInicio = document.getElementById("anioIni").getAttribute("data-anioInicio");
    var anioFin = document.getElementById("anioF").getAttribute("data-anioFin");
    var mesInicio = document.getElementById("mesIni").getAttribute("data-mesInicio");
    var mesFin = document.getElementById("mesF").getAttribute("data-mesFin");
    var esSolidario = document.getElementById("idEsSolidario").getAttribute("data-solidario");
    var esFinanciado = document.getElementById("idEsFinanciado").getAttribute("data-financiado");
    var fuenteFinanciacion = document.getElementById("fuenteFinanciacion").value;
    var tipoFinanciacion = document.getElementById("idTipoFinanciacion").value;
    alert(esSolidario + " -- " + esFinanciado + " -- " + fuenteFinanciacion + " -- " + tipoFinanciacion)
//    var fechaActoAdministrativo = document.getElementById("idFechaActoAdministrativo").getAttribute("data-fechaActoAdministrativo");
    // Si hay un año de libro, establece ese año como seleccionado

    switch (tipoProyecto) {
        case "Investigación":
            document.getElementById("tipo_proyecto_1").checked = true;
            break;
        case "Investigación y desarrollo":
            document.getElementById("tipo_proyecto_2").checked = true;
            break;
        case "Investigación + Creación":
            document.getElementById("tipo_proyecto_3").checked = true;
            break;
        case "Extensión y responsabilidad social en CTeI":
            document.getElementById("tipo_proyecto_4").checked = true;
            break;
        default:
            // Manejar el caso cuando no coincide con ningún tipo de proyecto conocido
            console.error("Tipo de proyecto desconocido:", tipoProyecto);
    }
    switch (esSolidario) {
        case "SI":
            document.getElementById("solidario").checked = true;
            document.getElementById("financiado").checked = false;
            break;
        default:
            console.error("Valor desconocido:", tipoProyecto);
    }
    switch (esFinanciado) {
        case "SI":
            document.getElementById("financiado").checked = true;
            mostrarTipoFinanciacion()
            break;
        default:
            console.error("Tipo de proyecto desconocido:", tipoProyecto);
    }
    var tipoFinanciacionDiv = document.getElementById('tipoFinanciacion');
    switch (fuenteFinanciacion) {
        case "Interna":
            tipoFinanciacionDiv.innerHTML = `
                <div class="col-xd-12 col-sm-12">
                    <div class="row">
                        <div class="col-xd-4 col-sm-4">
                            <div class="form-group">
                                <br><br>
                                <label for="input">Fuente de financiación(*)</label><br>
                                <div id="idFuenteFinanciacion" th:attr="data-fuenteFinanciacion=*{fuenteFinanciacion}"></div>
                                <input type="radio" checked onclick="mostrarFuenteFinanciacion()" id="idInterna" name="fuente_financiacion" value="Interna" required> Interna
                                <input type="radio" class="ml" onclick="mostrarFuenteFinanciacion()" id="idExterna" name="fuente_financiacion" value="Externa" required> Externa
                            </div>
                        </div>
                        <div class="col-xd-6 col-sm-6" id="fuenteFinanciacion">
                            <input type="hidden" id="idFinanciacion" name="financiacion" value="">
                        </div>
                    </div>
                </div>
            `;
            break;
        case "Externa":
            tipoFinanciacionDiv.innerHTML = `
                <div class="col-xd-12 col-sm-12">
                    <div class="row">
                        <div class="col-xd-4 col-sm-4">
                            <div class="form-group">
                                <br><br>
                                <label for="input">Fuente de financiación(*)</label><br>
                                <div id="idFuenteFinanciacion" th:attr="data-fuenteFinanciacion=*{fuenteFinanciacion}"></div>
                                <input type="radio" onclick="mostrarFuenteFinanciacion()" id="idInterna" name="fuente_financiacion" value="Interna" required> Interna
                                <input type="radio" checked class="ml" onclick="mostrarFuenteFinanciacion()" id="idExterna" name="fuente_financiacion" value="Externa" required> Externa
                            </div>
                        </div>
                        <div class="col-xd-6 col-sm-6" id="fuenteFinanciacion">
                            <input type="hidden" id="idFinanciacion" name="financiacion" value="">
                        </div>
                    </div>
                </div>
            `;
            break;
        default:
            console.error("Tipo de proyecto desconocido:", tipoProyecto);
    }
    var fuenteFinanciacionDiv = document.getElementById('fuenteFinanciacion');
    switch (fuenteFinanciacion) {
        case "Externa":
            fuenteFinanciacionDiv.innerHTML = `
                <div class="form-group">
                    <br><br><br>
                    <div id="idTipoFinanciacion" th:attr="data-tipoFinanciacion=*{tipoFinanciacion}"></div>
                    <input type="radio" id="idNacional" name="financiacion" value="Nacional" required> Nacional
                    <input type="radio" class="ml" id="idInternacional" name="financiacion" value="Internacional" required> Internacional
                </div>
            `;
            break;
        case "Interna":
            document.getElementById("financiado").checked = true;
            mostrarTipoFinanciacion()
            break;
        default:
            console.error("Tipo de proyecto desconocido:", tipoProyecto);
    }
    // Obtener la fecha del atributo data-fechaActoAdministrativo
    var fechaActoAdministrativo = document.getElementById("idFechaActoAdministrativo").getAttribute("data-fechaActoAdministrativo");

    // Convertir la fecha al formato deseado (YYYY-MM-DD)
    var fechaFormateada = fechaActoAdministrativo.split(" ")[0]; // Obtener solo la parte de la fecha (ignorar la hora)
    console.log("Fecha formateada:", fechaFormateada); // Comprobar la fecha formateada

    // Asignar la fecha formateada al input usando jQuery UI Datepicker
    $('#dta_acto_admString').datepicker({
        dateFormat: 'yy-mm-dd' // Formato de fecha deseado
    }).datepicker('setDate', fechaFormateada); // Establecer la fecha en el input


    if (anioInicio) {
        var select = document.getElementById("anioInicio");
        select.value = anioInicio;
    }
    if (anioFin) {
        var select = document.getElementById("anioFin");
        select.value = anioFin;
    }
    if (mesInicio) {
        var select = document.getElementById("mesInicio");
        select.value = mesInicio;
    }
    if (mesFin) {
        var select = document.getElementById("mesFin");
        select.value = mesFin;
    }
}
window.onload = onLoad;

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
    var selectInicio = document.getElementById("mesInicio");
    var selectFin = document.getElementById("mesFin");

    var meses = [
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    ]

    // Recorremos el array de meses y agregamos cada uno como una opción
    for (var i = 0; i < meses.length; i++) {
        var option = document.createElement("option");
        option.text = meses[i];
        option.value = meses[i]; // El valor será el nombre del mes
        selectInicio.add(option);
    }
    for (var i = 0; i < meses.length; i++) {
        var option = document.createElement("option");
        option.text = meses[i];
        option.value = meses[i]; // El valor será el nombre del mes
        selectFin.add(option);
    }
}

function ComboAno(){
    var n = (new Date()).getFullYear()
    var selectInicio = document.getElementById("anioInicio");
    var selectFin = document.getElementById("anioFin");
    for(var i = n; i>=1900; i--)selectInicio.options.add(new Option(i,i));
    for(var i = n; i>=1900; i--)selectFin.options.add(new Option(i,i));
}

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

function mostrarTipoFinanciacion() {
    var tipoFinanciacion = document.querySelector('input[name="tipo_financiacion_proyecto"]:checked').value;
    var tipoFinanciacionDiv = document.getElementById('tipoFinanciacion');

        if (tipoFinanciacion === "Financiado") {
            tipoFinanciacionDiv.innerHTML = `
                <div class="col-xd-12 col-sm-12">
                    <div class="row">
                        <div class="col-xd-4 col-sm-4">
                            <div class="form-group">
                                <br><br>
                                <label for="input">Fuente de financiación(*)</label><br>
                                <div id="idFuenteFinanciacion" th:attr="data-fuenteFinanciacion=*{fuenteFinanciacion}"></div>
                                <input type="radio" checked onclick="mostrarFuenteFinanciacion()" id="idInterna" name="fuente_financiacion" value="Interna" required> Interna
                                <input type="radio"  class="ml" onclick="mostrarFuenteFinanciacion()" id="idExterna" name="fuente_financiacion" value="Externa" required> Externa
                            </div>
                        </div>

                        <div class="col-xd-6 col-sm-6" id="fuenteFinanciacion">
                            <input type="hidden" id="idFinanciacion" name="financiacion" value="">
                        </div>
                    </div>
                </div>
            `;
        } else {
            // Limpiamos el contenido del div si no es "Financiado"
            tipoFinanciacionDiv.innerHTML = `
                        <input type="hidden" id="idInterna" name="fuente_financiacion" value="">
                        <input type="hidden" id="idInternacional" name="financiacion" value="">
            `;
        }
}

function mostrarFuenteFinanciacion() {
    var fuenteFinanciacion = document.querySelector('input[name="fuente_financiacion"]:checked').value;
    var fuenteFinanciacionDiv = document.getElementById('fuenteFinanciacion');

    if (fuenteFinanciacion === "Externa") {
        fuenteFinanciacionDiv.innerHTML = `
                <div class="form-group">
                    <br><br><br>
                    <div id="idTipoFinanciacion" th:attr="data-tipoFinanciacion=*{tipoFinanciacion}"></div>
                    <input type="radio" checked id="idNacional" name="financiacion" value="Nacional" required> Nacional
                    <input type="radio" class="ml" id="idInternacional" name="financiacion" value="Internacional" required> Internacional
                </div>
            `;
    } else {
        fuenteFinanciacionDiv.innerHTML =`
                <input type="hidden" id="idFinanciacion" name="financiacion" value="">
        `;
    }
}

function cambiarInputValorProyecto() {
    var select = document.getElementById("rolIntitucion");
    var valorSeleccionado = select.value;
    var tituloValorProyecto = document.getElementById("tituloValorProyecto");
    var contenidoInput = document.getElementById("contenidoInput");

    switch (valorSeleccionado) {
        case "Financiadora":
                tituloValorProyecto.innerHTML = "<br><br>Valor del proyecto sin contrapartida (*) (Pesos)";
                contenidoInput.innerHTML = '<input type="text" th:field="*{valorProyectoSinContrapartida}" class="form-control" name="valorProyectoSinContrapartida" required>';
                break;
            case "Ejecutora":
                tituloValorProyecto.innerHTML = "<br><br>Valor de la contrapartida";
                contenidoInput.innerHTML = '<input type="text" th:field="*{valorContrapartida}" class="form-control" name="valorContrapartida" required>';
                break;
            case "Coejecutora":
                tituloValorProyecto.innerHTML = "<br><br>Valor de la contrapartida";
                contenidoInput.innerHTML = '<input type="text" th:field="*{valorContrapartida}" class="form-control" name="valorContrapartida" required>';
                break;
            default:
                break;
    }
}

$(document).ready(function() {
    // Cuando se ingresa texto en el textarea
    $('#resumen').on('input', function() {
        // Obtener la longitud del texto ingresado
        var longitudTexto = $(this).val().length;
        // Calcular los caracteres restantes
        var caracteresRestantes = 4000 - longitudTexto;
        // Actualizar el contador
        $('#contador').text(caracteresRestantes);
    });
});

//$(function() {
//    $('#dta_acto_admString').datepicker({
//        dateFormat: 'yy-mm-dd' // Formato de fecha deseado
//    });
//});
