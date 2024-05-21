var passwordInput = document.getElementById('clave');
var confirmPasswordInput = document.getElementById('verificarClave');
var progressBar = document.getElementById('password-strength');
var passwordMatchMessage = document.getElementById('passwordMatchMessage');
var submitButton = document.getElementById('submitButton');

// Agregar eventos de escucha para validar la contraseña al cargar la página y cuando se cambien los valores de los campos
document.addEventListener('DOMContentLoaded', validatePassword);
passwordInput.addEventListener('input', validatePassword);
confirmPasswordInput.addEventListener('input', validatePassword);

// Función para validar la contraseña
function validatePassword() {
    var password = passwordInput.value;
    var confirmPassword = confirmPasswordInput.value;

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
    progressBar.style.width = strength + '%';
    progressBar.style.backgroundColor = getStrengthColor(strength);
    submitButton.disabled = true;
    // Verificar si las contraseñas coinciden y mostrar mensaje correspondiente
    if(confirmPassword.length > 0) {
        if (password === confirmPassword) {
            passwordMatchMessage.textContent = 'Las contraseñas coinciden';
            passwordMatchMessage.style.color = 'green'; // Cambiar el color del mensaje a verde
            submitButton.disabled = false;
        } else {
            passwordMatchMessage.textContent = 'Las contraseñas no coinciden';
            passwordMatchMessage.style.color = 'red'; // Mantener el color del mensaje en rojo
            submitButton.disabled = true;
        }
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
    if (strength < 30) {
        return 'red';
    } else if (strength > 31 && strength < 60) {
        return 'orange';
    } else {
        return 'green';
    }
}
