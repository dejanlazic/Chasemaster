// window.onload = init;

var request;
var _debug;
var _message;
var left;
var top;

function init() {
  //alert("Click 2 fields in the FIRST row (a1-h1)");
  
  _debug = document.getElementById("msg");
  _message = document.getElementById('message');
  
  _debug.innerHTML = _debug.value + "<br/>In init()";

  postload();
  InitDragDrop();  
}

function postload() {
  var leftTopDiv = document.getElementById("a8");
  left = leftTopDiv.style.left;
  top = leftTopDiv.style.top;
  
  _debug.innerHTML = _debug.value + "<br/>In postload(), left=" + left + ", top=" + top + ", value=" + leftTopDiv.value;
  //_debug.innerHTML = _debug.value + "<br/>In postload(), left=";
  
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

  if (passwordConfirmationField.value.length == 0) {
    _message.innerHTML = "Password confirmation must be entered";
    passwordConfirmationField.focus(); // set the focus to this input
    return false;
  }
  
  if (passwordField != passwordConfirmationField) {
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


var movesCount = 0;
var positionFrom;
var positionTo;

/* Drag and Drop */

// necessary for dragging a picture
var startX = 0; 
var startY = 0; 
var offsetX = 0; 
var offsetY = 0; 

function InitDragDrop() { 
  document.onmousedown = OnMouseDown; 
  document.onmouseup = OnMouseUp; 
}

function OnMouseDown(e) { 
  // IE is retarded and doesn't pass the event object
  if (e == null) 
    e = window.event; 
    
  // grab the mouse position
  startX = e.clientX; 
  startY = e.clientY; 

  // IE uses srcElement, others use target
  var target = e.target != null ? e.target : e.srcElement;
  
  // grab the clicked element's position
  offsetX = ExtractNumber(target.style.left); 
  offsetY = ExtractNumber(target.style.top); 
  
  _debug.innerHTML = _debug.value + "<br/>In OnMouseDown(): " + target.id + ": " + startX + "," + startY + " - " + offsetX + "," + offsetY;
  
  //if ((e.button == 1 && window.event != null || e.button == 0) && target.className == 'drag') {
  if (e.button == 1 && window.event != null || e.button == 0) {  
    document.onmousemove = OnMouseMove;
      
    if (movesCount == 0) {
      positionFrom = target.id;
      movesCount = 1;
    } else {
      _message.value = "ERROR detected in OnMouseDown()";
    }
    
    // prevent text selection in IE
    document.onselectstart = function () { return false; }; 
    // prevent IE from trying to drag an image
    target.ondragstart = function() { return false; }; 
    
    return false; 
  }
}

function OnMouseMove(e) { 
  if (e == null) 
    var e = window.event; 

  // IE uses srcElement, others use target
  var target = e.target != null ? e.target : e.srcElement;    
  _debug.innerHTML = _debug.value + "<br/>In OnMouseMove(): " + target.id + ": " + e.clientX + "," + e.clientY; 
}

function OnMouseUp(e) {
  if (e == null) 
    var e = window.event; 

    // IE uses srcElement, others use target
    var target = e.target != null ? e.target : e.srcElement;    
    _debug.innerHTML = _debug.value + "<br/>In OnMouseUp(): " + target.id + ": " + e.clientX + "," + e.clientY; 
  
    if (movesCount == 1) {
      positionTo = target.id;
      movesCount = 0; // reset counter

      _debug.innerHTML = _debug.value + "<br/>In OnMouseUp, calling sendMoves(" + positionFrom + ", " + positionTo + ")";

      sendMoves();
    } else {
      _message.value = "ERROR detected in OnMouseUp()";
    }
    
    // we're done with these events until the next OnMouseDown
    document.onmousemove = null; 
    document.onselectstart = null; 
}

function ExtractNumber(value) { 
  var n = parseInt(value); 
  
  return n == null || isNaN(n) ? 0 : n; 
} 


/* AJAX */

function sendMoves() {
  _debug.innerHTML = _debug.value + "<br/>In sendMoves(" + positionFrom + ", " + positionTo + ")";

  // var url = "/Chasemaster/AjaxResponse?positionFrom=" + escape(positionFrom) + "&positionTo=" + escape(positionTo);
  var url = "/Chasemaster/async?positionFrom=" + escape(positionFrom) + "&positionTo=" + escape(positionTo);

  if (window.XMLHttpRequest) {
    request = new XMLHttpRequest();
  } else if (window.ActiveXObject) {
    request = new ActiveXObject("Microsoft.XMLHTTP");
  }

  request.open("Get", url, true);
  request.onreadystatechange = callback;
  request.send(null);
}

function callback() {
  // alert("Back in callback(): " + request.readyState + ", " + request.status);

  if (request.readyState == 4) {
    if (request.status == 200) {
      _message.value = request.responseText;
    }
  }

  clear();
}

function clear() {
  positionFrom = null;
  positionTo = null;
}