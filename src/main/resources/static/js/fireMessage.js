/**
 * 
 */
  // Your web app's Firebase configuration
  // For Firebase JS SDK v7.20.0 and later, measurementId is optional
  var firebaseConfig = {
    apiKey: "AIzaSyCdbLBXVeVnKD-xf7t5zUyOR8MP6U1H3Q8",
    authDomain: "desifarmings.firebaseapp.com",
    databaseURL: "https://desifarmings-default-rtdb.firebaseio.com",
    projectId: "desifarmings",
    storageBucket: "desifarmings.appspot.com",
    messagingSenderId: "796274608747",
    appId: "1:796274608747:web:14e2d1b71d78fe7718ff2e",
    measurementId: "G-SEM2NKKY3X"
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  firebase.analytics();

// Retrieve Firebase Messaging object.
const messaging = firebase.messaging();