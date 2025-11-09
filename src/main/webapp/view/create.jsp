<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tạo người dùng</title>
</head>
<body>
<h2>Tạo người dùng</h2>
<p><a href="/">Về trang chủ</a> | <a href="/users">Danh sách</a></p>

<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
  </c:if>

<form method="post" action="/users">
    <div>
        <label>Tên</label><br/>
        <input name="name" placeholder="Nhập tên" />
    </div>
    <div>
        <label>Email</label><br/>
        <input name="email" type="email" placeholder="Nhập email" />
    </div>
    <div style="margin-top: 8px;">
        <button type="submit">Lưu</button>
    </div>
</form>

</body>
</html>


