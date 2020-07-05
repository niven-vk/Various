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

	<h2>Report:</h2>
	<%= DBService.get_report() %>

</html>