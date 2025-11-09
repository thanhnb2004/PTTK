<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<div class="menu-item">
    <h2>Nhập phụ tùng từ nhà cung cấp</h2>
    <p>Quản lý và nhập phụ tùng từ các nhà cung cấp</p>
    <a href="<%= contextPath %>/view/GdChinhNhanVienKho.jsp?page=purchase" class="button">Nhập phụ tùng</a>
</div>

<div class="menu-item">
    <h2>Khác</h2>
    <p>Các chức năng khác sẽ được cập nhật sau</p>
    <button disabled>Chức năng khác (Sắp có)</button>
</div>

