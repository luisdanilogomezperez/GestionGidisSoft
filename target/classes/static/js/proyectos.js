async function listarProyectos(){
    const request = await fetch('/api/v1/proyectoInvestigacion', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    })
    const res = await request.json();
    if(!res){
        document.getElementById("tbody").innerHTML = "<td>No hay proyectos disponibles</td>"
    }
    else{
        console.log(res)
        document.getElementById("tbody").innerHTML = ""
        for (let i = 0; i < res.length; i++){
            let titulo = res[i].titulo
            let anioInicio = res[i].anioInicio
            let id = res[i].id

            document.getElementById("tbody").innerHTML +=
                ` <tr>
        <td>${titulo}</td>
        <td>${anioInicio}</td>
        <td>
          <div class="dropdown show">
            <a class="btn dropdown-toggle" src="img/puntos.png" href="#" role="button" id="desplegable"
              data-toggle="dropdown" aria-haspopup="true">
              <img src="../img/puntos.png" class="align-right" height="30px" width="30px">
            </a>



            <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item" onclick="verPro(${id})" data-toggle="modal" style="background-color:#FFFFFF" id="btn-abrir-popup3"
                >Ver</a>
              <a class="dropdown-item" onclick="eliminarProyecto('${id}')" data-toggle="modal" style="background-color:#FFFFFF"
                >Eliminar</a>
              <a class="dropdown-item" onclick="editarPro(${id})" data-toggle="modal" style="background-color:#FFFFFF" id="btn-abrir-popup2"
                >Editar</a>
              
            </div>
          </div>
        </td>

      </tr>
        `
        }

    }

}

function verPro(id){
    window.location.href=`verProyecto.html?id=${id}`
}

function editarPro(id){
    window.location.href=`editarProyecto.html?id=${id}`
}

listarProyectos()

async function registroProyecto() {
    let data = {};

    data.titulo = document.getElementById('titulo').value;
    data.tipoProyecto = document.getElementById('tipoProyecto').value;
    data.mesInicio = document.getElementById('mesInicio').value;
    data.mesFin = document.getElementById('mesFin').value;
    data.anioInicio = document.getElementById('anioInicio').value;
    data.anioFin = document.getElementById('anioFin').value;
    data.financiado = document.getElementById('financiado').checked;
    data.fuenteFinanciacion = document.getElementById('fuenteFinanciacion').value;
    data.ambito = document.getElementById('ambito').value;
    data.resumen = document.getElementById('resumen').value;
    data.instituciones = document.getElementById('instituciones').value;

    console.log(data.financiado)


    const request = await fetch('/api/v1/proyectoInvestigacion/guardar', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(() => {
                alert("Proyecto Registrado Correctamente");
                window.location.href = 'viewProyectos.html';
    }).catch( err => {
            alert("Error de registro.");
        });
}

async function eliminarProyecto(id) {
    const request = await fetch(`/api/v1/proyectoInvestigacion/borrar/${id}`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    })

    window.alert("Se eliminó correctamente")
    window.location.reload()
    listarArticulos()

}