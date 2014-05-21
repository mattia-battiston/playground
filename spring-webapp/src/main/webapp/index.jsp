<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Hello World</title>
</head>
<body>
   <h2>${message}</h2>

   <form:form  modelAttribute="customer" method="GET" action="registerCustomer">
       Username: <form:input path="username" />
       <input type="submit" value="Say Hello"/>
   </form:form>

</body>
</html>