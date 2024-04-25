/*
async function fetchEventos() {
    const response = await fetch('evento/verEventos', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    return await response.json();
}

function crearEventoHTML(evento) {
    return `<tr>
            <td>${evento.nombreEvento}</td>
            <td>${evento.fechaInicio}</td>
            <td>${evento.descripcion}</td>
            <td>
              <button type="button" class="btn btn-outline-info" onclick="editarEvento('${evento.idEvento}')">Editar</button>
              <button type="button" class="btn btn-outline-danger" onclick="eliminarEvento('${evento.idEvento}')">Eliminar</button>
            </td>
          </tr>`;
}

function editarEvento(idEvento) {
    window.location.href = `./editarEvento.html?id=${idEvento}`;
}

function renderizarEventos(eventos) {
    document.querySelector("#eventos tbody").innerHTML = eventos.map(crearEventoHTML).join('');
}

async function cargarEventos() {
    try {
        const eventos = await fetchEventos();
        renderizarEventos(eventos);
    } catch (error) {
        console.error('Error loading products:', error);
    }
}

async function eliminarEvento(id){
    if (confirm('Está seguro de eliminar este evento?')){
        const request = await fetch('evento/eliminar/'+ id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
        location.reload();
    }
}

cargarEventos().then(r => {});
*/

$(document).ready(function () {
    $('#tab_logic').DataTable();
});