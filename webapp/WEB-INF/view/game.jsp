<html>

	<head>
		<title>Chase Master: Game</title>

		<!-- Use context relative (i.e. absolute) paths with forwarding -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
	</head>

	<body>
		<script type="text/javascript">
			<!-- hide script from older browsers
				document.write("<h1>Chasemaster</h1>");
			// end hiding script --> 
		</script>

      <table>
	      <tr>
		      <td>		   
					<div id="grid">		
						<div class="row">
							<div id="a8" class="cell white">a8</div>
							<div id="b8" class="cell black">b8</div>
							<div id="c8" class="cell white">c8</div>
							<div id="d8" class="cell black">d8</div>
							<div id="e8" class="cell white">e8</div>
							<div id="f8" class="cell black">f8</div>
							<div id="g8" class="cell white">g8</div>
							<div id="h8" class="cell black">h8</div>
						</div>
						<div class="row">
							<div id="a7" class="cell black">a7</div>
							<div id="b7" class="cell white">b7</div>
							<div id="c7" class="cell black">c7</div>
							<div id="d7" class="cell white">d7</div>
							<div id="e7" class="cell black">e7</div>
							<div id="f7" class="cell white">f7</div>
							<div id="g7" class="cell black">g7</div>
							<div id="h7" class="cell white">h7</div>
						</div>
						<div class="row">
							<div id="a6" class="cell white">a6</div>
							<div id="b6" class="cell black">b6</div>
							<div id="c6" class="cell white">c6</div>
							<div id="d6" class="cell black">d6</div>
							<div id="e6" class="cell white">e6</div>
							<div id="f6" class="cell black">f6</div>
							<div id="g6" class="cell white">g6</div>
							<div id="h6" class="cell black">h6</div>
						</div>
						<div class="row">
							<div id="a5" class="cell black">a5</div>
							<div id="b5" class="cell white">b5</div>
							<div id="c5" class="cell black">c5</div>
							<div id="d5" class="cell white">d5</div>
							<div id="e5" class="cell black">e5</div>
							<div id="f5" class="cell white">f5</div>
							<div id="g5" class="cell black">g5</div>
							<div id="h5" class="cell white">h5</div>
						</div>
						<div class="row">
							<div id="a4" class="cell white">a4</div>
							<div id="b4" class="cell black">b4</div>
							<div id="c4" class="cell white">c4</div>
							<div id="d4" class="cell black">d4</div>
							<div id="e4" class="cell white">e4</div>
							<div id="f4" class="cell black">f4</div>
							<div id="g4" class="cell white">g4</div>
							<div id="h4" class="cell black">h4</div>
						</div>
						<div class="row">
							<div id="a3" class="cell black">a3</div>
							<div id="b3" class="cell white">b3</div>
							<div id="c3" class="cell black">c3</div>
							<div id="d3" class="cell white">d3</div>
							<div id="e3" class="cell black">e3</div>
							<div id="f3" class="cell white">f3</div>
							<div id="g3" class="cell black">g3</div>
							<div id="h3" class="cell white">h3</div>
						</div>
						<div class="row">
							<div id="a2" class="cell white">a2</div>
							<div id="b2" class="cell black">b2</div>
							<div id="c2" class="cell white">c2</div>
							<div id="d2" class="cell black">d2</div>
							<div id="e2" class="cell white">e2</div>
							<div id="f2" class="cell black">f2</div>
							<div id="g2" class="cell white">g2</div>
							<div id="h2" class="cell black"><a href="#" id="h2">h2</a></div>
						</div>
						<div class="row">
							<div id="a1" class="cell black" onclick="moveHandler('a1');">a1</div>
							<div id="b1" class="cell white" onclick="moveHandler('b1');">b1</div>
							<div id="c1" class="cell black" onclick="moveHandler('c1');">c1</div>
							<div id="d1" class="cell white" onclick="moveHandler('d1');">d1</div>
							<div id="e1" class="cell black" onclick="moveHandler('e1');">e1</div>
							<div id="f1" class="cell white" onclick="moveHandler('f1');">f1</div>
							<div id="g1" class="cell black" onclick="moveHandler('g1');">g1</div>
							<div id="h1" class="cell white" onclick="moveHandler('h1');">h1</div>
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
		
		      <!-- Testing column - REMOVE -->
		      <td> 
		         <b>Context path:</b> ${pageContext.request.contextPath} <br/>
               <b>User ID:</b> ${sessionScope.userId}

		         <p id="msg"></p>
		      </td>
	      <tr>
      </table>
	</body>
</html>