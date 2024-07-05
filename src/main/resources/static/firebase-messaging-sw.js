// Importa le librerie di Firebase necessarie per il funzionamento delle notifiche push.
importScripts('https://www.gstatic.com/firebasejs/8.2.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.2.0/firebase-messaging.js');

// Configura il tuo oggetto firebaseConfig qui.
const firebaseConfig = {
   apiKey: "YOUR_API_KEY",
   authDomain: "YOUR_PROJECT_ID.firebaseapp.com",
   projectId: "YOUR_PROJECT_ID",
   storageBucket: "YOUR_PROJECT_ID.appspot.com",
   messagingSenderId: "YOUR_MESSAGING_SENDER_ID",
   appId: "YOUR_APP_ID",
   measurementId: "YOUR_MEASUREMENT_ID"
};

// Inizializza Firebase.
firebase.initializeApp(firebaseConfig);

// Inizializza Firebase Messaging e gestisci le notifiche push in arrivo.
const messaging = firebase.messaging();

// Aggiungi qui il gestore per le notifiche push in arrivo, se necessario.
messaging.setBackgroundMessageHandler(function (payload) {
   const title = "Hello World";
   const options = {
      body: payload.data.status
   };
   return self.registration.showNotification(title, options);
});