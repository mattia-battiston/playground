<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Hello World</title>
</head>
<body>
   <h2>${message}</h2>

   <form:form method="post" action="sayHello">

               <form:label path="usernameLabel">Username</form:label>
               <form:input path="username" />
               <input type="submit" value="Say Hello"/>

   </form:form>

</body>
</html>