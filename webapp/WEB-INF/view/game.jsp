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
					<div id="board">		
						<div id="row8" class="row">
                      <!-- NOTE: Calling a method with parameter in EL is Servlet 3.0 feature -->
							<div id="A8" class="cell white" style='background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.A8}); background-size: 50px 50px; height: 50px; width: 50px;'>a8</div>
							<div id="B8" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.B8}); background-size: 50px 50px; height: 50px; width: 50px;">b8</div>
							<div id="C8" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.C8}); background-size: 50px 50px; height: 50px; width: 50px;">c8</div>
							<div id="D8" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.D8}); background-size: 50px 50px; height: 50px; width: 50px;">d8</div>
							<div id="E8" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.E8}); background-size: 50px 50px; height: 50px; width: 50px;">e8</div>
							<div id="F8" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.F8}); background-size: 50px 50px; height: 50px; width: 50px;">f8</div>
							<div id="G8" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.G8}); background-size: 50px 50px; height: 50px; width: 50px;">g8</div>
							<div id="H8" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.H8}); background-size: 50px 50px; height: 50px; width: 50px;">h8</div>
						</div>
						<div id="row7" class="row">
							<div id="A7" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.A7}); background-size: 50px 50px; height: 50px; width: 50px;">a7</div>
							<div id="B7" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.B7}); background-size: 50px 50px; height: 50px; width: 50px;">b7</div>
							<div id="C7" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.C7}); background-size: 50px 50px; height: 50px; width: 50px;">c7</div>
							<div id="D7" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.D7}); background-size: 50px 50px; height: 50px; width: 50px;">d7</div>
							<div id="E7" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.E7}); background-size: 50px 50px; height: 50px; width: 50px;">e7</div>
							<div id="F7" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.F7}); background-size: 50px 50px; height: 50px; width: 50px;">f7</div>
							<div id="G7" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.G7}); background-size: 50px 50px; height: 50px; width: 50px;">g7</div>
							<div id="H7" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.H7}); background-size: 50px 50px; height: 50px; width: 50px;">h7</div>
						</div>
						<div id="row6" class="row">
							<div id="A6" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.A6}); background-size: 50px 50px; height: 50px; width: 50px;">a6</div>
							<div id="B6" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.B6}); background-size: 50px 50px; height: 50px; width: 50px;">b6</div>
							<div id="C6" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.C6}); background-size: 50px 50px; height: 50px; width: 50px;">c6</div>
							<div id="D6" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.D6}); background-size: 50px 50px; height: 50px; width: 50px;">d6</div>
							<div id="E6" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.E6}); background-size: 50px 50px; height: 50px; width: 50px;">e6</div>
							<div id="F6" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.F6}); background-size: 50px 50px; height: 50px; width: 50px;">f6</div>
							<div id="G6" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.G6}); background-size: 50px 50px; height: 50px; width: 50px;">g6</div>
							<div id="H6" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.H6}); background-size: 50px 50px; height: 50px; width: 50px;">h6</div>
						</div>
						<div id="row5" class="row">
							<div id="A5" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.A5}); background-size: 50px 50px; height: 50px; width: 50px;">a5</div>
							<div id="B5" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.B5}); background-size: 50px 50px; height: 50px; width: 50px;">b5</div>
							<div id="C5" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.C5}); background-size: 50px 50px; height: 50px; width: 50px;">c5</div>
							<div id="D5" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.D5}); background-size: 50px 50px; height: 50px; width: 50px;">d5</div>
							<div id="E5" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.E5}); background-size: 50px 50px; height: 50px; width: 50px;">e5</div>
							<div id="F5" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.F5}); background-size: 50px 50px; height: 50px; width: 50px;">f5</div>
							<div id="G5" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.G5}); background-size: 50px 50px; height: 50px; width: 50px;">g5</div>
							<div id="H5" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.H5}); background-size: 50px 50px; height: 50px; width: 50px;">h5</div>
						</div>
						<div id="row4" class="row">
							<div id="A4" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.A4}); background-size: 50px 50px; height: 50px; width: 50px;">a4</div>
							<div id="B4" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.B4}); background-size: 50px 50px; height: 50px; width: 50px;">b4</div>
							<div id="C4" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.C4}); background-size: 50px 50px; height: 50px; width: 50px;">c4</div>
							<div id="D4" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.D4}); background-size: 50px 50px; height: 50px; width: 50px;">d4</div>
							<div id="E4" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.E4}); background-size: 50px 50px; height: 50px; width: 50px;">e4</div>
							<div id="F4" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.F4}); background-size: 50px 50px; height: 50px; width: 50px;">f4</div>
							<div id="G4" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.G4}); background-size: 50px 50px; height: 50px; width: 50px;">g4</div>
							<div id="H4" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.H4}); background-size: 50px 50px; height: 50px; width: 50px;">h4</div>
						</div>
						<div id="row3" class="row">
							<div id="A3" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.A3}); background-size: 50px 50px; height: 50px; width: 50px;">a3</div>
							<div id="B3" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.B3}); background-size: 50px 50px; height: 50px; width: 50px;">b3</div>
							<div id="C3" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.C3}); background-size: 50px 50px; height: 50px; width: 50px;">c3</div>
							<div id="D3" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.D3}); background-size: 50px 50px; height: 50px; width: 50px;">d3</div>
							<div id="E3" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.E3}); background-size: 50px 50px; height: 50px; width: 50px;">e3</div>
							<div id="F3" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.F3}); background-size: 50px 50px; height: 50px; width: 50px;">f3</div>
							<div id="G3" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.G3}); background-size: 50px 50px; height: 50px; width: 50px;">g3</div>
							<div id="H3" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.H3}); background-size: 50px 50px; height: 50px; width: 50px;">h3</div>
						</div>
						<div id="row2" class="row">
							<div id="A2" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.A2}); background-size: 50px 50px; height: 50px; width: 50px;">a2</div>
							<div id="B2" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.B2}); background-size: 50px 50px; height: 50px; width: 50px;">b2</div>
							<div id="C2" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.C2}); background-size: 50px 50px; height: 50px; width: 50px;">c2</div>
							<div id="D2" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.D2}); background-size: 50px 50px; height: 50px; width: 50px;">d2</div>
							<div id="E2" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.E2}); background-size: 50px 50px; height: 50px; width: 50px;">e2</div>
							<div id="F2" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.F2}); background-size: 50px 50px; height: 50px; width: 50px;">f2</div>
							<div id="G2" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.G2}); background-size: 50px 50px; height: 50px; width: 50px;">g2</div>
							<div id="H2" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.H2}); background-size: 50px 50px; height: 50px; width: 50px;">h2</div>
						</div>
						<div id="row1" class="row">
							<div id="A1" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.A1}); background-size: 50px 50px; height: 50px; width: 50px;">a1</div>
							<div id="B1" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.B1}); background-size: 50px 50px; height: 50px; width: 50px;">b1</div>
							<div id="C1" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.C1}); background-size: 50px 50px; height: 50px; width: 50px;">c1</div>
							<div id="D1" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.D1}); background-size: 50px 50px; height: 50px; width: 50px;">d1</div>
							<div id="E1" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.E1}); background-size: 50px 50px; height: 50px; width: 50px;">e1</div>
							<div id="F1" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.F1}); background-size: 50px 50px; height: 50px; width: 50px;">f1</div>
							<div id="G1" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.G1}); background-size: 50px 50px; height: 50px; width: 50px;">g1</div>
							<div id="H1" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${applicationScope.boardImages.H1}); background-size: 50px 50px; height: 50px; width: 50px;">h1</div>
						</div>		
					</div> <!-- field -->
			
					<div id="boardLetters">		
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
		      </td>
		
		      <!-- Performed movements column (make a table) -->
		      <td align="left" valign="top">
              <div id="message" class="message" /> 
		      </td>
	      <tr>

          <tr>
            <!-- Testing column - Use it later for table with users and their movements  -->
            <td>			
                <!-- input type="hidden" id="playerid" name="playerid" value="${requestScope.playerId}" / -->
                <input id="playerid" name="playerid" value="${requestScope.playerId}" />
               <p id="debug"></p>
            </td>
          </tr>
      </table>


      <script>
        <!--
        // call AFTER page is loaded
        window.onload = init; 
        
        // AJAX call to register async communication in servlet, and wait until a message is posted  

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

                        var data = eval('(' + xmlRequest.responseText + ')');                        
                        var movementFrom = data.movementFrom;
                        var movementTo = data.movementTo;

                        var content = '<b>From:</b>' + movementFrom + '<br/>';                                   
                        content += '<b>To  :</b>' + movementTo;
                        content += '<h3>Winning players</h3>';           
                        content += '<table><thead>';
                        content += '<tr><th>From</th>';
                        content += '<th>To</th>';
                        content += '<th>ID</th>';
                        content += '</tr></thead><tbody>';
                        var players = data.winningPlayers;
                        for(var i = 0; i < players.length; i++) {
                          content += '<tr><td>' + movementFrom + '</td>';
                          content += '<td>' + movementTo + '</td>';
                          content += '<td>' + players[i] + '</td></tr>';
                        }
                        content += '</tbody></table>';

                        content += '<h3>Losing players</h3>';           
                        content += '<table><thead>';
                        content += '<tr><th>From</th>';
                        content += '<th>To</th>';
                        content += '<th>ID</th>';
                        content += '</tr></thead><tbody>';
                        var losingPlayers = data.losingPlayers;
                        for(var j = 0; j < losingPlayers.length; j++) {
                          content += '<tr><td>?' + '</td>';
                          content += '<td>?' + '</td>';
                          content += '<td>' + losingPlayers[j] + '</td></tr>';

                          if(losingPlayers[j] == document.getElementById("playerid").value) {
                            document.getElementById("board").style.display = 'none';
                            document.getElementById("boardLetters").style.display = 'none';
                          } 
                        }
                        content += '</tbody></table>';
                        
                        document.getElementById("message").innerHTML = content;

                        // move icon on the board
                        var _img = document.getElementById(movementFrom);
                        //alert(_img.id);
                        //alert(_img.style['background-image']);
                        
                        return false;
                    }
                }
                
                xmlRequest.open("GET", "Game?operation=start&playerid=" + escape(document.getElementById("playerid").value) + "&t=" + new Date(), true);
                xmlRequest.send();
            }
        }
        
        setInterval(getMessages, 1000);
      -->
      </script>
	</body>
</html>