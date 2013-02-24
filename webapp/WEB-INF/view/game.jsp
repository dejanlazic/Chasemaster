<html>

	<head>
		<title>Chasemaster: Game</title>

		<!-- Use context relative (i.e. absolute) paths with forwarding -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
	</head>

	<body>
      <table>
	      <tr>
		      <td>		   
					<div id="grid">		
						<div class="row">
							<div id="a8" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A8}); background-size: 50px 50px; height: 50px; width: 50px;">a8</div>
							<div id="b8" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B8}); background-size: 50px 50px; height: 50px; width: 50px;">b8</div>
							<div id="c8" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C8}); background-size: 50px 50px; height: 50px; width: 50px;">c8</div>
							<div id="d8" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D8}); background-size: 50px 50px; height: 50px; width: 50px;">d8</div>
							<div id="e8" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E8}); background-size: 50px 50px; height: 50px; width: 50px;">e8</div>
							<div id="f8" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F8}); background-size: 50px 50px; height: 50px; width: 50px;">f8</div>
							<div id="g8" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G8}); background-size: 50px 50px; height: 50px; width: 50px;">g8</div>
							<div id="h8" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H8}); background-size: 50px 50px; height: 50px; width: 50px;">h8</div>
						</div>
						<div class="row">
							<div id="a7" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A7}); background-size: 50px 50px; height: 50px; width: 50px;">a7</div>
							<div id="b7" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B7}); background-size: 50px 50px; height: 50px; width: 50px;">b7</div>
							<div id="c7" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C7}); background-size: 50px 50px; height: 50px; width: 50px;">c7</div>
							<div id="d7" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D7}); background-size: 50px 50px; height: 50px; width: 50px;">d7</div>
							<div id="e7" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E7}); background-size: 50px 50px; height: 50px; width: 50px;">e7</div>
							<div id="f7" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F7}); background-size: 50px 50px; height: 50px; width: 50px;">f7</div>
							<div id="g7" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G7}); background-size: 50px 50px; height: 50px; width: 50px;">g7</div>
							<div id="h7" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H7}); background-size: 50px 50px; height: 50px; width: 50px;">h7</div>
						</div>
						<div class="row">
							<div id="a6" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A6}); background-size: 50px 50px; height: 50px; width: 50px;">a6</div>
							<div id="b6" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B6}); background-size: 50px 50px; height: 50px; width: 50px;">b6</div>
							<div id="c6" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C6}); background-size: 50px 50px; height: 50px; width: 50px;">c6</div>
							<div id="d6" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D6}); background-size: 50px 50px; height: 50px; width: 50px;">d6</div>
							<div id="e6" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E6}); background-size: 50px 50px; height: 50px; width: 50px;">e6</div>
							<div id="f6" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F6}); background-size: 50px 50px; height: 50px; width: 50px;">f6</div>
							<div id="g6" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G6}); background-size: 50px 50px; height: 50px; width: 50px;">g6</div>
							<div id="h6" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H6}); background-size: 50px 50px; height: 50px; width: 50px;">h6</div>
						</div>
						<div class="row">
							<div id="a5" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A5}); background-size: 50px 50px; height: 50px; width: 50px;">a5</div>
							<div id="b5" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B5}); background-size: 50px 50px; height: 50px; width: 50px;">b5</div>
							<div id="c5" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C5}); background-size: 50px 50px; height: 50px; width: 50px;">c5</div>
							<div id="d5" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D5}); background-size: 50px 50px; height: 50px; width: 50px;">d5</div>
							<div id="e5" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E5}); background-size: 50px 50px; height: 50px; width: 50px;">e5</div>
							<div id="f5" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F5}); background-size: 50px 50px; height: 50px; width: 50px;">f5</div>
							<div id="g5" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G5}); background-size: 50px 50px; height: 50px; width: 50px;">g5</div>
							<div id="h5" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H5}); background-size: 50px 50px; height: 50px; width: 50px;">h5</div>
						</div>
						<div class="row">
							<div id="a4" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A4}); background-size: 50px 50px; height: 50px; width: 50px;">a4</div>
							<div id="b4" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B4}); background-size: 50px 50px; height: 50px; width: 50px;">b4</div>
							<div id="c4" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C4}); background-size: 50px 50px; height: 50px; width: 50px;">c4</div>
							<div id="d4" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D4}); background-size: 50px 50px; height: 50px; width: 50px;">d4</div>
							<div id="e4" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E4}); background-size: 50px 50px; height: 50px; width: 50px;">e4</div>
							<div id="f4" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F4}); background-size: 50px 50px; height: 50px; width: 50px;">f4</div>
							<div id="g4" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G4}); background-size: 50px 50px; height: 50px; width: 50px;">g4</div>
							<div id="h4" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H4}); background-size: 50px 50px; height: 50px; width: 50px;">h4</div>
						</div>
						<div class="row">
							<div id="a3" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A3}); background-size: 50px 50px; height: 50px; width: 50px;">a3</div>
							<div id="b3" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B3}); background-size: 50px 50px; height: 50px; width: 50px;">b3</div>
							<div id="c3" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C3}); background-size: 50px 50px; height: 50px; width: 50px;">c3</div>
							<div id="d3" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D3}); background-size: 50px 50px; height: 50px; width: 50px;">d3</div>
							<div id="e3" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E3}); background-size: 50px 50px; height: 50px; width: 50px;">e3</div>
							<div id="f3" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F3}); background-size: 50px 50px; height: 50px; width: 50px;">f3</div>
							<div id="g3" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G3}); background-size: 50px 50px; height: 50px; width: 50px;">g3</div>
							<div id="h3" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H3}); background-size: 50px 50px; height: 50px; width: 50px;">h3</div>
						</div>
						<div class="row">
							<div id="a2" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A2}); background-size: 50px 50px; height: 50px; width: 50px;">a2</div>
							<div id="b2" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B2}); background-size: 50px 50px; height: 50px; width: 50px;">b2</div>
							<div id="c2" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C2}); background-size: 50px 50px; height: 50px; width: 50px;">c2</div>
							<div id="d2" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D2}); background-size: 50px 50px; height: 50px; width: 50px;">d2</div>
							<div id="e2" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E2}); background-size: 50px 50px; height: 50px; width: 50px;">e2</div>
							<div id="f2" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F2}); background-size: 50px 50px; height: 50px; width: 50px;">f2</div>
							<div id="g2" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G2}); background-size: 50px 50px; height: 50px; width: 50px;">g2</div>
							<div id="h2" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H2}); background-size: 50px 50px; height: 50px; width: 50px;">h2</div>
						</div>
						<div class="row">
							<div id="a1" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.A1}); background-size: 50px 50px; height: 50px; width: 50px;">a1</div>
							<div id="b1" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.B1}); background-size: 50px 50px; height: 50px; width: 50px;">b1</div>
							<div id="c1" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.C1}); background-size: 50px 50px; height: 50px; width: 50px;">c1</div>
							<div id="d1" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.D1}); background-size: 50px 50px; height: 50px; width: 50px;">d1</div>
							<div id="e1" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.E1}); background-size: 50px 50px; height: 50px; width: 50px;">e1</div>
							<div id="f1" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.F1}); background-size: 50px 50px; height: 50px; width: 50px;">f1</div>
							<div id="g1" class="cell black" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.G1}); background-size: 50px 50px; height: 50px; width: 50px;">g1</div>
							<div id="h1" class="cell white" style="background-image: url(${pageContext.request.contextPath}/img/${sessionScope.pieces.H1}); background-size: 50px 50px; height: 50px; width: 50px;">h1</div>
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
			
					<!-- TODO: Check if div is necessary -->
					<div class="message">
						<input type="text" readonly id="message" class="message">
					</div>		
		      </td>
		
		      <!-- Testing column - Use it later for table with users and their movements  -->
		      <td> 
		         <b>Context path:</b> ${pageContext.request.contextPath} <br/>
               <b>User ID:</b> ${sessionScope.userId}

		         <p id="msg"></p>
		      </td>
	      <tr>
      </table>

      <script>
        // call AFTER page is loaded
        window.onload = init; 
      </script>
	</body>
</html>