<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="resources/style.css">
</head>

<body>
	<h2>Customer SignUp Form</h2>

	<form:form method="POST" commandName="customer" action="customer/signup">
		<form:errors path="*" cssClass="errorblock" element="div" />
		<table>
			<tr>
				<td>Customer Name :</td>
				<td><form:input path="name" /></td>
			</tr>
			<tr>
				<td>Customer Name :</td>
				<td><form:input path="surname" /></td>
			</tr>
			<tr>
				<td>Customer Age :</td>
				<td><form:input path="age" /></td>
			</tr>
			<tr>
				<td colspan="3"><input type="submit" /></td>
			</tr>
		</table>
	</form:form>

</body>
</html>