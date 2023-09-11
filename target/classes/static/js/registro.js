async function registro() {
    let data = {};

    data.nombre = document.getElementById('nombre').value;
    data.apellido = document.getElementById('apellido').value;
    data.email = document.getElementById('email').value;
    data.clave = document.getElementById('clave').value;
    data.documento = document.getElementById('documento').value;
    data.telefono = document.getElementById('telefono').value;

    const request = await fetch('/api/v1/usuario/guardar', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(res => res.json())
        .then(data => {
            if (data.guardo) {
                alert("Usuario Registrado");
                window.location.href = '../index.html';
            }
            else
                alert("No se registró");
        }).catch( err => {
            alert("Error de registro.");
        });


}