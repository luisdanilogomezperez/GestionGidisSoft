async function login() {
    console.log("Entré")
    let data = {};

    data.email = document.getElementById('email').value;
    data.clave = document.getElementById('clave').value;

    const request = await fetch('api/v1/usuario/login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(res => res.json())
        .then(data => {
            if(data.accedio)
                window.location.href = 'html/inicio.html';
            else{
                window.alert("Verifique sus credenciales!")
                window.location.href = 'index.html';
            }

        });




}