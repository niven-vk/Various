<!DOCTYPE html>

<html lang="en">

<head>

<meta charset="UTF-8">


</head>

<body>

	<p>
		The time is
		<%=new java.util.Date()%>
	<form action="show-ip-out.jsp">
		<input type="submit" name="button1" value="Show ip Out" />
	</form>
	<br/>
	<form action="show-countries.jsp">
		<input type="submit" name="button1" value="Show countries" />
	</form>
	<br/>
	<form action="add-data.jsp" method="post">
		<input type="text" name="ip" value="ip" /> 
		<input type="text" name="batch_id" value="batch_id" /> 
		<input type="text" name="country" value="country name" /> 
		<input type="text" name="code" value="code" /> 
		<input type="text" name="code3" value="code3" /> 
		<input type="text" name="emoji" value="emoji" /> 
		<input type="submit" value="Submit" />
	</form>
	<br/>
	<form action="show-report.jsp">
		<input type="submit" name="button1" value="Show report" />
	</form>
</body>

</html>