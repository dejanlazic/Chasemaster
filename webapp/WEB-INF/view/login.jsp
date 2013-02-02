<!doctype html>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
  <meta charset="utf-8">  
  <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

	<!-- Use context relative (i.e. absolute) paths with forwarding -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>

	<title>Chasemaster: Login</title>
</head>

<body>
   <div id="main">
      <h1>Login</h1>
      
      <!-- INPUT FORM -->
      
    <div class="form">
      <form:form method="post" action="login" commandName="userCredentials" class="form-vertical">
         <form:label path="username">Username</form:label>
         <form:input path="username" />
         <form:label path="password">Password</form:label>
         <form:input path="password" />               
                        
         <input type="submit" value="Submit" class="btn" />
      </form:form>
    </div>
  
      <!-- 
      <a href="/Chasemaster/registrationForm" class="btn btn-primary">Register</a>
      <a href="/registrationForm" class="btn btn-primary">Register</a>
      <a href="/registrationForm/" class="btn btn-primary">Register</a>
      <a href="/Chasemaster/registrationForm/" class="btn btn-primary">Register</a>      
      <form:form method="get" action="registrationForm" class="form-vertical">
         <input type="submit" value="Submit" class="btn" />
      </form:form>                        
       -->
      <a href="/Chasemaster/web/registrationForm">Registration</a>
      
      <!-- OUTPUT DATA -->
      
      <h3><c:out value="${city.name}"/></h3>


      <c:if test="${!empty geoDataMap}">
         <h5>Geographic</h5>
                  
         <table class="table table-bordered table-striped">
            <tr>
               <td style="width: 50%;">Country name</td>
               <td style="width: 50%;"><c:out value="${geoDataMap.countryName}"/></td>
            </tr>
            <tr>
               <td>Country code</td>
               <td><c:out value="${geoDataMap.countryCode}"/></td>
            </tr>
         </table>                        
      </c:if>            
      
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