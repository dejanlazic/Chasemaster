window.onload = init;

var _debug;
var _message;

function init() {
  //alert("Click 2 fields in the FIRST row (a1-h1)");
  
  _debug = document.getElementById("debug");
  _message = document.getElementById("message");
  
  _debug.innerHTML = _debug.innerHTML + "<br/>In init()";  
}

function postload() {
  init();
  
  var leftTopDiv = document.getElementById("a8");
  left = leftTopDiv.style.left;
  top = leftTopDiv.style.top;
  
  //_debug.innerHTML = _debug.value + "<br/>In postload(), left=" + left + ", top=" + top + ", value=" + leftTopDiv.value;
  
  //alert("postload()");
}

// check input mandatory input fields
function validateLoginForm() {
  var usernameField = document.getElementById("username");
  var passwordField = document.getElementById("password");

  // alert("UN:" + usernameField.value + ", PWD: " + passwordField.value);

  if (usernameField.value.length == 0) {
    _message.innerHTML = "Username must be entered";
    usernameField.focus(); // set the focus to this input
    return false;
  }

  if (passwordField.value.length == 0) {
    _message.innerHTML = "Password must be entered";
    passwordField.focus(); // set the focus to this input
    return false;
  }

  return true;
}

// check input mandatory input fields
function validateRegistrationForm() {
  var usernameField = document.getElementById("username");
  var passwordField = document.getElementById("password");
  var passwordConfirmationField = document.getElementById("passwordConfirmation");

  alert("UN:" + usernameField.value + ", PWD: '" + passwordField.value + "', PWD2: '" + passwordConfirmationField.value + "'");

  if (usernameField.value.length == 0) {
    _message.innerHTML = "Username must be entered";
    usernameField.focus(); // set the focus to this input
    return false;
  }

  if (passwordField.value.length == 0) {
    _message.innerHTML = "Password must be entered";
    passwordField.focus(); // set the focus to this input
    return false;
  }

  if (passwordConfirmationField.value.length == 0) {
    _message.innerHTML = "Password confirmation must be entered";
    passwordConfirmationField.focus(); // set the focus to this input
    return false;
  }
  
  if (passwordField.value != passwordConfirmationField.value) {
    _message.innerHTML = "Password must be confirmed with the same value";
    passwordConfirmationField.focus(); // set the focus to this input
    return false;
  }
  
  return true;
}

function doConfirm() {
  if (confirm("Are you sure?")) {
    alert("Yes");
  } else {
    alert("No");
  }

  doPrompt();
}

function doPrompt() {
  var ans = prompt("Are you sure again?", "Of course");

  if (ans) {
    alert("You said " + ans);
  } else {
    alert("You said nothing");
  }
}