async function registro() {
    let data = {};

    data.username = document.getElementById('username').value;
    data.clave = document.getElementById('clave').value;
    data.primerNombre = document.getElementById('primerNombre').value;
    data.segundoNombre = document.getElementById('segundoNombre').value;
    data.primerApellido = document.getElementById('primerApellido').value;
    data.segundoApellido = document.getElementById('segundoApellido').value;
    data.telefono = document.getElementById('telefono').value;
    data.direccion = document.getElementById('direccion').value;
    data.documento = document.getElementById('documento').value;
    data.email = document.getElementById('email').value;


    const request = await fetch('/api/v1/usuarios/guardar', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(res => res.json())
        .then(data => {
            if (data.guardo) {
                Swal.fire({
                    title: "Éxito",
                    text: "El registro se ha completado con éxito.",
                    icon: "success",
                    timer: 3000,
                    showCancelButton: false,
                    showConfirmButton: false
                }).then(() => {
                    // Esta función se ejecutará después de que el usuario cierre el SweetAlert
                    window.location.href = '../index.html'; // Redirige a la página deseada
                });
            }
            else
            Swal.fire({
                icon: 'error',
                title: 'Error',
                html: '<div style="text-align: justify;">' +
                'Se ha producido un error durante el registro.<br/><br/>' +
                'Al parecer, ya existe un usuario registrado con la misma dirección de correo electrónico, o te recomendamos verificar cuidadosamente los datos proporcionados.' +
                '</div>',
                showCancelButton: false,
                showConfirmButton: true
            }).then(() => {
                // Puedes agregar más acciones después de que se cierre la alerta si es necesario
                console.log('Alerta de error cerrada');
            });


    }).catch( err => {
            alert("Error de registro.");
        });


}