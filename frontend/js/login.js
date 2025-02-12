// Gestione dell'evento submit del form di login
document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault(); // Previene il comportamento di submit predefinito
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Simulazione di una chiamata al backend per la validazione delle credenziali
    // TODO: Sostituire con una chiamata reale al backend
    fakeLogin(username, password);
});

function fakeLogin(username, password) {
    // Simula un ritardo (ad esempio 1 secondo) per imitare una chiamata al backend
    setTimeout(function() {
        // Esempio di validazione: login riuscito se username e password sono "admin"
        if (username === 'admin' && password === 'admin') {
            // Creazione dei dati utente, inclusi dati aggiuntivi
            const userData = {
                username: username,
                name: "Mario Rossi",
                email: "mario.rossi@example.com",
                additionalData: {
                    lastLogin: "2025-02-11 10:30:00",
                    role: "Administrator"
                }
            };
            // Salvataggio dei dati nel localStorage
            localStorage.setItem('userData', JSON.stringify(userData));
            // Redirect alla dashboard
            window.location.href = 'dashboard.html';
        } else {
            // Mostra il messaggio di errore in caso di credenziali non valide
            document.getElementById('errorMessage').style.display = 'block';
        }
    }, 1000);
}
