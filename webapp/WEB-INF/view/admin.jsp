<!doctype html>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
   <div id="main">
      <h1>Administration</h1>
      
      <!-- INPUT FORM -->
      
      <div class="form">
        <form method="post" action="login.do" onSubmit="return validateLoginForm();">
            <table>
              <tr>
                <td align="left"><input type="submit" value="Submit" class="btn" /></td>
              </tr>
            </table>
        </form>
      </div>

      <!-- layout (2 columns) -->      
      <table>
        <tr>
          <!-- Left column (menu) -->
          <td>
            <h1>Matches</h1>
            <c:if test="${!empty matchesList}">
              <c:forEach var="matchData" items="${matchesList}" varStatus="counter">
                <a href="/Chasemaster/getMatch.do?id=${matchData.id}"><c:out value="${matchData.playOn}"/></a><br/>
              </c:forEach>
            </c:if>

            <p><a href="/Chasemaster/createMatch.do">New match</a></p>
          </td>

          <!-- Right column (content) -->
          <td>
          </td>
        </tr>
      </table>

      <!-- OUTPUT DATA -->      

      <c:if test="${!empty dataList}">
         <h5>Weather</h5>
                  
         <c:forEach var="weatherData" items="${dataList}" varStatus="counter">
            <c:choose>
               <c:when test="${counter.first}">
                  <table class="table table-bordered table-striped">
                     <thead>
                        <tr>
                           <th style="width: 50%;">Current conditions</th>
                           <th style="width: 50%;">&nbsp;</th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr>
                           <td>Pressure</td>
                           <td><c:out value="${weatherData.pressure}"/></td>
                        </tr>
                     </tbody>
                  </table>                        
               </c:when>
               <c:otherwise>
                  <table class="table table-bordered table-striped">
                     <thead>
                        <tr>
                           <th style="width: 50%;">Date</th>
                           <th style="width: 50%;"><c:out value="${weatherData.date}"/></th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr>
                           <td>Temperature min. (C)</td>
                           <td><c:out value="${weatherData.tempMinC}"/></td>
                        </tr>
                        <tr>
                           <td>Temperature max. (C)</td>
                           <td><c:out value="${weatherData.tempMaxC}"/></td>
                        </tr>                              
                     </tbody>
                  </table>
               </c:otherwise>
            </c:choose>
         </c:forEach>               
      </c:if>                      
   </div>
</body>
</html>