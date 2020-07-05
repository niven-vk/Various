<!DOCTYPE html>
<%@ page import ='com.rec.zadanie4.DBService' %>
<html lang="en">

<head>

<meta charset="UTF-8">

<title>Hello </title>

</head>

<body>
<form action="/home">
		<input type="submit" value="Go back" />
	</form>
${param.ip} ${param.country }
<br/>
<%
DBService.add_row(
		request.getParameter("ip"),
		request.getParameter("batch_id"),
		request.getParameter("country"),
		request.getParameter("code"),
		request.getParameter("code3"),
		request.getParameter("emoji")
		);
%>
	<h2>Data added successfully!</h2>
	

</html>