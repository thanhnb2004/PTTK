<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<c:choose>
    <c:when test="${not empty service}">
        <h2>Thông tin dịch vụ</h2>
        <div class="detail-item">
            <div class="detail-label">ID Dịch vụ:</div>
            <div class="detail-value">${service.id}</div>
        </div>
        <div class="detail-item">
            <div class="detail-label">Tên dịch vụ:</div>
            <div class="detail-value">${service.name}</div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="no-service">
            <p>Không tìm thấy thông tin dịch vụ.</p>
            <p><a href="<%= contextPath %>/view/GdChinhKh.jsp?page=search" class="button">Quay về tìm kiếm</a></p>
        </div>
    </c:otherwise>
</c:choose>

