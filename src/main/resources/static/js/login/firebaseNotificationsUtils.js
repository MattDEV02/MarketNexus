const firebaseConfig = {
   apiKey: "AIzaSyBQ4sCEI9ZhvUXzK5glm_a2CcKV4d7LBfk",
   authDomain: "marketnexus.firebaseapp.com",
   projectId: "marketnexus",
   storageBucket: "marketnexus.appspot.com",
   messagingSenderId: "884202382554",
   appId: "1:884202382554:web:c47ede0daa91ee7d61f1e3",
   measurementId: "G-0DNFQT9WRX"
};

const vapidKey = "BH_mizqc-VwAkshL17wZxhySQ6itNZ54yqjtS0YxvW6wBWe9NHrMmRMQZArM6hGuA25iKL8LNko9OBOgg2gvDJI";

const initializeFirebase = firebase => !firebase.apps.length ? firebase.initializeApp(firebaseConfig) : firebase.app();