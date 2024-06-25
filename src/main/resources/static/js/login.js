
var estadoUsuario = document.getElementById("estadoUsuario").value;

function onLoad(){
    if (estadoUsuario != null) {
        mostrarEstadoUsuario();
    }
}
window.onload = onLoad;

function mostrarEstadoUsuario() {
    if (estadoUsuario.includes("DESHABILITADO")) {
        Swal.fire({
            icon: "info",
            title: '<strong>Usuario Deshabilitado Temporalmente</strong>',
            html:
            'Lamentablemente, su cuenta ha sido deshabilitada por un breve periodo.' +
            '<br><br>' +
            'Por favor, póngase en contacto con el equipo de soporte <b><a href="#">gidis@ufps.edu.co</a></b> para más información.' +
            '<br><br>' +
            '<br><br>' +
            'Gracias por su comprensión y paciencia.',
            showCloseButton: true,
            focusConfirm: false,
            confirmButtonText: 'Entendido',
            confirmButtonColor: 'red'
        });
    }
}
