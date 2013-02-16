window.onload = initAll;

var moveCount = 0;
var move1;
var move2;
var request;

function initAll() {
   alert("Click 2 fields in the FIRST row (a1-h1)");
   document.getElementById("msg").innerHTML = document.getElementById("msg").value + "In initAll()";
   document.getElementById("msg").value = document.getElementById("msg").value + "- In initAll()";

   //document.getElementById("h1").onclick = clickHandler;
   //document.getElementById("h2").onclick = clickHandler;
}

function clickHandler() {
  document.getElementById("msg").innerHTML = document.getElementById("msg").value + "<br/>In clickHandler()";
  document.getElementById("msg").value = document.getElementById("msg").value + "<br/>- In clickHandler()";

   // do some check
   // return false; // to cancel execution
   // return true;
}


//check input controls of a comment to be created
function validateLogin() {
   var username = document.getElementById("username");
   var password = document.getElementById("password");

   alert("UN:" + username.value + ", PWD: " + password.value);
   
   if (username.value.length == 0) {
      alert("Username must be entered");
      username.focus(); // set the focus to this input
      return false;
   }

   if (password.value.length == 0) {
      alert("Password must be entered");
      password.focus(); // set the focus to this input
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

/* AJAX */

function moveHandler(move) {
  document.getElementById("msg").innerHTML = document.getElementById("msg").value + "<br/>In moveHandler(): " + moveCount;
  document.getElementById("msg").value = document.getElementById("msg").value + "<br/>- In moveHandler(): " + moveCount;
  
   if (moveCount == 0) {
      move1 = move;
      moveCount = 1;
   } else if (moveCount == 1) {
      move2 = move;
      moveCount = 0;

      document.getElementById("msg").innerHTML = document.getElementById("msg").value + "<br/>In moveHandler(" + move1 + ", " + move2 + ")";
      document.getElementById("msg").value = document.getElementById("msg").value + "<br/>- In moveHandler(" + move1 + ", " + move2 + ")";
      
      sendMoves();
   }
}

function sendMoves() {
  document.getElementById("msg").innerHTML = document.getElementById("msg").value + "<br/>In sendMoves(" + move1 + ", " + move2 + ")";
  document.getElementById("msg").value = document.getElementById("msg").value + "<br/>- In sendMoves(" + move1 + ", " + move2 + ")";
  
   //var url = "/Chasemaster/AjaxResponse?move1=" + escape(move1) + "&move2=" + escape(move2);
  var url = "/Chasemaster/async?move1=" + escape(move1) + "&move2=" + escape(move2);
  //var url = "/Chasemaster/web/move";

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
   //alert("Back in callback(): " + request.readyState + ", " + request.status);
   
   if (request.readyState == 4) {
      if (request.status == 200) {
         document.getElementById('message').value = request.responseText;
      }
   }
   
   clear();
}

function clear() {
   move1 = null;
   move2 = null;
}