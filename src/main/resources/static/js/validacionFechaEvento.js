document.addEventListener("DOMContentLoaded", function() {
    var fechaInicio = document.getElementById("fechaInicio");
    var fechaFin = document.getElementById("fechaFin");
    var guardarBtn = document.getElementById("guardar");

    function validateDates() {
        var inicio = new Date(fechaInicio.value);
        var fin = new Date(fechaFin.value);

        if (inicio && fin && inicio >= fin) {
            alert("La fecha Fin debe ser mayor a la fecha Inicio.");
            guardarBtn.disabled = true;
        } else {
            guardarBtn.disabled = false;
        }
    }

    fechaInicio.addEventListener("change", validateDates);
    fechaFin.addEventListener("change", validateDates);
});