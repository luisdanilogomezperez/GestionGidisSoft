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
        document.getElementById("editLibro").innerHTML = "<td>No hay libros disponibles</td>"
    }
    else{
        console.log(res)
        document.getElementById("editLibro").innerHTML = ""
        let id = res.id
        let titulo = res.titulo
        let isbn = res.isbn
        let lugarPublicacion = res.lugarPublicacion
        let editorial = res.editorial
        let disciplina = res.disciplina
        let certificadoCreditos = res.certificadoCreditos
        let certificadoInstitucionAvala = res.certificadoInstitucionAvala


        document.getElementById("editLibro").innerHTML +=
            ` <form onsubmit="event.preventDefault(); editarLibro(${id})">
            <div class="form-group">
                <label for="input">Titulo</label>
                <input type="text" class="form-control" value="${titulo}" name="titulo" id="titulo" placeholder="Titulo" required>
            </div><br>
            <div class="row">
                <div class="col-xd-12 col-sm-7">
                    <div class="form-group">
                        <label for="input">Lugar de Publicación</label>
                        <input type="text" class="form-control" value="${lugarPublicacion}" name="lugarPublicacion" id="lugarPublicacion" placeholder="Lugar de Publicación"
                            required>
                    </div>
                </div>

            </div><br>
            <div class="form-group">
                <label for="input">Editorial</label>
                <input type="text" class="form-control" value="${editorial}" name="editorial" id="editorial" placeholder="Editorial" required>
            </div><br>
            <div class="row">
                <div class="col-xd-12 col-sm-4">
                    <div class="form-group">
                        <label for="input">Disciplina</label>
                        <input type="text" class="form-control" value="${disciplina}" name="disciplina" id="disciplina" placeholder="Disciplina" required>
                    </div>
                </div>

                <div class="col-sm-2">
                    <div class="form-group">
                        <label for="input">Certificado Creditos</label>
                        <input type="text" name="certificadoCreditos" value="${certificadoCreditos}" class="form-control" id="certificadoCreditos" placeholder="Certificado Creditos" required>
                    </div>
                </div>

                <div class="col-sm-4">
                    <div class="form-group">
                        <label for="input">Certificado Institución que avala</label>
                        <input type="text" class="form-control" value="${certificadoInstitucionAvala}" name="certificadoInstitucionAvala" id="certificadoInstitucionAvala"
                            placeholder="Certificado Institución que avala" required>
                    </div>
                </div>

                <div class="col-sm-2">
                    <div class="form-group">
                        <label for="input">ISBN</label>
                        <input type="text" name="isbn" value="${isbn}" class="form-control" id="isbn" placeholder="ISBN" required>
                    </div>
                </div>

            </div><br>




            <!--Boton-->
            <br>
            <button type="submit" class="btn btn-primary" value="registrar" id="guardar">Editar</button>
        </form>
        `
    }

}
verLibro()

async function editarLibro(id) {
    let data = {};

    data.titulo = document.getElementById('titulo').value;
    data.isbn = document.getElementById('isbn').value;
    data.lugarPublicacion = document.getElementById('lugarPublicacion').value;
    data.editorial = document.getElementById('editorial').value;
    data.disciplina = document.getElementById('disciplina').value;
    data.certificadoCreditos = document.getElementById('certificadoCreditos').value;
    data.certificadoInstitucionAvala = document.getElementById('certificadoInstitucionAvala').value;


    const request = await fetch(`/api/v1/libro/editar/${id}`, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(() => {
                alert("Libro Actualizado");
                window.location.href = './viewLibros.html'

        })
}