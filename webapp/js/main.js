window.onload = init;

var xmlRequest;
var _debug;
var _message;

function init() {
  _debug = document.getElementById("debug");
  _message = document.getElementById("message");
  
  _debug.innerHTML = _debug.innerHTML + "<br/>In init()";  
  // getAllowedMovements();
}

//function getAllowedMovements() {
//  var url = "/Chasemaster/test.do";
//
//  alert('getAllowedMovements 1');
//
//  if (window.XMLHttpRequest) {
//    xmlRequest = new XMLHttpRequest();
//  } else if (window.ActiveXObject) {
//    xmlRequest = new ActiveXObject("Microsoft.XMLHTTP");
//  }
//
//  alert('getAllowedMovements 2');
//
//  xmlRequest.open("GET", url, false);
//  alert('getAllowedMovements 3');
//  xmlRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//  
//  xmlRequest.onreadystatechange = callback;
////xmlRequest.onreadystatechange = function() {
////  alert('getAllowedMovements 3: ');
////  if (xmlRequest.readyState == 4 && xmlRequest.status == 200) {
////    alert('getAllowedMovements 4');
////    alert(xmlRequest.responseText);
////
////    // var contentElement = document.getElementById("message");
////    // contentElement.innerHTML = xmlRequest.responseText + contentElement.innerHTML;
////    // contentElement.innerHTML = xmlRequest.responseText;
////  }
////}
//
//  alert('getAllowedMovements 4');
//  // xmlRequest.send(null);
//  xmlRequest.send("operation=test");
//}
//
//function callback() {
//  _message.innerHTML = "Back in callback()";
//  //alert("Back in callback(): " + xmlRequest.readyState + ", " + xmlRequest.status);
//  alert('W');
//  
////  if (xmlRequest.readyState == 4 && xmlRequest.status == 200) {
////      alert("W"); //_message.value = xmlRequest.responseText;
////    }
////  }
//
//  //clear();
//}

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