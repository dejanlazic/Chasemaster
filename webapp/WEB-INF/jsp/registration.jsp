<!doctype html>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<title>Registration</title>

<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet">
<link
  href="http://twitter.github.com/bootstrap/assets/css/bootstrap-responsive.css" rel="stylesheet">

<link href="http://heroku.github.com/template-app-bootstrap/heroku.css"
  rel="stylesheet">
</head>

<body>
  <div class="container">
    <div class="row">
      <div class="span8 offset2">
        <div class="page-header">
          <h1>Login</h1>
        </div>

        <!-- INPUT FORM -->

        <form:form method="post" action="register" commandName="user"
          class="form-vertical">
          <!-- 
          <form:label path="id">Id</form:label>
          <form:input path="id" readonly="true"/>
           -->          
          <form:label path="username">Username</form:label>
          <form:input path="username" />
          <form:label path="password">Password</form:label>
          <form:input path="password" />
          <form:label path="passwordConfirmation">Confirm Password</form:label>
          <form:input path="passwordConfirmation" />
          <form:label path="email">E-mail</form:label>
          <form:input path="email" />

          <input type="submit" value="Submit" class="btn" />
        </form:form>

        <a href="/Chasemaster/">Login</a>

        <!-- OUTPUT DATA -->

        <h3>
          <c:out value="${city.name}" />
        </h3>


        <c:if test="${!empty geoDataMap}">
          <h5>Geographic</h5>

          <table class="table table-bordered table-striped">
            <tr>
              <td style="width: 50%;">Country name</td>
              <td style="width: 50%;"><c:out
                  value="${geoDataMap.countryName}" /></td>
            </tr>
            <tr>
              <td>Country code</td>
              <td><c:out value="${geoDataMap.countryCode}" /></td>
            </tr>
            <tr>
              <td>Description</td>
              <td><c:out value="${geoDataMap.fcodeName}" /></td>
            </tr>
            <tr>
              <td>Longitude</td>
              <td><c:out value="${geoDataMap.longitude}" /></td>
            </tr>
            <tr>
              <td>Latitude</td>
              <td><c:out value="${geoDataMap.latitude}" /></td>
            </tr>
            <tr>
              <td>Population</td>
              <td><c:out value="${geoDataMap.population}" /></td>
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
                      <td><c:out value="${weatherData.pressure}" /></td>
                    </tr>
                    <tr>
                      <td>Temperature (C)</td>
                      <td><c:out value="${weatherData.tempC}" /></td>
                    </tr>
                    <tr>
                      <td>Temperature (F)</td>
                      <td><c:out value="${weatherData.tempF}" /></td>
                    </tr>
                    <tr>
                      <td>Visibility</td>
                      <td><c:out value="${weatherData.visibility}" /></td>
                    </tr>
                    <tr>
                      <td>Humidity</td>
                      <td><c:out value="${weatherData.humidity}" /></td>
                    </tr>
                    <tr>
                      <td>Description</td>
                      <td><font color="#FF0000"><c:out
                            value="${weatherData.weatherDesc}" /></font></td>
                    </tr>
                  </tbody>
                </table>
              </c:when>
              <c:otherwise>
                <table class="table table-bordered table-striped">
                  <thead>
                    <tr>
                      <th style="width: 50%;">Date</th>
                      <th style="width: 50%;"><c:out
                          value="${weatherData.date}" /></th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>Temperature min. (C)</td>
                      <td><c:out value="${weatherData.tempMinC}" /></td>
                    </tr>
                    <tr>
                      <td>Temperature max. (C)</td>
                      <td><c:out value="${weatherData.tempMaxC}" /></td>
                    </tr>
                    <tr>
                      <td>Temperature min. (F)</td>
                      <td><c:out value="${weatherData.tempMinF}" /></td>
                    </tr>
                    <tr>
                      <td>Temperature max. (F)</td>
                      <td><c:out value="${weatherData.tempMaxF}" /></td>
                    </tr>
                    <tr>
                      <td>Wind speed (Kmph)</td>
                      <td><c:out value="${weatherData.windspeedKmph}" /></td>
                    </tr>
                    <tr>
                      <td>Wind speed (Mph)</td>
                      <td><c:out value="${weatherData.windspeedMiles}" /></td>
                    </tr>
                    <tr>
                      <td>Description</td>
                      <td><font color="#FF0000"><c:out
                            value="${weatherData.weatherDesc}" /></font></td>
                    </tr>
                  </tbody>
                </table>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </c:if>
      </div>
    </div>
  </div>
</body>
</html>