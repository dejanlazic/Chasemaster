<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<!-- Use context relative (i.e. absolute) paths with forwarding -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>

		<title>Chase Master: Error Page</title>
	</head>

	<body>
		<div class="message"><c:out value="${requestScope.errMessage}"/></div>
	</body>
</html>