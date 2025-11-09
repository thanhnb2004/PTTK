<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    String selectedSupplierId = (String) session.getAttribute("selectedSupplierId");
    String selectedSupplierName = (String) session.getAttribute("selectedSupplierName");
    boolean hasSelectedSupplier = selectedSupplierId != null && !selectedSupplierId.isEmpty();
%>
<h3>Tìm kiếm phụ tùng</h3>
<% if (hasSelectedSupplier) { %>
    <div style="background-color: #e3f2fd; padding: 10px; border-radius: 5px; margin-bottom: 15px; border-left: 4px solid #2196F3;">
        <p style="margin: 0; font-weight: bold; color: #1976D2;">
            📦 Nhà cung cấp đã chọn: 
            <span style="color: #333;"><%= selectedSupplierId != null ? selectedSupplierId : "" %></span>
            <% if (selectedSupplierName != null && !selectedSupplierName.isEmpty()) { %>
                - <span style="color: #333;"><%= selectedSupplierName %></span>
            <% } %>
        </p>
    </div>
<% } %>
<form class="search-form" method="GET" action="<%= contextPath %>/sparePart">
    <input type="hidden" name="action" value="search">
    <input type="text" name="keyword" placeholder="<%= hasSelectedSupplier ? "Nhập tên phụ tùng..." : "Vui lòng chọn nhà cung cấp trước" %>" 
           value="<%= request.getParameter("partKeyword") != null ? request.getParameter("partKeyword") : 
                     (request.getAttribute("partKeyword") != null ? request.getAttribute("partKeyword") : "") %>"
           id="partKeywordInput" <%= !hasSelectedSupplier ? "disabled" : "" %>>
    <button type="submit" <%= !hasSelectedSupplier ? "disabled" : "" %>>Tìm kiếm</button>
</form>
<% if (!hasSelectedSupplier) { %>
    <p style="color: #f44336; font-size: 14px; margin-top: 10px;">⚠️ Vui lòng chọn nhà cung cấp trước khi tìm kiếm phụ tùng.</p>
<% } %>

