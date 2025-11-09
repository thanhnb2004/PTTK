<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết người dùng</title>
</head>
<body>
<h2>Chi tiết người dùng</h2>
<p><a href="/">Về trang chủ</a> | <a href="/users">Danh sách</a></p>

<c:choose>
    <c:when test="${not empty user}">
        <ul>
            <li><strong>ID:</strong> ${user.id}</li>
            <li><strong>Tên:</strong> ${user.name}</li>
            <li><strong>Email:</strong> ${user.email}</li>
        </ul>
    </c:when>
    <c:otherwise>
        <p>Không tìm thấy người dùng.</p>
    </c:otherwise>
  </c:choose>

</body>
</html>


