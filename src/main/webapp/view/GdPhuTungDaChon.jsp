<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
    // Selected parts sẽ được set từ session hoặc PurchaseInvoiceServlet
%>
<h3>Phụ tùng đã chọn</h3>
<c:if test="${not empty selectedParts}">
    <table>
        <thead>
            <tr>
                <th>Mã nhà cung cấp</th>
                <th>Mã phụ tùng</th>
                <th>Tên phụ tùng</th>
                <th>Số lượng</th>
                <th>Giá (VNĐ)</th>
                <th>Thành tiền (VNĐ)</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${selectedParts}">
                <tr>
                    <td>${item.supplierId}</td>
                    <td>${item.partId}</td>
                    <td>${item.partName}</td>
                    <td>${item.quantity}</td>
                    <td>${item.price}</td>
                    <td>${item.quantity * item.price}</td>
                    <td>
                        <button class="action-btn btn-remove" onclick="removePart('${item.partId}')">Xóa</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
        <tfoot>
            <tr style="font-weight: bold; background-color: #e3f2fd;">
                <td colspan="4" style="text-align: right;">Tổng cộng:</td>
                <td>
                    <c:set var="total" value="0" />
                    <c:forEach var="item" items="${selectedParts}">
                        <c:set var="total" value="${total + (item.quantity * item.price)}" />
                    </c:forEach>
                    ${total}
                </td>
                <td></td>
            </tr>
        </tfoot>
    </table>
    
    <div style="margin-top: 20px; text-align: right;">
        <button class="button btn-confirm" onclick="finalizeInvoice()">Chốt hóa đơn</button>
    </div>
</c:if>
<c:if test="${empty selectedParts}">
    <div class="no-results">
        <p>Chưa có phụ tùng nào được chọn. Vui lòng chọn nhà cung cấp và phụ tùng.</p>
    </div>
</c:if>

<script>
    function removePart(partId) {
        if (confirm('Bạn có chắc chắn muốn xóa phụ tùng này?')) {
            const formData = new FormData();
            formData.append('action', 'removePart');
            formData.append('partId', partId);
            
            fetch('<%= contextPath %>/purchaseInvoice', {
                method: 'POST',
                body: formData
            })
            .then(response => response.text())
            .then(data => {
                location.reload();
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Có lỗi xảy ra khi xóa phụ tùng!');
            });
        }
    }
    
    function finalizeInvoice() {
        if (confirm('Bạn có chắc chắn muốn chốt hóa đơn này?')) {
            window.location.href = '<%= contextPath %>/purchaseInvoice?action=finalize';
        }
    }
</script>

