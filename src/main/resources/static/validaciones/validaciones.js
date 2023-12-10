jQuery.extend(jQuery.validator.messages, {
    required: "Campo requerido",
    remote: "Valor no v\u00e1lido",
    email: "Email no v\u00e1lido",
    url: "La URL no tiene el formato correcto.<br>Recuerde que la URL debe contener el protocolo http://",
    date: "Fecha no v\u00e1lida",
    dateISO: "Fecha no v\u00e1lida",
    number: "N\u00famero no v\u00e1lido",
    digits: "D\u00edgito no v\u00e1lido",
    creditcard: "N\u00famero de tarjeta no v\u00e1lido",
    equalTo: "Los valores no coinciden",
    accept: "Formato no v\u00e1lido",
    esValidoFormatoNit: "NIT no v\u00e1lido. Ej: 00000000-0",
    esValidoDOI: "DOI no v\u00e1lido",
    onlyNumberLetter: "Formato no v\u00e1lido",
    onlyNumberLetterAccent: "Formato no v\u00e1lido (S\u00F3lo se permiten letras y números)",
    onlyNumberLetterAccent_: "Formato no v\u00e1lido",
    step: jQuery.validator.format("Valor debe ser m\u00FAltiplo de {0}"),
    maxlength: jQuery.validator.format("Longitud m\u00e1xima de {0} caracteres"),
    minlength: jQuery.validator.format("Longitud m\u00ednima de {0} caracteres."),
    rangelength: jQuery.validator.format("Longitud entre {0} y {1} caracteres"),
    range: jQuery.validator.format("Valor entre {0} y {1}"),
    max: jQuery.validator.format("Valor menor o igual a {0}"),
    min: jQuery.validator.format("Valor mayor o igual a {0}"),
    anoMayor: jQuery.validator.format("El año {0} no puede ser mayor al {1}"),
    esValidoIsbn: jQuery.validator.format("El ISBN no es v\u00e1lido")
});
$.validator.addMethod("esValidoFormatoNit", function (nit, element) {
    if (nit === null) {
        return false;
    }
    var patron = /^\d{9,}-\d{1}$/;
    if (!patron.test(nit)) {
        return patron.test(nit);
    }
    nit = nit.split("-");
    if (nit[0] === null || nit[1] === null) {
        return false;
    }
    var digitoCalculado = calcularDigitoVerificador(nit[0]);
    return Number(nit[1].trim()) === digitoCalculado;
});

function esValidoNit(nit, digito) {
    if (nit === null || digito === null) {
        return false;
    }
    var digitoCalculado = calcularDigitoVerificador(nit);
    return Number(digito.trim()) === digitoCalculado;
}

$.validator.addMethod("esValidoDOI", function (doi, element) {

    if (doi === null || doi === "") {
        return true;
    }

    var patron = /^(10[.][0-9]{4,}(?:[.][0-9]+)*\/(?:(?!["&\'<>])\S)+)$/g;
    if (!patron.test(doi)) {
        return patron.test(doi);
    }
    return true;
});

$.validator.methods.email = function (value, element) {
    return this.optional(element) || /[a-zA-Z0-9/d=#$%@_ -]+@[a-z0-9-]+\.[a-z]+/.test(value);
};

$.validator.addMethod("onlyNumberLetter", function (value, element) {
    return this.optional(element) || /^[a-z0-9]+$/i.test(value);
});

$.validator.addMethod("onlyNumberLetterAccent_", function (value, element) {
    return this.optional(element) || /^[a-zA-ZÁÉÍÓÚÑ\u00E0-\u00FC0-9\-\s]+$/.test(value);
});

/**
 * Se aceptan numeros, letras, letras con acento, comilla doble, comilla sencilla, ampersand, parentesis,
 * asterisco, signo mas, coma, punto, guion, punto y barra inclinada a la derecha, dos puntos, punto y coma,
 * menor que, mayor que, igual, signos de interrogacion abierto y cerrado y los unicode desde 00BF hasta 017F
 * que incluyen letras mayusculas y minusculas con tilde, virgulina, etc.
 * @param {string} value Valor a evaluar
 * @param {string} element 
 */
$.validator.addMethod("onlyNumberLetterAccent", function (value, element) {
    return this.optional(element) || /^[a-zA-Z0-9\u00BF-\u017F\u0022\u0026-\u002F\u003A-\u003F\s]+$/.test(value);
});
function calcularDigitoVerificador(nit) {
    var nitArray = Array.from(nit);
    var aux = nitArray.length - 1;
    var valorCalculado = 0;
    for (var i = 0; i <= aux; i++) {
        switch (i) {
            case 0:
                valorCalculado += 3 * Number(nitArray[aux - 0].trim());
                break;
            case 1:
                valorCalculado += 7 * Number(nitArray[aux - 1].trim());
                break;
            case 2:
                valorCalculado += 13 * Number(nitArray[aux - 2].trim());
                break;
            case 3:
                valorCalculado += 17 * Number(nitArray[aux - 3].trim());
                break;
            case 4:
                valorCalculado += 19 * Number(nitArray[aux - 4].trim());
                break;
            case 5:
                valorCalculado += 23 * Number(nitArray[aux - 5].trim());
                break;
            case 6:
                valorCalculado += 29 * Number(nitArray[aux - 6].trim());
                break;
            case 7:
                valorCalculado += 37 * Number(nitArray[aux - 7].trim());
                break;
            case 8:
                valorCalculado += 41 * Number(nitArray[aux - 8].trim());
                break;
            case 9:
                valorCalculado += 43 * Number(nitArray[aux - 9].trim());
                break;
            case 10:
                valorCalculado += 47 * Number(nitArray[aux - 10].trim());
                break;
            case 11:
                valorCalculado += 53 * Number(nitArray[aux - 11].trim());
                break;
            case 12:
                valorCalculado += 59 * Number(nitArray[aux - 12].trim());
                break;
            case 13:
                valorCalculado += 67 * Number(nitArray[aux - 13].trim());
                break;
            case 14:
                valorCalculado += 71 * Number(nitArray[aux - 14].trim());
                break;
        }
    }

    var modulo = valorCalculado % 11;
    if (valorCalculado % 11 > 1) {
        modulo = 11 - modulo;
    }

    return modulo;
}

$.validator.addMethod("esValidoIsbn", function (value, element) {
    if (value === null) {
        return false;
    }
    return validarIsbn(value);
});

function validarIsbn(isbn) {
    var isbnA = isbn.replace(/-|\s/g, "");
    var isbnArray = Array.from(isbnA);
    var resultadoSuma = 0;
    var residuo = 0;
    var numeroVerificador = 0;
    if(isbnArray.length != 10){
        if(isbnArray.length != 13){
            return false;
        }
    }
    
    if (isbnArray.length == 10) {
        resultadoSuma = resultadoSuma + (isbnArray[0] * 1);
        resultadoSuma = resultadoSuma + (isbnArray[1] * 2);
        resultadoSuma = resultadoSuma + (isbnArray[2] * 3);
        resultadoSuma = resultadoSuma + (isbnArray[3] * 4);
        resultadoSuma = resultadoSuma + (isbnArray[4] * 5);
        resultadoSuma = resultadoSuma + (isbnArray[5] * 6);
        resultadoSuma = resultadoSuma + (isbnArray[6] * 7);
        resultadoSuma = resultadoSuma + (isbnArray[7] * 8);
        resultadoSuma = resultadoSuma + (isbnArray[8] * 9);
        residuo = resultadoSuma % 11;
        if(isbnArray[9] == "X" || isbnArray[9]=="x" || isbnArray[9]==10){
            if(residuo == 10){
                return true;
            }else{
                return false;
            }
        }
        return residuo === Number(isbnArray[9].trim());
    }
    if (isbnArray.length == 13) {
        resultadoSuma = resultadoSuma + (isbnArray[0] * 1);
        resultadoSuma = resultadoSuma + (isbnArray[1] * 3);
        resultadoSuma = resultadoSuma + (isbnArray[2] * 1);
        resultadoSuma = resultadoSuma + (isbnArray[3] * 3);
        resultadoSuma = resultadoSuma + (isbnArray[4] * 1);
        resultadoSuma = resultadoSuma + (isbnArray[5] * 3);
        resultadoSuma = resultadoSuma + (isbnArray[6] * 1);
        resultadoSuma = resultadoSuma + (isbnArray[7] * 3);
        resultadoSuma = resultadoSuma + (isbnArray[8] * 1);
        resultadoSuma = resultadoSuma + (isbnArray[9] * 3);
        resultadoSuma = resultadoSuma + (isbnArray[10] * 1);
        resultadoSuma = resultadoSuma + (isbnArray[11] * 3);
        residuo = resultadoSuma % 10;
        if(residuo != 0){
         numeroVerificador = 10 - residuo;   
        }else{
            numeroVerificador = residuo;
        }
        return numeroVerificador === Number(isbnArray[12].trim());
    }
    return true;

}