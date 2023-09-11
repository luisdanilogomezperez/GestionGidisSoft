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
        document.getElementById("proyecto").innerHTML = "<td>No hay proyectos disponibles</td>"
    }
    else{
        console.log(res)
        document.getElementById("proyecto").innerHTML = ""
        let titulo = res.titulo
        let tipoProyecto = res.tipoProyecto
        let mesInicio = res.mesInicio
        let mesFin = res.mesFin
        let anioInicio = res.anioInicio
        let anioFin = res.anioFin
        let financiado = (res.financiado) ? "Sí" : "No"
        let fuenteFinanciacion = res.fuenteFinanciacion
        let ambito = res.ambito
        let resumen = res.resumen
        let instituciones = res.instituciones


        document.getElementById("proyecto").innerHTML +=
            ` <div class="form-group" id="prueba">
            <label for="input"><b>Titulo del Proyecto</b></label>
            <p id="titulo" name="titulo">${titulo}</p>
        </div><br>
        <div class="form-group" id="prueba">
            <label for="input"><b>Tipo Proyecto </b></label>
            <p id="tipoProyecto" name="tipoProyecto">${tipoProyecto}</p>
        </div><br>
        <div class="row">
        <div class="form-group col-sm-4"id="prueba">
            <label for="input"><b>Mes Inicio </b></label>
            <p id="mesInicio" name="mesInicio">${mesInicio}</p>
        </div>
        <div class="form-group col-sm-4"id="prueba">
            <label for="input"><b>Mes Fin </b></label>
            <p id="mesFin" name="mesFin">${mesFin}</p>
        </div>
        </div><br>
        
        <div class="row">
        <div class="form-group col-sm-3"id="prueba">
            <label for="input"><b>Año Inicio </b></label>
            <p id="anioInicio" name="anioInicio">${anioInicio}</p>
        </div>
        <div class="form-group col-sm-3"id="prueba">
            <label for="input"><b>Año fin</b></label>
            <p id="anioFin" name="anioFin">${anioFin}</p>
        </div>
        <div class="form-group col-sm-3"id="prueba">
            <label for="input"><b>Financiado</b></label>
            <p id="financiado" name="financiado">${financiado}</p>
        </div>
        <div class="form-group col-sm-3"id="prueba">
            <label for="input"><b>Fuente de Financiación</b></label>
            <p id="fuenteFinanciacion" name="fuenteFinanciacion">${fuenteFinanciacion}</p>
        </div>
        </div><br>
        
        <div class="row">
        <div class="form-group col-sm-4"id="prueba">
            <label for="input"><b>Ambito</b></label>
            <p id="ambito" name="ambito">${ambito}</p>
        </div>
        <div class="form-group col-sm-4"id="prueba">
            <label for="input"><b>Resumen</b></label>
            <p id="resumen" name="resumen">${resumen}</p>            
        </div>
        <div class="form-group col-sm-4"id="prueba">
            <label for="input"><b>Instituciones</b></label>
            <p id="instituciones" name="instituciones">${instituciones}</p>            
        </div>
        </div><br>
        <button type="submit" class="btn btn-primary" value="Ingresar" id="ingresar" >Descargar</button>
        <br><br>       
        `
    }

}
verProyecto()