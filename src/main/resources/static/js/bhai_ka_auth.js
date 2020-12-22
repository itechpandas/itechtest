/**
 * 
 */

firebase.auth().onAuthStateChanged(function(user) {
  if (user) {
    // User is signed in.
    var user = firebase.auth().currentUser;

    if(user != null){

      var email_id = user.email;
      alert(email_id);
    }

  } else {
    // No user is signed in.
alert("no user");

  }
});

function login(){

  var userEmail = document.getElementById("email_auth").value;
  var userPass = document.getElementById("auth_pass").value;

  firebase.auth().signInWithEmailAndPassword(userEmail, userPass).catch(function(error) {
    // Handle Errors here.
    var errorCode = error.code;
    var errorMessage = error.message;

    window.alert("Error : " + errorMessage);

    // ...
  });

}

function logout(){
  firebase.auth().signOut();
}
