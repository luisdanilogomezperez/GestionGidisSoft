var passwordInput = document.getElementById('clave');
var progressBar = document.getElementById('password-strength');
var passwordMatchMessage = document.getElementById('passwordMatchMessage');
var guardarBtn = document.getElementById("btnRegistro");

document.addEventListener('DOMContentLoaded', validatePassword);
passwordInput.addEventListener('input', validatePassword);

// Función para validar la contraseña
function validatePassword() {
    var password = passwordInput.value
    // Verificar longitud de la contraseña y habilitar/deshabilitar el botón
    if (password.length >= 8) {
        progressBar.style.width = '100%';
        progressBar.style.backgroundColor = 'green';
    } else {
        progressBar.style.width = '0';
        progressBar.style.backgroundColor = 'red';
    }
    // Calcular la fuerza de la contraseña y actualizar la barra de progreso
        var strength = calculatePasswordStrength(password);
        progressBar.style.display = 'block';
        progressBar.style.width = strength + '%';
        progressBar.style.backgroundColor = getStrengthColor(strength);
    // Verificar contraseñas mostrar mensaje correspondiente
    if(password.length > 0) {
        if (password.length < 8) {
            passwordMatchMessage.textContent = 'Minimo 8 caracteres (X)';
            passwordMatchMessage.style.color = 'red'; // Cambiar el color del mensaje a verde
            guardarBtn.disabled = true;
        } else {
            passwordMatchMessage.textContent = 'Minimo 8 caracteres (OK)';
            passwordMatchMessage.style.color = 'green'; // Mantener el color del mensaje en rojo
            guardarBtn.disabled = false;
        }
    } else {
         passwordMatchMessage.textContent = "";
    }
}

function calculatePasswordStrength(password) {
    var score = 0;

    // Verificar la longitud de la contraseña
    if (password.length >= 8) {
        score += 15;
    }

    // Verificar si la contraseña contiene letras minúsculas, mayúsculas, números y caracteres especiales
    if (password.match(/[a-z]/)) {
        score += 15;
    }
    if (password.match(/[A-Z]/)) {
        score += 15;
    }
    if (password.match(/[0-9]/)) {
        score += 15;
    }
    if (password.match(/[!@#$%^&*()_+{}[\]:;<>,.?/\\-]/)) {
        score += 15;
    }
    return score;
}

function getStrengthColor(strength) {
    if (strength <= 30) {
        return 'red';
    } else if (strength >= 31 && strength <= 60) {
        return 'orange';
    } else if(strength >= 61){
        return 'green';
    }
}