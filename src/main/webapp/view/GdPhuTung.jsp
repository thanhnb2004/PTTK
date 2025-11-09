<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
    // Parts sẽ được set từ SparePartServlet sau khi search
    String selectedSupplierId = (String) session.getAttribute("selectedSupplierId");
    boolean hasSearched = request.getAttribute("parts") != null || 
                          request.getParameter("partKeyword") != null;
    boolean hasSelectedSupplier = selectedSupplierId != null && !selectedSupplierId.isEmpty();
%>
<h3>Danh sách phụ tùng</h3>
<c:if test="${not empty parts}">
    <table>
        <thead>
            <tr>
                <th>Mã</th>
                <th>Tên</th>
                <th>Đơn vị</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="part" items="${parts}">
                <tr>
                    <td>${part.id}</td>
                    <td>${part.name}</td>
                    <td>${part.unit}</td>
                    <td>
                        <button class="action-btn btn-select" onclick="selectPart('${part.id}', '${part.name}')">Chọn</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>
<c:if test="${empty parts}">
    <div class="no-results">
        <% if (!hasSelectedSupplier) { %>
            <p>Vui lòng chọn nhà cung cấp trước khi tìm kiếm phụ tùng.</p>
        <% } else if (hasSearched) { %>
            <p>Không tìm thấy phụ tùng nào. Vui lòng thử lại với từ khóa khác.</p>
        <% } else { %>
            <p>Vui lòng tìm kiếm phụ tùng để xem danh sách.</p>
        <% } %>
    </div>
</c:if>

<script>
    function selectPart(partId, partName) {
        // Lấy supplierId từ session (đã được set khi chọn supplier)
        const supplierId = '<%= session.getAttribute("selectedSupplierId") != null ? session.getAttribute("selectedSupplierId") : "" %>';
        if (!supplierId) {
            alert('Vui lòng chọn nhà cung cấp trước!');
            return;
        }
        
        // Hiển thị modal để nhập số lượng và giá
        const partIdInput = document.getElementById('selectedPartId');
        const partNameDisplay = document.getElementById('selectedPartName');
        const supplierIdInput = document.getElementById('selectedSupplierId');
        const modal = document.getElementById('quantityPriceModal');
        
        console.log('selectPart called with:', { partId, partName, supplierId });
        console.log('Elements found:', {
            partIdInput: !!partIdInput,
            partNameDisplay: !!partNameDisplay,
            supplierIdInput: !!supplierIdInput,
            modal: !!modal
        });
        
        if (partIdInput && partNameDisplay && supplierIdInput && modal) {
            partIdInput.value = partId;
            partNameDisplay.textContent = partName;
            supplierIdInput.value = supplierId;
            
            // Lưu vào data attributes của modal để backup
            modal.setAttribute('data-part-id', partId);
            modal.setAttribute('data-supplier-id', supplierId);
            
            console.log('Values set:', {
                partIdInputValue: partIdInput.value,
                supplierIdInputValue: supplierIdInput.value
            });
            
            modal.style.display = 'block';
            
            // Kiểm tra lại sau khi set
            setTimeout(function() {
                console.log('After modal open - partId:', document.getElementById('selectedPartId')?.value);
                console.log('After modal open - supplierId:', document.getElementById('selectedSupplierId')?.value);
            }, 100);
        } else {
            console.error('Missing elements:', {
                partIdInput: !!partIdInput,
                partNameDisplay: !!partNameDisplay,
                supplierIdInput: !!supplierIdInput,
                modal: !!modal
            });
            alert('Có lỗi xảy ra! Vui lòng tải lại trang.');
        }
    }
</script>

