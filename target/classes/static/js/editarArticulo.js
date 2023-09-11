async function verArticulo(){
    const datosURL = window.location.search;
    const urlParams = new URLSearchParams(datosURL);
    let id = urlParams.get('id');
    const request = await fetch(`/api/v1/articulo/${id}`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    })
    const res = await request.json();
    if(!res){
        document.getElementById("editArticulo").innerHTML = "<td>No hay artículos disponibles</td>"
    }
    else{
        console.log(res)
        document.getElementById("editArticulo").innerHTML = ""
        let id = res.id
        let titulo = res.titulo
        let paginaInicial = res.paginaInicial
        let paginaFinal = res.paginaFinal
        let nombreRevista = res.nombreRevista
        let tipoArticulo = res.tipoArticulo
        let volumen = res.volumen
        let fasciculoRevista = res.fasciculoRevista
        let serieRevista = res.serieRevista
        let ciudad = res.ciudad
        let digitalObjectIdentifierDOI = res.digitalObjectIdentifierDOI
        let medioDivulgacion = res.medioDivulgacion
        let coautores = res.coautores
        let idioma = res.idioma


        document.getElementById("editArticulo").innerHTML +=
            ` <form onsubmit="event.preventDefault(); editarArticulo(${id})">
            <div class="form-group">
                <label for="input">Titulo</label>
                <input type="text" value="${titulo}" class="form-control" name="titulo" id="titulo" placeholder="Titulo" required>
            </div><br>
            <div class="row">
                <div class="col-xd-12 col-sm-7">
                    <div class="form-group">
                        <label for="input">Coautores</label>
                        <input type="text" value="${coautores}" class="form-control" name="coautores" id="coautores" placeholder="Coautores"
                            required>
                    </div>
                </div>

            </div><br>
            <div class="form-group">
                <label for="input">Nombre de Revista</label>
                <input type="text" value="${nombreRevista}" class="form-control" name="nombreRevista" id="nombreRevista" placeholder="Nombre de Revista" required>
            </div><br>
            <div class="row">
                <div class="col-xd-12 col-sm-4">
                    <div class="form-group">
                        <label for="input">Fasciculo de Revista</label>
                        <input type="text" value="${fasciculoRevista}" class="form-control" name="fasciculoRevista" id="fasciculoRevista" placeholder="Fasciculo de Revista" required>
                    </div>
                </div>

                <div class="col-sm-2">
                    <div class="form-group">
                        <label for="input">Ciudad</label>
                        <input type="text" value="${ciudad}" name="ciudad" class="form-control" id="ciudad" placeholder="Ciudad" required>
                    </div>
                </div>

                <div class="col-sm-4">
                    <div class="form-group">
                        <label for="input">Serie Revista</label>
                        <input type="text" value="${serieRevista}" class="form-control" name="serieRevista" id="serieRevista"
                            placeholder="Serie Revista" required>
                    </div>
                </div>

                <div class="col-sm-2">
                    <div class="form-group">
                        <label for="input">DOI</label>
                        <input type="text" value="${digitalObjectIdentifierDOI}" name="digitalObjectIdentifierDOI" class="form-control" id="digitalObjectIdentifierDOI" placeholder="DOI" required>
                    </div>
                </div>

            </div><br>

            <div class="form-group">
                <label for="input">Medio de divulgación</label>
                <input type="text" value="${medioDivulgacion}" class="form-control" name="medioDivulgacion" id="medioDivulgacion" placeholder="Medio de divulgación">
            </div><br>

            <div class="row">
                <div class="col-xd-12 col-sm-3">
                    <div class="form-group">
                        <label for="input">Idioma</label>
                        <input type="text" value="${idioma}" class="form-control" name="idioma" id="idioma" placeholder="Idioma" required>
                    </div>
                </div>

                <div class="col-sm-2">
                    <div class="form-group">
                        <label for="input">Volumen</label>
                        <input type="text" value="${volumen}" name="volumen" class="form-control" id="volumen" placeholder="Volumen" required>
                    </div>
                </div>

                <div class="col-sm-3">
                    <div class="form-group">
                        <label for="input">Tipo Artículo</label>
                        <input type="text" value="${tipoArticulo}" class="form-control" name="tipoArticulo" id="tipoArticulo"
                               placeholder="Tipo artículo" required>
                    </div>
                </div>

                <div class="col-sm-2">
                    <div class="form-group">
                        <label for="input">Página Inicial</label>
                        <input type="text" value="${paginaInicial}" name="paginaInicial" class="form-control" id="paginaInicial" placeholder="Página Inicial" required>
                    </div>
                </div>

                <div class="col-sm-2">
                    <div class="form-group">
                        <label for="input">Página Final</label>
                        <input type="text" value="${paginaFinal}" name="paginaFinal" class="form-control" id="paginaFinal" placeholder="Página Final" required>
                    </div>
                </div>

            </div>



            <!--Boton-->
            <br>
            <button type="submit" class="btn btn-primary" value="registrar" id="guardar">Editar</button>
            </form>
        `
    }

}
verArticulo()

async function editarArticulo(id) {
    let data = {};

    data.titulo = document.getElementById('titulo').value;
    data.paginaInicial = document.getElementById('paginaInicial').value;
    data.paginaFinal = document.getElementById('paginaFinal').value;
    data.nombreRevista = document.getElementById('nombreRevista').value;
    data.tipoArticulo = document.getElementById('tipoArticulo').value;
    data.volumen = document.getElementById('volumen').value;
    data.fasciculoRevista = document.getElementById('fasciculoRevista').value;
    data.serieRevista = document.getElementById('serieRevista').value;
    data.ciudad = document.getElementById('ciudad').value;
    data.digitalObjectIdentifierDOI = document.getElementById('digitalObjectIdentifierDOI').value;
    data.medioDivulgacion = document.getElementById('medioDivulgacion').value;
    data.coautores = document.getElementById('coautores').value;
    data.idioma = document.getElementById('idioma').value;

    const request = await fetch(`/api/v1/articulo/editar/${id}`, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(() => {
                alert("Usuario Actualizado");
                window.location.href = './viewArticulos.html'

        })
}