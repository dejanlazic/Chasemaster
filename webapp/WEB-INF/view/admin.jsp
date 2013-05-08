<!doctype html>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
  <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

	<!-- Use context relative (i.e. absolute) paths with forwarding -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
	<title>Chasemaster: Administration</title>
</head>

<body>
  <div id=main>
      <h1>Administration</h1>
      
      <!-- layout (2 columns) -->      
      <table width="100%">       
        <tr>
          <th width="25%">Matches</th>
          <th width="75%">Movements</th>
        </tr>
 
        <tr>
          <!-- Left column (menu) -->
          <td>
            <c:if test="${!empty matchesList}">
              <c:forEach var="match" items="${matchesList}" varStatus="counter">
                <a href="/Chasemaster/getMatch.do?id=${match.id}">
                  <fmt:formatDate value="${match.playOn}" pattern="dd/MM/yyyy HH:mm:ss"/></a><br/>
              </c:forEach>
            </c:if>

            <p><a href="/Chasemaster/createMatch.do">New match</a></p>
          </td>

          <!-- Right column (content) -->
          <td>
            <c:if test="${!empty match}">
               <c:forEach var="turn" items="${match.turns}" varStatus="counterMatch">
                   <!-- counterMatch.count = counterMatch.index + 1 -->
                  Turn #<c:out value="${counterMatch.count}"/> <br/>
                  <table border="1" cellspacing="2" cellpadding="2">
                    <tr>
                      <th width="25%">Player</th>
                      <th width="25%">From</th>
                      <th width="25%">To</th>
                      <th width="25%">Duration</th>
                    </tr>
                    <c:forEach var="movement" items="${turn.movements}" varStatus="counterMov">
                      <tr>
                        <td><c:out value="${movement.playerId}"/></td>
                        <td><c:out value="${movement.from}"/></td>
                        <td><c:out value="${movement.to}"/></td>
                        <td><c:out value="${movement.duration}"/></td>                        
                      </tr>
                    </c:forEach>
                  </table>
                </c:forEach>
            </c:if>
          </td>
        </tr>
      </table>     
  </div>            
</body>
</html>