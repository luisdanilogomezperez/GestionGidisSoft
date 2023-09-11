async function verProyecto(){
    const datosURL = window.location.search;
    const urlParams = new URLSearchParams(datosURL);
    let id = urlParams.get('id');
    const request = await fetch(`/api/v1/proyectoInvestigacion/${id}`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    })
    const res = await request.json();
    if(!res){
        document.getElementById("editProyecto").innerHTML = "<td>No hay proyectos disponibles</td>"
    }
    else{
        console.log(res)
        document.getElementById("editProyecto").innerHTML = ""
        let id = res.id
        let titulo = res.titulo
        let tipoProyecto = res.tipoProyecto
        let mesInicio = res.mesInicio
        let mesFin = res.mesFin
        let anioInicio = res.anioInicio
        let anioFin = res.anioFin
        let financiado = res.financiado
        let fuenteFinanciacion = res.fuenteFinanciacion
        let ambito = res.ambito
        let resumen = res.resumen
        let instituciones = res.instituciones


        document.getElementById("editProyecto").innerHTML +=
            ` <form onsubmit="event.preventDefault(); editarProyecto(${id})">
            <div class="form-group">
                <label for="input">Titulo</label>
                <input type="text" class="form-control" value="${titulo}" name="titulo" id="titulo" placeholder="Titulo" required>
            </div><br>
            <div class="row">
                <div class="col-xd-12 col-sm-7">
                    <div class="form-group">
                        <label for="input">Tipo Proyecto</label>
                        <input type="text" class="form-control" value="${tipoProyecto}" name="tipoProyecto" id="tipoProyecto" placeholder="Tipo Proyecto"
                               required>
                    </div>
                </div>
            </div><br>
            <div class="row">
                <div class="col-xd-12 col-sm-2">
                <div class="form-group">
                    <label for="input">Mes Inicio</label>
                    <input type="text" class="form-control col-sm-4" value="${mesInicio}" name="mesInicio" id="mesInicio" placeholder="Mes Inicio" required>
                </div></div><br>

                    <div class="col-xd-12 col-sm-2">
                <div class="form-group">
                    <label for="input">Mes Fin</label>
                    <input type="text" class="form-control col-sm-4" value="${mesFin}" name="mesFin" id="mesFin" placeholder="Mes Fin" required>
                </div></div>
                        <div class="col-sm-2"><br><br>
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="${financiado}" id="financiado">
                                <label class="form-check-label" for="financiado">
                                    Financiado
                                </label>
                            </div>
                        </div>

                    <br>

            </div><br>
            <div class="row">
                <div class="col-xd-12 col-sm-2">
                    <div class="form-group">
                        <label for="input">Año inicio</label>
                        <input type="text" class="form-control" value="${anioInicio}" name="anioInicio" id="anioInicio" placeholder="Año inicio" required>
                    </div>
                </div>

                <div class="col-xd-12 col-sm-2">
                    <div class="form-group">
                        <label for="input">Año Fin</label>
                        <input type="text" class="form-control" value="${anioFin}" name="anioFin" id="anioFin" placeholder="Año fin" required>
                    </div>
                </div>



                <div class="col-sm-6">
                    <div class="form-group">
                        <label for="input">Fuente de Financiación</label>
                        <input type="text"  value="${fuenteFinanciacion}" name="fuenteFinanciacion" class="form-control" id="fuenteFinanciacion" placeholder="Fuente de financiación" required>
                    </div>
                </div>

            </div><br>


            <div class="form-group">
                <label for="input">Resumen</label>
                <input type="text" class="form-control" value="${resumen}" name="resumen" id="resumen" placeholder="Resumen" required>
                </div><br>
            <div class="row">
                <div class="col-xd-12 col-sm-5">
                    <div class="form-group">
                        <label for="input">Ámbito</label>
                        <input type="text" class="form-control" value="${ambito}" name="ambito" id="ambito" placeholder="Ámbito">
                    </div>
                </div>

                <div class="col-sm-5">
                    <div class="form-group">
                        <label for="input">Instituciones</label>
                        <input type="text" name="instituciones" value="${instituciones}" class="form-control" id="instituciones" placeholder="Instituciones" required>
                    </div>
                </div>

            </div>



            <!--Boton-->
            <br>
            <button type="submit" class="btn btn-primary" value="registrar" id="guardar">Guardar</button>
        </form>
        `
    }

}
verProyecto()

async function editarProyecto(id) {
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

    const request = await fetch(`/api/v1/proyectoInvestigacion/editar/${id}`, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(() => {
                alert("Proyecto Actualizado");
                window.location.href = './viewProyectos.html'

        })
}