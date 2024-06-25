$(document).ready(function () {
    $('#tab_logic1').DataTable();
    $('#tab_logic2').DataTable();
    $('#tab_logic3').DataTable();
    $('#tab_logic4').DataTable();
    $('#tab_logic5').DataTable();
    $('#tab_logic6').DataTable();
    $('#tab_logic7').DataTable();
    $('#tab_logic8').DataTable();
});

var estadoUsuario = document.getElementById("estadoUsuario").value;

function onLoad(){
    if (estadoUsuario != null) {
        confirmarCambioEstado();
    }
}
window.onload = onLoad;

function confirmarCambioEstado() {
    if (estadoUsuario.includes("error") || estadoUsuario.includes("Error")) {
        Swal.fire({
            position: "top-end",
            icon: "error",
            text: estadoUsuario,
            showConfirmButton: false,
            timer: 2000
            //backdrop: 'rgba(255, 0, 0, 0.1)'
        });
    } else {
        Swal.fire({
            position: "top-end",
            icon: "success",
            text: estadoUsuario,
            showConfirmButton: false,
            timer: 2000
            //backdrop: 'rgba(255, 0, 0, 0.1)'
        });
    }
}

function toggleAccordion(id) {
    var accordion = document.getElementById(id);
    accordion.classList.toggle("active");
    var content = accordion.nextElementSibling;
    if (content.style.maxHeight) {
        content.style.maxHeight = null;
    } else {
        content.style.maxHeight = content.scrollHeight + "px";
    }
}


function openTab(tabName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].classList.remove("active");
    }
    document.getElementById(tabName).classList.add("active");

    // Obtener todos los botones de pestaña
    tablinks = document.getElementsByClassName("tablinks");
    // Iterar sobre los botones de pestaña
    for (i = 0; i < tablinks.length; i++) {
        // Si el botón actual es el que se ha clicado, agregar la clase 'active'
        if (tablinks[i].getAttribute('onclick').includes(tabName)) {
            tablinks[i].classList.add("active");
        } else {
            // De lo contrario, quitar la clase 'active' para los demás botones
            tablinks[i].classList.remove("active");
        }
    }
}

