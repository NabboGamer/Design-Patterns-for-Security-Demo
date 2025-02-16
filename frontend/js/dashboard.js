document.addEventListener('DOMContentLoaded', function() {
    // Controllo autenticazione
    const userData = JSON.parse(localStorage.getItem('userData'));

    if (!userData) {
        window.location.href = 'login.html';
        return;
    }

    // Mappa degli elementi DOM
    const elements = {
        userName: document.getElementById('userName'),
        userSurname: document.getElementById('userSurname'),
        userLevel: document.getElementById('userLevel'),
        userCompanyIdentificationCode: document.getElementById('userCompanyIdentificationCode'),
        userImage: document.getElementById('userImage')
    };

    // Popolazione dati utente
    elements.userName.textContent = userData.name || 'N/D';
    elements.userSurname.textContent = userData.surname || 'N/D';
    elements.userLevel.textContent = userData.level || 'N/D';
    elements.userCompanyIdentificationCode.textContent = userData.companyIdentificationCode || 'N/D';

    // Caricamento immagine profilo da base64
    if (userData.image) {
        // Costruisci l'URI dati per l'immagine
        elements.userImage.src = `data:${userData.imageMimeType};base64,${userData.image}`;
        elements.userImage.onerror = () => {
            elements.userImage.src = '../images/default-avatar.png'; // Fallback per errori di caricamento
        };
    } else {
        // Nessuna immagine disponibile
        elements.userImage.src = '../images/default-avatar.png';
    }

    // Generazione barcode
    if (userData.companyIdentificationCode) {
        JsBarcode("#barcode", userData.companyIdentificationCode, {
            format: "CODE128",         // Formato del barcode
            displayValue: false,       // Mostra il valore testuale
            fontSize: 16,              // Dimensione del testo
            lineColor: "#2c3e50",      // Colore delle linee
            width: 4,                  // Spessore delle linee
            height: 80                 // Altezza del barcode
        });
    }

    // Logout
    document.getElementById('logoutButton').addEventListener('click', function() {
        localStorage.removeItem('userData');
        window.location.href = 'login.html';
    });

    // Animazione di caricamento
    document.querySelector('.dashboard-container').style.opacity = '0';
    setTimeout(() => {
        document.querySelector('.dashboard-container').style.transition = 'opacity 0.5s ease';
        document.querySelector('.dashboard-container').style.opacity = '1';
    }, 100);
});