<!doctype html>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
  <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

	<!-- Use context relative (i.e. absolute) paths with forwarding -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
	<title>Chasemaster: Registration</title>
</head>

<body>
  <div id="main">
    <div class="form">
       <h1>Registration</h1>

        <form method="post" action="register.do" onSubmit="return validateRegistrationForm();">
          <table>
            <tr>
              <td>Username</td>
              <td><input type="text" id="username" name="username"/></td>
            </tr>
            <tr>
              <td>Password</td>
              <td><input type="password" id="password" name="password" /></td>
            </tr>
            <tr>
              <td>Confirm Password</td>
              <td><input type="password" id="passwordConfirmation" name="passwordConfirmation" /></td>
            </tr>
            <tr>
              <td colspan="2"><p id="message" style="color:red;"></p></td>
            </tr>
            <tr>
              <td colspan="2" align="left"><input type="submit" value="Submit" class="btn" /></td>
            </tr>
          </table>
        </form>

        <a href="/Chasemaster/loginForm.do">Login</a>
      </div>
    </div>
</body>
</html>