<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String contextPath = request.getContextPath();
%>
<div class="search-form">
    <form method="get" action="<%= contextPath %>/GdTimKiem">
        <input type="text" name="keyword" placeholder="Nhập tên dịch vụ cần tìm..." value="${param.keyword}">
        <button type="submit">Tìm kiếm</button>
    </form>
</div>

<div class="results">
    <c:if test="${not empty services}">
        <h2>Kết quả tìm kiếm:</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên dịch vụ</th>
                    <th>ID Hóa đơn</th>
                    <th>Chi tiết</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="service" items="${services}">
                    <tr>
                        <td>${service.id}</td>
                        <td>${service.name}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty service.salesInvoiceIds}">
                                    ${fn:join(service.salesInvoiceIds, ", ")}
                                </c:when>
                                <c:otherwise>
                                    Không có
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><a href="<%= contextPath %>/searchService?id=${service.id}">Xem chi tiết</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    
    <c:if test="${empty services and not empty param.keyword}">
        <div class="no-results">
            <p>Không tìm thấy dịch vụ nào với từ khóa "${param.keyword}"</p>
        </div>
    </c:if>
    
    <c:if test="${empty services and empty param.keyword}">
        <div class="no-results">
            <p>Vui lòng nhập từ khóa để tìm kiếm dịch vụ</p>
        </div>
    </c:if>
</div>

