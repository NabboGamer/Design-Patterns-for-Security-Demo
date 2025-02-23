document.addEventListener('DOMContentLoaded', function() {
    // Recupero dati utente dal localStorage
    const userData = JSON.parse(localStorage.getItem('userData'));
    const userSecrets = JSON.parse(localStorage.getItem('userSecrets'));

    // Controllo autenticazione
    if (!userData || !userSecrets) {
        window.location.href = 'login.html';
        return;
    }

    // Mappa degli elementi DOM
    const elements = {
        userName: document.getElementById('userName'),
        userSurname: document.getElementById('userSurname'),
        userLevel: document.getElementById('userLevel'),
        userCompanyIdentificationCode: document.getElementById('userCompanyIdentificationCode'),
        userImage: document.getElementById('userImage'),
        userEmail: document.getElementById('userEmail'),
        userPhone: document.getElementById('userPhone'),
        userAddress: document.getElementById('userAddress')
    };

    // Popolazione dati utente
    elements.userName.textContent = userData.name || 'N/D';
    elements.userSurname.textContent = userData.surname || 'N/D';
    elements.userLevel.textContent = userData.level || 'N/D';
    elements.userCompanyIdentificationCode.textContent = userData.companyIdentificationCode || 'N/D';
    elements.userEmail.textContent = userData.email || 'N/D';
    elements.userPhone.textContent = userData.phone || 'N/D';
    elements.userAddress.textContent = userData.address || 'N/D';

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
            width: 7,                  // Spessore delle linee
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

    // Aggiunta gestione modifica
    const editButton = document.getElementById('editButton');
    let isEditMode = false;
    let originalValues = {};

    // Campi modificabili (escludendo livello e CIA)
    const editableFields = {
        'userName': 'name',
        'userSurname': 'surname',
        'userEmail': 'email',
        'userPhone': 'phone',
        'userAddress': 'address'
    };

     const responseFieldsToEditableFieldsMapping = {
        'name': 'userName',
        'surname': 'userSurname',
        'email': 'userEmail',
        'phone': 'userPhone',
        'address': 'userAddress'
     }

    if(userData.role === 'ADMIN') {
        editableFields['userLevel'] = 'level';
        responseFieldsToEditableFieldsMapping['level'] = 'userLevel';
    }

    editButton.addEventListener('click', async function() {
        if (!isEditMode) {
            // Entra in modalità modifica
            enterEditMode();
        } else {
            // Esegui il salvataggio
            const dataUpdatedCorrectly = {};
            await saveChanges(dataUpdatedCorrectly);
            exitEditMode(dataUpdatedCorrectly);
        }
    });

    function enterEditMode() {
        isEditMode = true;
        editButton.textContent = 'Salva';
        originalValues = {};

        Object.keys(editableFields).forEach(id => {
            const element = document.getElementById(id);
            originalValues[id] = element.textContent;

            const input = document.createElement('input');
            input.type = 'text';
            input.value = element.textContent;
            input.className = 'edit-input';
            input.dataset.field = id; // Aggiungi attributo data-field

            element.replaceWith(input); // Sostituisci direttamente lo span
        });
    }

    function exitEditMode(dataUpdatedCorrectly) {
        isEditMode = false;
        editButton.textContent = 'Modifica';

        // Ripristina gli span con i valori aggiornati
        Object.keys(editableFields).forEach(id => {
            const input = document.querySelector(`input[data-field="${id}"]`);
            const span = document.createElement('span');
            span.id = id;
            span.className = 'info-value';
            if(dataUpdatedCorrectly[id] !== undefined){
                span.textContent = input.value;
            } else {
                span.textContent = originalValues[id];
            }

            input.parentNode.replaceChild(span, input);
        });
    }

    async function saveChanges(dataUpdatedCorrectly) {
        const dataToUpdate = {};

        // Raccogli i dati modificati
        Object.entries(editableFields).forEach(([elementId, fieldName]) => {
            const input = document.querySelector(`input[data-field="${elementId}"]`);
            if (!input) {
                console.error('Input non trovato per il campo:', elementId);
                return;
            }

            const newValue = input.value;
            if (newValue !== originalValues[elementId]) {
                dataToUpdate[fieldName] = newValue;
            }
        });

        if (Object.keys(dataToUpdate).length === 0) return;

        // Invia le richieste di aggiornamento al BE
        for (const [updatedField, updatedValue] of Object.entries(dataToUpdate)) {
            console.log(`Campo ${updatedField} aggiornato con valore: ${updatedValue}`);
            try {
                const response = await sendRequest(updatedField, updatedValue, dataUpdatedCorrectly);
            } catch (error) {
                let message = `Errore durante l'aggiornamento del campo ${updatedField}: ${error.message}`;
                console.error(message);
                alert(message);
            }
        }
    }

    async function sendRequest(updatedField, updatedValue, dataUpdatedCorrectly) {
        try {
            const response = await fetch(`http://localhost:8080/dashboard/edit/${updatedField}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    'username': userSecrets.username,
                    'password': userSecrets.password,
                    [updatedField]: updatedValue
                })
            });

            if (response.status === 400 ) {
                throw new Error('ILLEGAL_ARGUMENT_ERROR');
            } else if (response.status === 401 ) {
                throw new Error('CREDENTIALS_ERROR');
            } else if (response.status === 403){
                throw new Error('INSUFFICIENT_PERMISSIONS');
            } else if (response.status >= 500) {
                throw new Error('SERVER_ERROR');
            } else if (!response.ok) {
                throw new Error('GENERIC_ERROR');
            }

            const data = await response.json();
            userData[updatedField] = updatedValue;
            localStorage.setItem('userData', JSON.stringify(data));
            dataUpdatedCorrectly[responseFieldsToEditableFieldsMapping[updatedField]] = updatedValue;
            return data;
        } catch (error) {
            let errorType;

            if (error.message === 'Failed to fetch') {
                errorType = 'NETWORK_ERROR';
            } else {
                errorType = error.message;
            }

            console.error(error);
            throw new Error(getErrorMessage(errorType));
        }
    }

    function getErrorMessage(errorType) {
        const messages = {
            'ILLEGAL_ARGUMENT_ERROR': 'Valore non valido!',
            'CREDENTIALS_ERROR': 'Credenziali non valide!',
            'INSUFFICIENT_PERMISSIONS': 'Non hai i permessi per eseguire questa azione!',
            'SERVER_ERROR': 'Errore interno del server. Riprova più tardi.',
            'NETWORK_ERROR': 'Errore di connessione. Verifica la tua rete.',
            'GENERIC_ERROR': 'Si è verificato un errore. Riprova.',
            'default': 'Errore sconosciuto.'
        };
        return messages[errorType] || messages.default;
    }

});

