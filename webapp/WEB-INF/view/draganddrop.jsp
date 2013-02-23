<html>
	<head>
		<title>Chasemaster: Game</title>

		<!-- Use context relative (i.e. absolute) paths with forwarding -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/dnd.js"></script>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/dnd2.js"></script>
	</head>

  <body>
    <!-- Example 1 -->
    <div id="list" style="position:relative;border:solid 1px black;width:150px;">
      <div id="e1" style="height:50px;background-color:Green;" class="list"></div>
      <div id="e2" style="height:140px;background-color:Blue;" class="list"></div>
      <div id="e3" style="height:100px;background-color:Red;" class="list"></div>
      <div id="e4" style="height:30px;background-color:Yellow;" class="list"></div>
      <div id="e5" style="height:70px;background-color:Fuchsia" class="list"></div>
    </div>

    <!-- Example 2 -->
    <div id="pageContainer">
      <div class="drag">Item 1 is &lt;div&gt;</div>
      <div class="drag">Item 2 is &lt;div&gt;</div>
      <img class="drag" src="drag_image.png" alt="drag image" />
      <pre id="debug"> </pre>
    </div>
  </body>
</html>