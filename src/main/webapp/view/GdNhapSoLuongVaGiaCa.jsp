<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<form method="POST" action="<%= contextPath %>/purchaseInvoice" id="quantityPriceForm">
    <input type="hidden" name="action" value="addPart">
    <input type="hidden" name="partId" id="selectedPartId">
    <input type="hidden" name="supplierId" id="selectedSupplierId">
    
    <div class="form-group">
        <label>Phụ tùng:</label>
        <p id="selectedPartName" style="padding: 10px; background-color: #f5f5f5; border-radius: 5px;"></p>
    </div>
    
    <div class="form-group">
        <label for="quantity">Số lượng:</label>
        <input type="number" name="quantity" id="quantity" min="1" required>
    </div>
    
    <div class="form-group">
        <label for="price">Giá (VNĐ):</label>
        <input type="number" name="price" id="price" min="0" step="0.01" required>
    </div>
    
    <div style="display: flex; gap: 10px; justify-content: flex-end;">
        <button type="button" onclick="closeModal()" style="background-color: #666;">Hủy</button>
        <button type="submit" class="btn-confirm">Xác nhận</button>
    </div>
</form>

<script>
    function closeModal() {
        const modal = document.getElementById('quantityPriceModal');
        if (modal) {
            modal.style.display = 'none';
            document.getElementById('quantityPriceForm').reset();
        }
    }
    
    // Hàm để kiểm tra và log các giá trị hiện tại
    function checkValues() {
        const partId = document.getElementById('selectedPartId')?.value;
        const supplierId = document.getElementById('selectedSupplierId')?.value;
        const quantity = document.getElementById('quantity')?.value;
        const price = document.getElementById('price')?.value;
        
        console.log('Current values check:', {
            partId: partId,
            supplierId: supplierId,
            quantity: quantity,
            price: price
        });
        
        return { partId, supplierId, quantity, price };
    }
    
    // Kiểm tra giá trị mỗi khi modal được hiển thị
    const modal = document.getElementById('quantityPriceModal');
    if (modal) {
        const observer = new MutationObserver(function(mutations) {
            mutations.forEach(function(mutation) {
                if (mutation.type === 'attributes' && mutation.attributeName === 'style') {
                    const display = modal.style.display;
                    if (display === 'block') {
                        console.log('Modal opened, checking values...');
                        setTimeout(checkValues, 100); // Đợi một chút để đảm bảo giá trị đã được set
                    }
                }
            });
        });
        observer.observe(modal, { attributes: true, attributeFilter: ['style'] });
    }
    
    // Submit form
    document.getElementById('quantityPriceForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        // Lấy các element
        const partIdElement = document.getElementById('selectedPartId');
        const supplierIdElement = document.getElementById('selectedSupplierId');
        const quantityElement = document.getElementById('quantity');
        const priceElement = document.getElementById('price');
        
        // Kiểm tra các element có tồn tại không
        if (!partIdElement || !supplierIdElement || !quantityElement || !priceElement) {
            console.error('Missing elements:', {
                partIdElement: !!partIdElement,
                supplierIdElement: !!supplierIdElement,
                quantityElement: !!quantityElement,
                priceElement: !!priceElement
            });
            alert('Có lỗi xảy ra! Vui lòng tải lại trang.');
            return;
        }
        
        // Lấy các giá trị từ input fields
        let partId = partIdElement.value;
        let supplierId = supplierIdElement.value;
        const quantity = quantityElement.value;
        const price = priceElement.value;
        
        // Nếu các giá trị bị null, thử lấy từ data attributes của modal
        if (!partId || !supplierId) {
            const modal = document.getElementById('quantityPriceModal');
            if (modal) {
                if (!partId) {
                    partId = modal.getAttribute('data-part-id');
                    console.log('Got partId from modal data attribute:', partId);
                }
                if (!supplierId) {
                    supplierId = modal.getAttribute('data-supplier-id');
                    console.log('Got supplierId from modal data attribute:', supplierId);
                }
            }
        }
        
        console.log('Before submit - partId element:', partIdElement);
        console.log('Before submit - partId:', partId);
        console.log('Before submit - supplierId element:', supplierIdElement);
        console.log('Before submit - supplierId:', supplierId);
        console.log('Before submit - quantity:', quantity);
        console.log('Before submit - price:', price);
        
        if (!partId || !supplierId || !quantity || !price) {
            alert('Vui lòng điền đầy đủ thông tin! partId: ' + partId + ', supplierId: ' + supplierId + ', quantity: ' + quantity + ', price: ' + price);
            return;
        }
        
        // Tạo URLSearchParams để gửi data
        const params = new URLSearchParams();
        params.append('action', 'addPart');
        params.append('partId', partId);
        params.append('supplierId', supplierId);
        params.append('quantity', quantity);
        params.append('price', price);
        
        // Debug: log params
        console.log('URLSearchParams entries:');
        for (let [key, value] of params.entries()) {
            console.log(key + ': ' + value);
        }
        
        console.log('URLSearchParams string:', params.toString());
        
        fetch('<%= contextPath %>/purchaseInvoice?action=addPart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: params.toString()
        })
        .then(response => {
            if (response.ok) {
                // Reload trang để cập nhật danh sách phụ tùng đã chọn
                window.location.reload();
            } else {
                // Lấy thông báo lỗi từ response
                return response.text().then(text => {
                    console.error('Server error:', text);
                    alert('Có lỗi xảy ra khi thêm phụ tùng! Lỗi: ' + (text || response.statusText));
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Có lỗi xảy ra khi thêm phụ tùng! Lỗi: ' + error.message);
        });
    });
</script>

