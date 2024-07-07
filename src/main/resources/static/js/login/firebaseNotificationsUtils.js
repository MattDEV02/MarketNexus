const firebaseConfig = {
   apiKey: "AIzaSyBQ4sCEI9ZhvUXzK5glm_a2CcKV4d7LBfk",
   authDomain: "marketnexus.firebaseapp.com",
   projectId: "marketnexus",
   storageBucket: "marketnexus.appspot.com",
   messagingSenderId: "884202382554",
   appId: "1:884202382554:web:c47ede0daa91ee7d61f1e3",
   measurementId: "G-0DNFQT9WRX"
};

const vapidKey = "BJfWInhR997GRhcwRDzCTgxoHBn7kIXxQ4jY80DCBDt90AZdB-GvKQgik2BiVBh_x0ECdHgVe_wN58fYf-SolrQ";

const initializeFirebase = firebase => !firebase.apps.length ? firebase.initializeApp(firebaseConfig) : firebase.app();