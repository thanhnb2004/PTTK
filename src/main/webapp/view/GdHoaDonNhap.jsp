<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String contextPath = request.getContextPath();
    // Invoice details sẽ được set từ PurchaseInvoiceServlet
%>
<div style="max-width: 800px; margin: 0 auto;">
    <h2 style="text-align: center; color: #333; margin-bottom: 30px;">Hóa đơn nhập</h2>
    
    <c:if test="${not empty invoice}">
        <div style="background-color: #f9f9f9; padding: 20px; border-radius: 5px; margin-bottom: 20px;">
            <p><strong>Mã hóa đơn:</strong> ${invoice.id}</p>
            <p><strong>Ngày nhập:</strong> 
                <fmt:formatDate value="${invoice.purchaseDate}" pattern="dd/MM/yyyy" />
            </p>
            <p><strong>Nhân viên:</strong> ${invoice.staffId}</p>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>Mã nhà cung cấp</th>
                    <th>Mã phụ tùng</th>
                    <th>Tên phụ tùng</th>
                    <th>Số lượng</th>
                    <th>Giá (VNĐ)</th>
                    <th>Thành tiền (VNĐ)</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${invoiceItems}">
                    <tr>
                        <td>${item.supplierId}</td>
                        <td>${item.partId}</td>
                        <td>${item.partName}</td>
                        <td>${item.quantity}</td>
                        <td><fmt:formatNumber value="${item.price}" type="number" /></td>
                        <td><fmt:formatNumber value="${item.quantity * item.price}" type="number" /></td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr style="font-weight: bold; background-color: #e3f2fd;">
                    <td colspan="4" style="text-align: right;">Tổng cộng:</td>
                    <td>
                        <c:set var="total" value="0" />
                        <c:forEach var="item" items="${invoiceItems}">
                            <c:set var="total" value="${total + (item.quantity * item.price)}" />
                        </c:forEach>
                        <fmt:formatNumber value="${total}" type="number" />
                    </td>
                </tr>
            </tfoot>
        </table>
        
        <div style="margin-top: 30px; text-align: center;">
            <a href="<%= contextPath %>/view/GdChinhNhanVienKho.jsp?page=purchase" class="button">Tiếp tục nhập</a>
            <a href="<%= contextPath %>/view/GdChinhNhanVienKho.jsp?page=menu" class="button" style="background-color: #4CAF50;">Về menu</a>
        </div>
    </c:if>
    
    <c:if test="${empty invoice}">
        <div class="no-results">
            <p>Không có thông tin hóa đơn.</p>
        </div>
    </c:if>
</div>

