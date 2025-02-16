// Gestione dell'evento submit del form di login
document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault(); // Previene il comportamento di submit predefinito
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const requiredMessage = document.getElementById('requiredMessage');
    const errorMessage = document.getElementById('errorMessage');

    // Nascondi tutti i messaggi
    requiredMessage.style.display = 'none';
    errorMessage.style.display = 'none';

    // Validazione campi vuoti
    if (!username.trim() || !password.trim()) {
        requiredMessage.style.display = 'block';
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
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Credenziali non valide');
        }
        return response.json();
    }).then(data => {
        // Salvataggio dei dati utente nel localStorage
        localStorage.setItem('userData', JSON.stringify(data));
        // Redirect alla dashboard
        window.location.href = 'dashboard.html';
    }).catch(error => {
        console.error('Errore:', error);
        document.getElementById('errorMessage').style.display = 'block';
    });
});

document.querySelectorAll('#loginForm input').forEach(input => {
    input.addEventListener('input', () => {
        document.getElementById('requiredMessage').style.display = 'none';
        document.getElementById('errorMessage').style.display = 'none';
    });
});