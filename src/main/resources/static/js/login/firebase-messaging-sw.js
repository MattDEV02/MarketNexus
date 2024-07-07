// Import Firebase libraries
importScripts("/js/libraries/firebase/firebase-app.js");
importScripts("/js/libraries/firebase/firebase-messaging.js");
importScripts("/js/login/firebaseNotificationsUtils.js");

initializeFirebase(firebase);

const messaging = firebase.messaging();

// Handle background notifications
messaging.setBackgroundMessageHandler(payload => {
   console.log(payload);
   const title = "Background Message Title";
   const icon = "/images/logo/logo_192.png";
   const options = {
      body: payload.data.status,
      icon,
      image: icon,
      badge: icon,
      tag: "Sales",
   };
   return self.registration.showNotification(title, options);
});