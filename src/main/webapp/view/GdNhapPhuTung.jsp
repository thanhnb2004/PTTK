<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
    
    // Load selected parts từ session
    Object selectedPartsObj = session.getAttribute("selectedParts");
    if (selectedPartsObj != null) {
        request.setAttribute("selectedParts", selectedPartsObj);
    }
    
    // Suppliers và parts sẽ được load từ servlet trước khi include các JSP con
    // Nếu chưa có trong request, các JSP con sẽ hiển thị thông báo "Chưa có dữ liệu"
%>
<div class="purchase-layout">
    <!-- Left Panel: Supplier Management -->
    <div class="left-panel">
        <div class="panel-section">
            <jsp:include page="/view/GdTkNhaCungCap.jsp" />
        </div>
        <div class="panel-section">
            <jsp:include page="/view/GdNhaCungCap.jsp" />
        </div>
    </div>
    
    <!-- Right Panel: Part Management -->
    <div class="right-panel">
        <div class="panel-section">
            <jsp:include page="/view/GdTkPhuTung.jsp" />
        </div>
        <div class="panel-section">
            <jsp:include page="/view/GdPhuTung.jsp" />
        </div>
    </div>
</div>

<!-- Selected Parts Section -->
<div class="selected-parts">
    <jsp:include page="/view/GdPhuTungDaChon.jsp" />
</div>

<!-- Modal for quantity and price input -->
<div id="quantityPriceModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Nhập số lượng và giá</h3>
            <span class="close" onclick="closeQuantityModal()">&times;</span>
        </div>
        <jsp:include page="/view/GdNhapSoLuongVaGiaCa.jsp" />
    </div>
</div>

<script>
    function closeQuantityModal() {
        document.getElementById('quantityPriceModal').style.display = 'none';
    }
    
    window.onclick = function(event) {
        const modal = document.getElementById('quantityPriceModal');
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    }
</script>

