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
<!-- 
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
-->
	<title>Chasemaster: Registration</title>
</head>

<body>
  <div id="main">
     <h1>Login</h1>

     <!-- INPUT FORM -->
    <div class="form">
        <form:form method="post" action="register" commandName="user" class="form-vertical">
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

        <a href="/Chasemaster/web/">Login</a>
      </div>
    </div>
</body>
</html>