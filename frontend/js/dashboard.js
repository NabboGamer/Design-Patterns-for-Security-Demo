// dashboard.js
document.addEventListener('DOMContentLoaded', function() {
    // Controllo autenticazione
    const userData = JSON.parse(localStorage.getItem('userData'));

    /*if (!userData) {
        window.location.href = 'login.html';
        return;
    }*/

    // Mappa dei dati utente agli elementi DOM
    const elements = {
        fullName: document.getElementById('fullName'),
        username: document.getElementById('username'),
        userEmail: document.getElementById('userEmail'),
        lastLogin: document.getElementById('lastLogin'),
        userRole: document.getElementById('userRole')
    };

    // Popola i dati
    elements.fullName.textContent = userData.name || 'N/D';
    elements.username.textContent = userData.username || 'N/D';
    elements.userEmail.textContent = userData.email || 'N/D';
    elements.lastLogin.textContent = userData.additionalData?.lastLogin || 'N/D';
    elements.userRole.textContent = userData.additionalData?.role || 'N/D';

    // Formatta la data dell'ultimo accesso se necessario
    try {
        const lastLoginDate = new Date(elements.lastLogin.textContent);
        if (!isNaN(lastLoginDate)) {
            elements.lastLogin.textContent = lastLoginDate.toLocaleString('it-IT', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric',
                hour: '2-digit',
                minute: '2-digit'
            });
        }
    } catch (error) {
        console.error('Errore formattazione data:', error);
    }

    // Gestione logout
    /*document.getElementById('logoutButton').addEventListener('click', function() {
        localStorage.removeItem('userData');
        window.location.href = 'login.html';
    });*/

    // Controllo sessione (base)
    /*setInterval(() => {
        if (!localStorage.getItem('userData')) {
            window.location.href = 'login.html';
        }
    }, 300000); // 5 minuti*/

    // Animazione al caricamento
    document.querySelector('.dashboard-container').style.opacity = '0';
    setTimeout(() => {
        document.querySelector('.dashboard-container').style.transition = 'opacity 0.5s ease';
        document.querySelector('.dashboard-container').style.opacity = '1';
    }, 100);
});