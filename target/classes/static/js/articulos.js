async function listarArticulos(){
    let data = {};
    const request = await fetch('/api/v1/articulo', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    })
            const res = await request.json();
            if(!res){
                document.getElementById("tbody").innerHTML = "<td>No hay artículos disponibles</td>"
            }
            else{
                console.log(res)
                document.getElementById("tbody").innerHTML = ""
                for (let i = 0; i < res.length; i++){
                    let titulo = res[i].titulo
                    let ciudad = res[i].ciudad
                    let coautores = res[i].coautores
                    let digitalObjectIdentifierDOI = res[i].digitalObjectIdentifierDOI
                    let id = res[i].id

                    document.getElementById("tbody").innerHTML +=
                        ` <tr>
        <td>${titulo}</td>
        <td>${ciudad}</td>
        <td>${coautores}</td>
        <td>${digitalObjectIdentifierDOI}</td>
        <td>
          <div class="dropdown show">
            <a class="btn dropdown-toggle" src="img/puntos.png" href="#" role="button" id="desplegable"
              data-toggle="dropdown" aria-haspopup="true">
              <img src="../img/puntos.png" class="align-right" height="30px" width="30px">
            </a>



            <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" onclick="verArt(${id})" data-toggle="modal" style="background-color:#FFFFFF" id="btn-abrir-popup3"
                href="verArticulo.html?id=${id}">Ver</a>
              <a class="dropdown-item" onclick="eliminarArticulo('${id}')" data-toggle="modal" style="background-color:#FFFFFF"
                >Eliminar</a>
              <a class="dropdown-item" onclick="editarArt(${id})" data-toggle="modal" style="background-color:#FFFFFF" id="btn-abrir-popup2"
                href="editarArticulo.html">Editar</a>
              
            </div>
          </div>
        </td>

      </tr>
        `
                }

            }

}

function verArt(id){
    window.location.href=`verArticulo.html?id=${id}`
}

function editarArt(id){
    window.location.href=`editarArticulo.html?id=${id}`
}

listarArticulos()


async function registroArticulo() {
    let data = {};

    data.titulo = document.getElementById('titulo').value;
    data.coautores = document.getElementById('coautores').value;
    data.nombreRevista = document.getElementById('nombreRevista').value;
    data.fasciculoRevista = document.getElementById('fasciculoRevista').value;
    data.ciudad = document.getElementById('ciudad').value;
    data.serieRevista = document.getElementById('serieRevista').value;
    data.digitalObjectIdentifierDOI = document.getElementById('digitalObjectIdentifierDOI').value;
    data.medioDivulgacion = document.getElementById('medioDivulgacion').value;
    data.idioma = document.getElementById('idioma').value;
    data.volumen = document.getElementById('volumen').value;
    data.tipoArticulo = document.getElementById('tipoArticulo').value;
    data.paginaInicial = document.getElementById('paginaInicial').value;
    data.paginaFinal = document.getElementById('paginaFinal').value;


    const request = await fetch('/api/v1/articulo/guardar', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(res => res.json())
        .then(data => {
            if (data.guardo) {
                alert("Artículo Registrado Correctamente");
                window.location.href = 'viewArticulos.html';
            }
            else
                alert("No se registró");
        }).catch( err => {
            alert("Error de registro.");
        });
}

async function eliminarArticulo(id) {
    const request = await fetch(`/api/v1/articulo/borrar/${id}`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    })

    window.alert("Se eliminó correctamente")
    listarArticulos()

}

