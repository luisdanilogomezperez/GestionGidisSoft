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
                alert("Usuario registrado exitosamente");
                window.location.href = '../index.html';
            }
            else
                alert("¡Hubo un error durante el registro!");
        }).catch( err => {
            alert("Error de registro.");
        });


}