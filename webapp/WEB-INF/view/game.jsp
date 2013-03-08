<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

	<head>
		<title>Chasemaster: Game</title>

		<!-- Use context relative (i.e. absolute) paths with forwarding -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/game.js"></script>
	</head>

	<body>
      <table>
	      <tr>
		      <td>		   
					<div id="grid">		
						<div class="row">
							<div id="A8" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A8}); background-size: 50px 50px; height: 50px; width: 50px;">a8</div>
							<div id="B8" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B8}); background-size: 50px 50px; height: 50px; width: 50px;">b8</div>
							<div id="C8" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C8}); background-size: 50px 50px; height: 50px; width: 50px;">c8</div>
							<div id="D8" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D8}); background-size: 50px 50px; height: 50px; width: 50px;">d8</div>
							<div id="E8" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E8}); background-size: 50px 50px; height: 50px; width: 50px;">e8</div>
							<div id="F8" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F8}); background-size: 50px 50px; height: 50px; width: 50px;">f8</div>
							<div id="G8" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G8}); background-size: 50px 50px; height: 50px; width: 50px;">g8</div>
							<div id="H8" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H8}); background-size: 50px 50px; height: 50px; width: 50px;">h8</div>
						</div>
						<div class="row">
							<div id="A7" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A7}); background-size: 50px 50px; height: 50px; width: 50px;">a7</div>
							<div id="B7" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B7}); background-size: 50px 50px; height: 50px; width: 50px;">b7</div>
							<div id="C7" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C7}); background-size: 50px 50px; height: 50px; width: 50px;">c7</div>
							<div id="D7" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D7}); background-size: 50px 50px; height: 50px; width: 50px;">d7</div>
							<div id="E7" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E7}); background-size: 50px 50px; height: 50px; width: 50px;">e7</div>
							<div id="F7" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F7}); background-size: 50px 50px; height: 50px; width: 50px;">f7</div>
							<div id="G7" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G7}); background-size: 50px 50px; height: 50px; width: 50px;">g7</div>
							<div id="H7" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H7}); background-size: 50px 50px; height: 50px; width: 50px;">h7</div>
						</div>
						<div class="row">
							<div id="A6" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A6}); background-size: 50px 50px; height: 50px; width: 50px;">a6</div>
							<div id="B6" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B6}); background-size: 50px 50px; height: 50px; width: 50px;">b6</div>
							<div id="C6" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C6}); background-size: 50px 50px; height: 50px; width: 50px;">c6</div>
							<div id="D6" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D6}); background-size: 50px 50px; height: 50px; width: 50px;">d6</div>
							<div id="E6" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E6}); background-size: 50px 50px; height: 50px; width: 50px;">e6</div>
							<div id="F6" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F6}); background-size: 50px 50px; height: 50px; width: 50px;">f6</div>
							<div id="G6" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G6}); background-size: 50px 50px; height: 50px; width: 50px;">g6</div>
							<div id="H6" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H6}); background-size: 50px 50px; height: 50px; width: 50px;">h6</div>
						</div>
						<div class="row">
							<div id="A5" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A5}); background-size: 50px 50px; height: 50px; width: 50px;">a5</div>
							<div id="B5" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B5}); background-size: 50px 50px; height: 50px; width: 50px;">b5</div>
							<div id="C5" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C5}); background-size: 50px 50px; height: 50px; width: 50px;">c5</div>
							<div id="D5" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D5}); background-size: 50px 50px; height: 50px; width: 50px;">d5</div>
							<div id="E5" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E5}); background-size: 50px 50px; height: 50px; width: 50px;">e5</div>
							<div id="F5" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F5}); background-size: 50px 50px; height: 50px; width: 50px;">f5</div>
							<div id="G5" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G5}); background-size: 50px 50px; height: 50px; width: 50px;">g5</div>
							<div id="H5" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H5}); background-size: 50px 50px; height: 50px; width: 50px;">h5</div>
						</div>
						<div class="row">
							<div id="A4" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A4}); background-size: 50px 50px; height: 50px; width: 50px;">a4</div>
							<div id="B4" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B4}); background-size: 50px 50px; height: 50px; width: 50px;">b4</div>
							<div id="C4" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C4}); background-size: 50px 50px; height: 50px; width: 50px;">c4</div>
							<div id="D4" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D4}); background-size: 50px 50px; height: 50px; width: 50px;">d4</div>
							<div id="E4" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E4}); background-size: 50px 50px; height: 50px; width: 50px;">e4</div>
							<div id="F4" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F4}); background-size: 50px 50px; height: 50px; width: 50px;">f4</div>
							<div id="G4" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G4}); background-size: 50px 50px; height: 50px; width: 50px;">g4</div>
							<div id="H4" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H4}); background-size: 50px 50px; height: 50px; width: 50px;">h4</div>
						</div>
						<div class="row">
							<div id="A3" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A3}); background-size: 50px 50px; height: 50px; width: 50px;">a3</div>
							<div id="B3" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B3}); background-size: 50px 50px; height: 50px; width: 50px;">b3</div>
							<div id="C3" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C3}); background-size: 50px 50px; height: 50px; width: 50px;">c3</div>
							<div id="D3" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D3}); background-size: 50px 50px; height: 50px; width: 50px;">d3</div>
							<div id="E3" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E3}); background-size: 50px 50px; height: 50px; width: 50px;">e3</div>
							<div id="F3" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F3}); background-size: 50px 50px; height: 50px; width: 50px;">f3</div>
							<div id="G3" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G3}); background-size: 50px 50px; height: 50px; width: 50px;">g3</div>
							<div id="H3" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H3}); background-size: 50px 50px; height: 50px; width: 50px;">h3</div>
						</div>
						<div class="row">
							<div id="A2" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A2}); background-size: 50px 50px; height: 50px; width: 50px;">a2</div>
							<div id="B2" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B2}); background-size: 50px 50px; height: 50px; width: 50px;">b2</div>
							<div id="C2" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C2}); background-size: 50px 50px; height: 50px; width: 50px;">c2</div>
							<div id="D2" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D2}); background-size: 50px 50px; height: 50px; width: 50px;">d2</div>
							<div id="E2" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E2}); background-size: 50px 50px; height: 50px; width: 50px;">e2</div>
							<div id="F2" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F2}); background-size: 50px 50px; height: 50px; width: 50px;">f2</div>
							<div id="G2" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G2}); background-size: 50px 50px; height: 50px; width: 50px;">g2</div>
							<div id="H2" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H2}); background-size: 50px 50px; height: 50px; width: 50px;">h2</div>
						</div>
						<div class="row">
							<div id="A1" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A1}); background-size: 50px 50px; height: 50px; width: 50px;">a1</div>
							<div id="B1" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B1}); background-size: 50px 50px; height: 50px; width: 50px;">b1</div>
							<div id="C1" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C1}); background-size: 50px 50px; height: 50px; width: 50px;">c1</div>
							<div id="D1" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D1}); background-size: 50px 50px; height: 50px; width: 50px;">d1</div>
							<div id="E1" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E1}); background-size: 50px 50px; height: 50px; width: 50px;">e1</div>
							<div id="F1" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F1}); background-size: 50px 50px; height: 50px; width: 50px;">f1</div>
							<div id="G1" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G1}); background-size: 50px 50px; height: 50px; width: 50px;">g1</div>
							<div id="H1" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H1}); background-size: 50px 50px; height: 50px; width: 50px;">h1</div>
						</div>		
					</div> <!-- field -->
			
					<div id="gridLetters">		
						<div class="row">
							<div class="cell cellLetters white">a</div>
							<div class="cell cellLetters white">b</div>
							<div class="cell cellLetters white">c</div>
							<div class="cell cellLetters white">d</div>
							<div class="cell cellLetters white">e</div>
							<div class="cell cellLetters white">f</div>
							<div class="cell cellLetters white">g</div>
							<div class="cell cellLettersLast white">h</div>
						</div>
					</div> <!-- gridLetters -->
			
					<div id="message" class="message" />
		      </td>
		
		      <!-- Testing column - Use it later for table with users and their movements  -->
		      <td> 
		         <b>Context path:</b> ${pageContext.request.contextPath} <br/>
                <!-- display cookie -->
                <b>User ID:</b> <c:out value="${cookie.playerId.value}" /> <br/>
                <!-- input type="hidden" id="playerid" name="playerid" value="${requestScope.playerId}" / -->
                <input id="playerid" name="playerid" value="${requestScope.playerId}" />
		         <p id="debug"></p>
		      </td>
	      <tr>
      </table>


      <script>
        // call AFTER page is loaded
        window.onload = init; 
        
        <!-- AJAX call to register async communication in servlet, and wait until a message is posted --> 

        var messagesWaiting = false;  
              
        function getMessages() {
            if(!messagesWaiting) {
                messagesWaiting = true;
                
                var xmlRequest = new XMLHttpRequest();
                xmlRequest.onreadystatechange = function() {
                    if (xmlRequest.readyState==4 && xmlRequest.status==200) {
                        messagesWaiting = false;
                        
                        clearPositions();
                        
                        // write given response
                        var contentElement = document.getElementById("message");
                        contentElement.innerHTML = xmlRequest.responseText + contentElement.innerHTML;
                    }
                }
                
                xmlRequest.open("GET", "Game?operation=start&playerid=" + escape(document.getElementById("playerid").value) + "&t=" + new Date(), true);
                xmlRequest.send();
            }
        }
        
        setInterval(getMessages, 1000);
      </script>
	</body>
</html>