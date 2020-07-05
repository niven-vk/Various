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

	<h2>Data from ip_out table:</h2>
	<%= DBService.get_ip() %>

</html>