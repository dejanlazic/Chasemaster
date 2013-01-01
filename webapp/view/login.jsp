<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  
    pageEncoding="ISO-8859-1" isELIgnored="false"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">   

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<!-- Use context relative (i.e. absolute) paths with forwarding -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>

		<title>Chase Master: Login</title>
	</head>

	<body>
		<div id="main">
			TODO:<a href="<%=request.getContextPath()%>/Controller?action=#">Home</a> <br />		
		
			<h1>Login</h1>
			
			<div class="form">
				<form action="Controller?action=login" method="post" onSubmit="return validateLogin();">
					<label>Username</label><br />  
					<input type="text" name="username" id="username" value="Username" size="30" /><br />
					<label for="password">Password</label><br />
					<input type="text" name="password" id="password" value="Password" size="30" /><br />
					<input type="checkbox" name="loginAsPlayer" value="Player"><br />
					<a href="<%=request.getContextPath()%>/Controller?action=register_page">Register</a> <br />
					
					<input id="sendbtn" class="label" type="submit" value="Login" />
					<!-- button id="savecomment" class="label" onClick="addDiv();" --> <!-- Save</button -->
				</form>
			</div>
		</div>
	</body>
</html>