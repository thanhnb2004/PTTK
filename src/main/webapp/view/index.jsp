<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home - PTTKHT</title>
</head>
<body>
<h1>PTTKHT - JSP/Servlet Base</h1>
<ul>
    <li><a href="<%= request.getContextPath() %>/view/customerLogin.jsp">Customer login</a></li>
    <li><a href="<%= request.getContextPath() %>/view/staffLogin.jsp">Staff login</a></li>
    <li><a href="<%= request.getContextPath() %>/view/customerSignup.jsp">Customer signup</a></li>
</ul>
</body>
</html>


