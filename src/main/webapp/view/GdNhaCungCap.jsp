<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
    // Suppliers sẽ được set từ SupplierServlet sau khi search
    boolean hasSearched = request.getAttribute("suppliers") != null || 
                          request.getParameter("supplierKeyword") != null;
%>
<h3>Danh sách nhà cung cấp</h3>
<c:if test="${not empty suppliers}">
    <table id="supplierTable">
        <thead>
            <tr>
                <th>Mã</th>
                <th>Tên</th>
                <th>Địa chỉ</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="supplier" items="${suppliers}">
                <tr>
                    <td>${supplier.id}</td>
                    <td>${supplier.name}</td>
                    <td>${supplier.address}</td>
                    <td>
                        <button class="action-btn btn-select" onclick="selectSupplier('${supplier.id}')">Chọn</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>
<c:if test="${empty suppliers}">
    <div class="no-results">
        <% if (hasSearched) { %>
            <p>Không tìm thấy nhà cung cấp nào. Vui lòng thử lại với từ khóa khác.</p>
        <% } else { %>
            <p>Vui lòng tìm kiếm nhà cung cấp để xem danh sách.</p>
        <% } %>
    </div>
</c:if>

<script>
    function selectSupplier(supplierId) {
        // Lấy tên nhà cung cấp từ bảng
        const rows = document.querySelectorAll('#supplierTable tbody tr');
        let supplierName = '';
        rows.forEach(row => {
            const idCell = row.querySelector('td:first-child');
            if (idCell && idCell.textContent.trim() === supplierId) {
                const nameCell = row.querySelector('td:nth-child(2)');
                if (nameCell) {
                    supplierName = nameCell.textContent.trim();
                }
            }
        });
        
        // Lưu supplier đã chọn vào session (gửi cả id và tên)
        const url = '<%= contextPath %>/purchaseInvoice?action=setSupplier&supplierId=' + encodeURIComponent(supplierId) + 
                    '&supplierName=' + encodeURIComponent(supplierName);
        fetch(url, {
            method: 'GET'
        })
        .then(() => {
            // Highlight selected supplier
            rows.forEach(row => {
                row.style.backgroundColor = '';
                if (row.querySelector('td:first-child')?.textContent.trim() === supplierId) {
                    row.style.backgroundColor = '#e3f2fd';
                }
            });
            
            // Reload trang để load lại danh sách phụ tùng và hiển thị thông tin nhà cung cấp
            window.location.href = '<%= contextPath %>/view/GdChinhNhanVienKho.jsp?page=purchase&supplierId=' + supplierId;
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Có lỗi xảy ra khi chọn nhà cung cấp!');
        });
    }
    
    // Load selected supplier on page load
    window.addEventListener('DOMContentLoaded', function() {
        const selectedSupplierId = '<%= session.getAttribute("selectedSupplierId") != null ? session.getAttribute("selectedSupplierId") : "" %>';
        if (selectedSupplierId) {
            // Highlight selected supplier
            const rows = document.querySelectorAll('#supplierTable tbody tr');
            rows.forEach(row => {
                if (row.querySelector('td:first-child')?.textContent.trim() === selectedSupplierId) {
                    row.style.backgroundColor = '#e3f2fd';
                }
            });
        }
    });
</script>

