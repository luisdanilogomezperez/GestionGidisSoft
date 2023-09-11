async function listarLibros(){
    const request = await fetch('/api/v1/libro', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    })
    const res = await request.json();
    if(!res){
        document.getElementById("tbody").innerHTML = "<td>No hay libros disponibles</td>"
    }
    else{
        console.log(res)
        document.getElementById("tbody").innerHTML = ""
        for (let i = 0; i < res.length; i++){
            let titulo = res[i].titulo
            let lugarPublicacion = res[i].lugarPublicacion
            let isbn = res[i].isbn
            let id = res[i].id

            document.getElementById("tbody").innerHTML +=
                ` <tr>
        <td>${titulo}</td>
        <td>${lugarPublicacion}</td>
        <td>${isbn}</td>
        <td>
          <div class="dropdown show">
            <a class="btn dropdown-toggle" src="img/puntos.png" href="#" role="button" id="desplegable"
              data-toggle="dropdown" aria-haspopup="true">
              <img src="../img/puntos.png" class="align-right" height="30px" width="30px">
            </a>



            <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" onclick="verLib(${id})" data-toggle="modal" style="background-color:#FFFFFF" id="btn-abrir-popup3"
                href="verLibro.html?id=${id}">Ver</a>
              <a class="dropdown-item" onclick="eliminarLibro('${id}')" data-toggle="modal" style="background-color:#FFFFFF"
                >Eliminar</a>
              <a class="dropdown-item" onclick="editarLib(${id})" data-toggle="modal" style="background-color:#FFFFFF" id="btn-abrir-popup2"
                href="editarLibro.html">Editar</a>
              
            </div>
          </div>
        </td>

      </tr>
        `
        }

    }

}

function verLib(id){
    window.location.href=`verLibro.html?id=${id}`
}

function editarLib(id){
    window.location.href=`editarLibro.html?id=${id}`
}

listarLibros()


async function registroLibro() {
    let data = {};

    data.titulo = document.getElementById('titulo').value;
    data.isbn = document.getElementById('isbn').value;
    data.lugarPublicacion = document.getElementById('lugarPublicacion').value;
    data.editorial = document.getElementById('editorial').value;
    data.disciplina = document.getElementById('disciplina').value;
    data.certificadoCreditos = document.getElementById('certificadoCreditos').value;
    data.certificadoInstitucionAvala = document.getElementById('certificadoInstitucionAvala').value;


    const request = await fetch('/api/v1/libro/guardar', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(() => {
                alert("Libro Registrado Correctamente");
                window.location.href="viewLibros.html"
                }).catch( err => {
            alert("Error de registro.");
        });
}

async function eliminarLibro(id) {
    const request = await fetch(`/api/v1/libro/borrar/${id}`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    })

    window.alert("Se eliminó correctamente")
    listarLibros()

}

