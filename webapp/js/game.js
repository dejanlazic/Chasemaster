//window.onload = init;

var xmlRequest;
var _board;
var _debug;
var _message;
var _hiddenPlayerId;
//var _checkMate;
var left;
var top;

function init() {
  _board = document.getElementById("board");
  // _debug = document.getElementById("debug");
  _message = document.getElementById("message");
  _hiddenPlayerId = document.getElementById("playerid");
  //_checkMate = document.getElementById("checkMate");

  // _debug.innerHTML = _debug.innerHTML + "<br/>In init()";
  // _debug.innerHTML = _debug.innerHTML + "<br/>hiddenPlayerId=" + _hiddenPlayerId.value;

  initDragDrop();
}

// implemented in game.jsp#getMessages()
function callback() {
  if (xmlRequest.readyState == 4 && xmlRequest.status == 200) {
    messagesWaiting = false;

    clearPositions();

    // write given response
    document.getElementById("message").innerHTML = xmlRequest.responseText;
  }
}

/* Drag and Drop */

var moved = false;
var movesCount = 0;
var positionFrom;
var positionTo;
// necessary for dragging a picture
var startX = 0;
var startY = 0;
var offsetX = 0;
var offsetY = 0;

function initDragDrop() {
  // document.onmousedown = onMouseDown;
  // document.onmouseup = onMouseUp;
  _board.onmousedown = onMouseDown;
  _board.onmouseup = onMouseUp;
}

function onMouseDown(e) {
  if (moved == true) {
    alert("Movement already performed");
    return false;
  }

  // IE is retarded and doesn't pass the event object
  if (e == null)
    e = window.event;

  // grab the mouse position
  startX = e.clientX;
  startY = e.clientY;

  // IE uses srcElement, others use target
  var target = e.target != null ? e.target : e.srcElement;

  if (target.parentNode.parentNode.id == 'board') {
    // grab the clicked element's position
    offsetX = extractNumber(target.style.left);
    offsetY = extractNumber(target.style.top);

    // _debug.innerHTML = _debug.innerHTML + "<br/>In onMouseDown(): " + target.id + ": " + startX + "," + startY + " -
    // " + offsetX + "," + offsetY;

    // if ((e.button == 1 && window.event != null || e.button == 0) && target.className == 'drag') {
    if (e.button == 1 && window.event != null || e.button == 0) {
      document.onmousemove = onMouseMove;

      if (movesCount == 0) {
        positionFrom = target.id;
        movesCount = 1;
      } else {
        _message.innerHTML = "ERROR detected in onMouseDown()";
      }

      // prevent text selection in IE
      document.onselectstart = function() {
        return false;
      };
      // prevent IE from trying to drag an image
      target.ondragstart = function() {
        return false;
      };

      return false;
    }
  }
}

function onMouseMove(e) {
  if (e == null)
    var e = window.event;

  // IE uses srcElement, others use target
  var target = e.target != null ? e.target : e.srcElement;
  // _debug.innerHTML = _debug.innerHTML + "<br/>In onMouseMove(): " + target.id + ": " + e.clientX + "," + e.clientY;
}

function onMouseUp(e) {
  if (e == null)
    var e = window.event;

  // IE uses srcElement, others use target
  var target = e.target != null ? e.target : e.srcElement;
  // _debug.innerHTML = _debug.innerHTML + "<br/>In onMouseUp(): " + target.id + ": " + e.clientX + "," + e.clientY;

  if (target.parentNode.parentNode.id == 'board') {
    if (movesCount == 1) {
      positionTo = target.id;
      movesCount = 0; // reset counter

      // _debug.innerHTML = _debug.innerHTML + "<br/>In onMouseUp, calling sendMovement(" + positionFrom + ", " +
      // positionTo + ")";

      moved = true;

      // send a movements
      sendMovement();
    } else {
      _message.innerHTML = "ERROR detected in OnMouseUp()";
    }

    // we're done with these events until the next OnMouseDown
    document.onmousemove = null;
    document.onselectstart = null;
  }
}

function extractNumber(value) {
  var n = parseInt(value);

  return n == null || isNaN(n) ? 0 : n;
}

/* AJAX */

function sendMovement() {
  // _debug.innerHTML = _debug.innerHTML + "<br/>In sendMovement(" + positionFrom + ", " + positionTo + ")";
  var d = new Date();
  var tms = d.getTime(); // in milliseconds
  // var tutc = d.toUTCString();
  // _debug.innerHTML = _debug.innerHTML + "<br/>In sendMovement - date:" + d + ", " + tms;

  // var url = "/Chasemaster/AjaxResponse?positionFrom=" + escape(positionFrom) + "&positionTo=" + escape(positionTo);
  // var url = "/Chasemaster/async?positionFrom=" + escape(positionFrom) + "&positionTo=" + escape(positionTo);
  // var url = "async.do?positionFrom=" + escape(positionFrom) + "&positionTo=" + escape(positionTo);
  // var url = "asyncsend.do?t=" + new Date() + "&positionFrom=" + escape(positionFrom) + "&positionTo=" +
  // escape(positionTo);
  // var url = "asyncsend.do?t=" + new Date();
  var url = "Game?t=" + d;

  var xmlRequest = null;
  if (window.XMLHttpRequest) {
    xmlRequest = new XMLHttpRequest();
  } else if (window.ActiveXObject) {
    xmlRequest = new ActiveXObject("Microsoft.XMLHTTP");
  }

  xmlRequest.open("POST", url, false);
  xmlRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  // xmlRequest.onreadystatechange = callback;
  // xmlRequest.send(null);

  // moved = false;
  xmlRequest.send("operation=move&positionFrom=" + escape(positionFrom) + "&positionTo=" + escape(positionTo) + "&playerid="
      + escape(_hiddenPlayerId.value) + "&tms=" + escape(tms));
}

function clearPositions() {
  positionFrom = null;
  positionTo = null;
  moved = false;
}

function deInit() {
  alert('Out of game (player id: ' + _hiddenPlayerId.value + ')');

//  document.onmousedown = null;
//  document.onmouseup = null;
  _board.onmousedown = null;
  _board.onmouseup = null;  
}