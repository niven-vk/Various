<!DOCTYPE html>
<%@ page import ='com.rec.zadanie4.DBService' %>
<html lang="en">

<head>

<meta charset="UTF-16">

<title>Hello </title>

</head>

<body>
<form action="/home">
		<input type="submit" value="Go back" />
	</form>
	<h2>Data from countries table:</h2>
	<%= DBService.getCountries() %>

</html>