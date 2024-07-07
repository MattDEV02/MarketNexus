initializeFirebase(firebase);

// Registra il Service Worker
if ("serviceWorker" in navigator) {
   navigator.serviceWorker.register("/js/login/firebase-messaging-sw.js")
      .then(registration => {
         console.log("Service Worker registration successful with scope: ", registration.scope);
         // Attendi che il Service Worker sia attivo
         navigator.serviceWorker.ready.then(registration => {
            console.log("Service Worker is ready:", registration);

            // Richiedi il permesso per le notifiche
            Notification.requestPermission().then(permission => {
               const messaging = firebase.messaging();
               if (permission === "granted") {
                  console.log("Notification permission granted.");

                  // Ottieni il token
                  return messaging.getToken({vapidKey});
               } else {
                  console.warn("Notification permission denied.");
               }
            }).then(token => {
               console.log("Token received: ", token);
               // Invia il token al server
               axios.post("/storeFirebaseToken", {token}, {
                  headers: {
                     "Content-Type": "application/json"
                  }
               })
                  .then(response => console.log("Token sent to server:", response))
                  .catch(error => console.error("Error sending token to server:", error));
            }).catch(error => {
               console.error("An error occurred while getting the token: ", error);
            });
         }).catch(error => console.error("Service Worker is not ready", error));
      })
      .catch(error => console.error("Service Worker registration failed: ", error));
} else {
   console.log("Service Worker is not supported by this Browser.");
}
