<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<div class="menu-item">
    <h2>Tìm kiếm thông tin dịch vụ</h2>
    <p>Tìm kiếm và xem thông tin chi tiết về các dịch vụ có sẵn</p>
    <a href="<%= contextPath %>/view/GdChinhKh.jsp?page=search" class="button">Tìm kiếm dịch vụ</a>
</div>

<div class="menu-item">
    <h2>Khác</h2>
    <p>Các chức năng khác sẽ được cập nhật sau</p>
    <button disabled>Chức năng khác (Sắp có)</button>
</div>

