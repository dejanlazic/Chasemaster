<!doctype html>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
  <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

	<!-- Use context relative (i.e. absolute) paths with forwarding -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
	<title>Chasemaster: Login</title>
</head>

<body>
   <div id="main">
      <!-- INPUT FORM -->
      
      <div class="form">
        <h1>Login</h1>
      
        <form method="post" action="login.do" onSubmit="return validateLoginForm();">
            <table>
              <tr>
                <td>Username</td>
              </tr>
              <tr>
                <td><input id="username" name="username" value="player01" /></td>
              </tr>
              <tr>
                <td>Password</td>
              </tr>
              <tr>
                <td><input id="password" name="password" value="player01" /></td>
              </tr>
              <tr>
                <td><p id="message" style="color:red;"></p></td>
              </tr>
              <tr>
                <td align="left"><input type="submit" value="Submit" class="btn" /></td>
              </tr>
            </table>
        </form>

        <a href="/Chasemaster/registrationForm.do">Registration</a>
      </div>
   </div>
</body>
</html>