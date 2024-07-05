// Importa le librerie di Firebase necessarie per il funzionamento delle notifiche push.

// Configura il tuo oggetto firebaseConfig qui.
const firebaseConfig = {
   apiKey: "AIzaSyBQ4sCEI9ZhvUXzK5glm_a2CcKV4d7LBfk",
   authDomain: "marketnexus.firebaseapp.com",
   projectId: "marketnexus",
   storageBucket: "marketnexus.appspot.com",
   messagingSenderId: "884202382554",
   appId: "1:884202382554:web:c47ede0daa91ee7d61f1e3",
   measurementId: "G-0DNFQT9WRX"
};

// Inizializza Firebase.
if (!firebase.apps.length) {
   firebase.initializeApp(firebaseConfig);
} else {
   firebase.app(); // se già inizializzato, usa quell"istanza
}

// Registra il Service Worker
if ("serviceWorker" in navigator) {
   navigator.serviceWorker.register("/firebase-messaging-sw.js")
      .then((registration) => {
         console.log("Service Worker registration successful with scope: ", registration.scope);
      })
      .catch((err) => {
         console.error("Service Worker registration failed: ", err);
      });
}

// Attendi che il Service Worker sia attivo
navigator.serviceWorker.ready.then((registration) => {
   console.log(registration);
   // Inizializza Firebase Messaging
   const messaging = firebase.messaging();

   messaging.onMessage((payload) => {
      console.log("Message received. ", payload);
      // Personalizza qui la visualizzazione della notifica
   });

   // Richiedi il permesso e sottoscrivi le notifiche push
   messaging.requestPermission().then(() => {
      return messaging.getToken({vapidKey: "BJfWInhR997GRhcwRDzCTgxoHBn7kIXxQ4jY80DCBDt90AZdB-GvKQgik2BiVBh_x0ECdHgVe_wN58fYf-SolrQ"});
   }).then((token) => {
      console.log("Notification permission granted. Token:", token);
      // Invia il token al server
      axios.post("/saveToken", token, {
         headers: {
            "Content-Type": "text/plain"
         }
      })
         .then(response => console.log(response, response.data))
         .catch(error => console.error(error));
   }).catch((error) => {
      console.error("Unable to get permission to notify.", error);
   });
}).catch((error) => {
   console.error("Service Worker is not ready", error);
});