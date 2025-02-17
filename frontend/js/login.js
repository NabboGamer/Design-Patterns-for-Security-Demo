// Gestione dell'evento submit del form di login
document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault(); // Previene il comportamento di submit predefinito
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('errorMessage');

    // Nascondi tutti i messaggi
    errorMessage.style.display = 'none';

    // Validazione campi vuoti
    if (!username.trim() || !password.trim()) {
        errorMessage.textContent = 'Compila tutti i campi!';
        errorMessage.style.display = 'block';
        return;
    }

    // Chiamata al backend per il login
    fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            'username': username,
            'password': password
        })
    }).then(response => {
        if (response.status === 401 || response.status === 403) { // Errori specifici di autenticazione
            throw new Error('CREDENTIALS_ERROR');
        } else if (response.status >= 500) { // Errori server
            throw new Error('SERVER_ERROR');
        } else if (!response.ok) { // Altri errori generici
            throw new Error('GENERIC_ERROR');
        }
        return response.json();
    }).then(data => {
        // Salvataggio dei dati utente nel localStorage
        localStorage.setItem('userData', JSON.stringify(data));
        // Redirect alla dashboard
        window.location.href = 'dashboard.html';
    }).catch(error => {
        let errorType;

        if (error.message === 'Failed to fetch') {
            errorType = 'NETWORK_ERROR'; // Errore di rete nativo lanciato da fetch stesso
        } else {
            errorType = error.message; // Errori personalizzati lanciati da me
        }

        errorMessage.textContent = getErrorMessage(errorType);
        errorMessage.style.display = 'block';
        console.error(error);
    });
});

document.querySelectorAll('#loginForm input').forEach(input => {
    input.addEventListener('input', () => {
        document.getElementById('errorMessage').style.display = 'none';
    });
});

function getErrorMessage(errorType) {
    const messages = {
        'CREDENTIALS_ERROR': 'Credenziali non valide!',
        'SERVER_ERROR': 'Errore interno del server. Riprova più tardi.',
        'NETWORK_ERROR': 'Errore di connessione. Verifica la tua rete.',
        'GENERIC_ERROR': 'Si è verificato un errore. Riprova.',
        'default': 'Errore sconosciuto.'
    };
    return messages[errorType] || messages.default;
}