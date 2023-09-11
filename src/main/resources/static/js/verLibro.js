async function verLibro(){
    const datosURL = window.location.search;
    const urlParams = new URLSearchParams(datosURL);
    let id = urlParams.get('id');
    const request = await fetch(`/api/v1/libro/${id}`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    })
    const res = await request.json();
    if(!res){
        document.getElementById("libro").innerHTML = "<td>No hay libros disponibles</td>"
    }
    else{
        console.log(res)
        document.getElementById("libro").innerHTML = ""
        let titulo = res.titulo
        let lugarPublicacion = res.lugarPublicacion
        let editorial = res.editorial
        let disciplina = res.disciplina
        let isbn = res.isbn
        let certificadoCreditos = res.certificadoCreditos
        let certificadoInstitucionAvala = res.certificadoInstitucionAvala

        document.getElementById("libro").innerHTML +=
            ` <div class="form-group" id="prueba">
            <label for="input"><b>Titulo del Proyecto</b></label>
            <p id="titulo" name="titulo">${titulo}</p>
        </div><br>
        <div class="form-group" id="prueba">
            <label for="input"><b>Lugar de Publicación</b></label>
            <p id="lugarPublicacion" name="lugarPublicacion">${lugarPublicacion}</p>
        </div><br>
        <div class="row">
        <div class="form-group col-sm-4"id="prueba">
            <label for="input"><b>Editorial</b></label>
            <p id="editorial" name="editorial">${editorial}</p>
        </div>
        <div class="form-group col-sm-4"id="prueba">
            <label for="input"><b>Disciplina</b></label>
            <p id="disciplina" name="disciplina">${disciplina}</p>
        </div>
        </div><br>
        
        <div class="row">
        <div class="form-group col-sm-3"id="prueba">
            <label for="input"><b>ISBN </b></label>
            <p id="isbn" name="isbn">${isbn}</p>
        </div>
        <div class="form-group col-sm-3"id="prueba">
            <label for="input"><b>Certificado Créditos</b></label>
            <p id="certificadoCreditos" name="certificadoCreditos">${certificadoCreditos}</p>
        </div>
        <div class="form-group col-sm-3"id="prueba">
            <label for="input"><b>Certificado Institución que avala</b></label>
            <p id="certificadoInstitucionAvala" name="certificadoInstitucionAvala">${certificadoInstitucionAvala}</p>
        </div>
        </div><br>
       
        <button type="submit" class="btn btn-primary" value="Ingresar" id="ingresar" >Descargar</button>
        <br><br>       
        `
    }

}
verLibro()