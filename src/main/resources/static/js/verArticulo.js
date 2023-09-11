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
        document.getElementById("articulo").innerHTML = "<td>No hay artículos disponibles</td>"
    }
    else{
        console.log(res)
        document.getElementById("articulo").innerHTML = ""
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


            document.getElementById("articulo").innerHTML +=
                ` <div class="form-group" id="prueba">
            <label for="input"><b>Titulo del Articulo</b></label>
            <p id="titulo" name="titulo">${titulo}</p>
        </div>
        <div class="form-group" id="prueba">
            <label for="input"><b>Coautores </b></label>
            <p id="coautores" name="coautores">${coautores}</p>
        </div>
        <div class="row">
        <div class="form-group col-sm-4"id="prueba">
            <label for="input"><b>Tipo Articulo </b></label>
            <p id="tipoArticulo" name="tipoArticulo">${tipoArticulo}</p>
        </div>
        <div class="form-group col-sm-4"id="prueba">
            <label for="input"><b>Ciudad </b></label>
            <p id="ciudad" name="ciudad">${ciudad}</p>
        </div>
        </div>
        
        <div class="row">
        <div class="form-group col-sm-3"id="prueba">
            <label for="input"><b>Nombre Revista </b></label>
            <p id="nombreRevista" name="nombreRevista">${nombreRevista}</p>
        </div>
        <div class="form-group col-sm-3"id="prueba">
            <label for="input"><b>Fasciculo Revista</b></label>
            <p id="fasciculoRevista" name="fasciculoRevista">${fasciculoRevista}</p>
        </div>
        <div class="form-group col-sm-3"id="prueba">
            <label for="input"><b>Serie Revista </b></label>
            <p id="serieRevista" name="serieRevista">${serieRevista}</p>
        </div>
        <div class="form-group col-sm-3"id="prueba">
            <label for="input"><b>Volumen </b></label>
            <p id="volumen" name="volumen">${volumen}</p>
        </div>
        </div>
        
        <div class="row">
        <div class="form-group col-sm-4"id="prueba">
            <label for="input"><b>Idioma </b></label>
            <p id="idioma" name="idioma">${idioma}</p>
        </div>
        <div class="form-group col-sm-4"id="prueba">
            <label for="input"><b>Medio Divulgación </b></label>
            <p id="medioDivulgacion" name="medioDivulgacion">${medioDivulgacion}</p>            
        </div>
        <div class="form-group col-sm-4"id="prueba">
            <label for="input"><b>DOI </b></label>
            <p id="digitalObjectIdentifierDOI" name="digitalObjectIdentifierDOI">${digitalObjectIdentifierDOI}</p>            
        </div>
        </div>
        
        <div class="row">
        <div class="form-group col-sm-4"id="prueba">
            <label for="input"><b>Página Inicial </b></label>
            <p id="paginaInicial" name="paginaInicial">${paginaInicial}</p>            
        </div>
        <div class="form-group col-sm-4"id="prueba">
            <label for="input"><b>Página Final </b></label>
            <p id="paginaFinal" name="paginaFinal">${paginaFinal}</p>            
        </div>
        </div>
        <br>
        <button type="submit" class="btn btn-primary" value="Ingresar" id="ingresar" >Descargar</button>
        <br><br>       
        `
        }

}
verArticulo()