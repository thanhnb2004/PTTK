<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<h3>Tìm kiếm nhà cung cấp</h3>
<form class="search-form" method="GET" action="<%= contextPath %>/supplier">
    <input type="hidden" name="action" value="search">
    <input type="text" name="keyword" placeholder="Nhập tên hoặc địa chỉ nhà cung cấp..." 
           value="<%= request.getParameter("supplierKeyword") != null ? request.getParameter("supplierKeyword") : 
                     (request.getAttribute("supplierKeyword") != null ? request.getAttribute("supplierKeyword") : "") %>">
    <button type="submit">Tìm kiếm</button>
</form>

