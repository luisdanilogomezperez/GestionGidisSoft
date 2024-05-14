
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
            'Por favor, póngase en contacto con el <a href="#">equipo de soporte</a> para más información.' +
            '<br><br>' +
            'Si cree que esto es un error, puede <a href="#">solicitar una revisión</a>.' +
            '<br><br>' +
            'Gracias por su comprensión y paciencia.',
            showCloseButton: true,
            focusConfirm: false,
            confirmButtonText: 'Entendido',
            confirmButtonColor: 'red',
            footer: '<a href="#">¿Necesitas más ayuda?</a>'
        });
    }
}
}