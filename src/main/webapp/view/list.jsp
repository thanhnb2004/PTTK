<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Danh sách người dùng</title>
    <style>
        table, th, td { border: 1px solid #ccc; border-collapse: collapse; padding: 8px; }
    </style>
  </head>
<body>
<h2>Danh sách người dùng</h2>
<p><a href="/">Về trang chủ</a> | <a href="/view/create.jsp">Tạo người dùng</a></p>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Chi tiết</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="u" items="${users}">
        <tr>
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.email}</td>
            <td><a href="/users?id=${u.id}">Xem</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>


