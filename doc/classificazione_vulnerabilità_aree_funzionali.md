# Classificazione delle vulnerabilità in base alle aree funzionali applicative in cui possono presentarsi

Nel seguito è fornita una possibile classificazione delle vulnerabilità individuate (tenendo presente sia la "OWASP Top Ten 2021" sia le “Linee Guida per lo sviluppo sicuro” di AGID per Java) in base alle aree funzionali applicative in cui esse possono presentarsi.


## 1. **Creazione**
  
Questa area riguarda il modo in cui vengono creati, istanziati o generati elementi (oggetti, query, strutture dati, ecc.) all’interno dell’applicazione.

**Vulnerabilità tipiche:**

- **Injection (SQL, LDAP, Command, ecc.):**  
  Si verifica quando input non validati o non adeguatamente sanificati vengono incorporati in comandi o query, portando all’esecuzione di codice malevolo.
  _Esempio:_ Un’applicazione Java che costruisce dinamicamente una query SQL concatenando stringhe senza adeguata sanificazione.

- **Cross-Site Scripting (XSS):**  
  Se l’applicazione genera pagine web includendo dati non controllati, potrebbe consentire l’iniezione di script dannosi.  
  _Esempio:_ Visualizzazione diretta di dati utente non sanificati in una pagina web.

- **Software and Data Integrity Failures:**  
  La deserializzazione di dati non controllati può portare all’instaurazione di oggetti in uno stato inconsistente o all’esecuzione di codice arbitrario.
  _Esempio:_ Deserializzazione di oggetti provenienti da fonti esterne senza adeguate verifiche.

- **Server-Side Request Forgery (SSRF):**
  Le falle SSRF si verificano ogni volta che un'applicazione web recupera una risorsa remota senza validare l'URL fornito dall'utente.
  _Esempio:_ Una applicazioni web che fornisce agli utenti finali una funzionalita che necessita di scaricare dati da un URL per eseguire successive elaborazioni(come caricamento di fogli excel o file pdf)

<div style="page-break-before: always;"></div>

## 2. **Autenticazione**

Questa area copre tutti gli aspetti legati alla verifica dell’identità degli utenti e alla gestione delle sessioni.

**Vulnerabilità tipiche:**

- **Identification and Authentication Failures:**  
  Problemi legati alla memorizzazione in chiaro delle password o all’assenza di meccanismi di hashing adeguati. 
  Se il sistema non invalida correttamente l'identificatore di sessione. La sessione dell'utente o i token di autenticazione (principalmente token di single sign-on (SSO)) non vengono invalidati in modo opportuno durante il logout o dopo un periodo di inattività.
  _Esempio:_ Session ID prevedibili o mancata invalidazione della sessione al logout dell'utente.

<br/><br/>

## 3. **Comunicazione**

Questa area riguarda lo scambio di dati tra componenti dell’applicazione con sistemi esterni, inclusi i protocolli e i meccanismi di cifratura.

**Vulnerabilità tipiche:**

- **Criptographic Failure:**
  La trasmissione o la memorizzazione di dati sensibili senza adeguata protezione (ad es. mancanza di crittografia, o crittografia insicura) espone a intercettazioni e furto di informazioni. L’uso di protocolli o configurazioni di sicurezza deboli (ad es. SSL/TLS mal configurati) che permettono attacchi di tipo man-in-the-middle.
  _Esempio:_ Comunicazioni HTTP invece di HTTPS in ambienti che richiedono la protezione dei dati.


<div style="page-break-before: always;"></div>
  

## 4. **Gestione Risorse**

Questa area coinvolge la gestione delle risorse del sistema, come file, database, memoria, thread e componenti esterni.

**Vulnerabilità tipiche:**

- **Broken Access Control:**  
  Rientra nella mancata protezione nell’accesso a dati o funzionalità.  
  _Esempio:_ Mancata verifica dei permessi quando un utente tenta di accedere a risorse non autorizzate.

- **Vulnerable and Outdated Components:**  
  L’inclusione di librerie o framework non aggiornati, che gestiscono risorse e possono esporre il sistema a exploit già noti.

- **Security Misconfiguration:**  
  Errori nelle configurazioni di risorse (ad es. database, container, file system) che possono consentire accessi non autorizzati o esecuzioni di codice.

<br/><br/>

## 5. **Logging**
 
Questa area si concentra sulle modalità di registrazione degli eventi, e sulla protezione dei log stessi.

**Vulnerabilità tipiche:**

- **Security Logging and Monitoring Failures:**
  La mancanza di registrazione adeguata degli eventi critici impedisce il rilevamento tempestivo di comportamenti anomali o attacchi in corso.  
  _Esempio:_ Assenza di log per tentativi di accesso falliti o anomalie nei processi di autenticazione.

- **Logging of sensitive information:**  
  La registrazione non filtrata di dati sensibili può facilitare l’esposizione di tali dati in caso di accesso non autorizzato ai log. Questa vulnerabilità è in parte associabile alla categoria di vulnerabilità "Sensitive Data Exposure" che dalla top ten 2021 è compresa in "Broken Access Control'.

<div style="page-break-before: always;"></div>

## Note Integrative

- **Sovrapposizioni:**  
  Alcune vulnerabilità possono coinvolgere più di una area funzionale. Ad esempio, un attacco di injection, che precedentemente è stato mappato in “Creazione”, può avere impatti anche sulla “Comunicazione” se i dati manipolati vengono poi trasmessi ad altri sistemi, oppure sulla “Gestione risorse” se il risultato dell’iniezione consente l’accesso non autorizzato a risorse.

- **Specificità dei documenti:**  
  Le “Linee Guida per lo sviluppo sicuro” di AGID forniscono indicazioni specifiche per Java, pertanto alcuni consigli (ad es. controlli specifici sul parsing di file XML o sulla gestione delle eccezioni) non hanno un corrispettivo esplicito nella OWASP Top Ten, ma rientrano comunque nelle aree sopra descritte.

- **Vulnerabilità OWASP assente**
  Nella mappatura si è cercato di introdurre quante più vulnerabilità OWASP possibili, però non tutte fanno riferimento a una specifica area funzionale applicativa.
  Di conseguenza è assente la vulnerabilità: _Insecure Design_ poichè fa riferimento a una cattiva progettazione applicativa più che a una specifica area funzionale dell'applicazione.

<div style="page-break-before: always;"></div>

## Tabella Riassuntiva

| Vulnerabilità                                    | Creazione | Autenticazione | Comunicazione | Gestione Risorse | Logging |
|--------------------------------------------------|-----------|----------------|---------------|------------------|---------|
| **Injection (SQL, LDAP, Command, ecc.)**         | ✅        |               | ✅             | ✅              |         |
| **Cross-Site Scripting**                         | ✅        |               | ✅             | ✅              |         |
| **Software and Data Integrity Failures**         | ✅        |               |                |                  |         |
| **Server-Side Request Forgery**                  | ✅        |               | ✅             |                  |         |
| **Identification and Authentication Failures**   |           | ✅             |               |                  |         |
| **Criptographic Failure**                        |           |                | ✅            |                  |         |
| **Broken Access Control**                        |           |                |               | ✅               |         |
| **Vulnerable and Outdated Components**           |           |                |               | ✅               |         |
| **Security Misconfiguration**                    |           |                |               | ✅               |         |
| **Security Logging and Monitoring Failures**     |           |                |               |                  | ✅      |
| **Logging of sensitive information**             |           |                |               | ✅               | ✅      |
