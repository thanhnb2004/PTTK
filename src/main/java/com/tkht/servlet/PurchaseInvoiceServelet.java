package com.tkht.servlet;

import com.tkht.dao.PartDao;
import com.tkht.dao.Purchase_InvoiceDao;
import com.tkht.dao.PurchaseInvoice_SupplierPartDao;
import com.tkht.dao.Supplier_PartDao;
import com.tkht.model.Member;
import com.tkht.model.Part;
import com.tkht.model.Purchase_Invoice;
import com.tkht.model.PurchaseInvoice_SupplierPart;
import com.tkht.model.Supplier_Part;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PurchaseInvoiceServelet extends HttpServlet {
    private final Purchase_InvoiceDao purchaseInvoiceDao = new Purchase_InvoiceDao();
    private final Supplier_PartDao supplierPartDao = new Supplier_PartDao();
    private final PartDao partDao = new PartDao();
    private final PurchaseInvoice_SupplierPartDao purchaseInvoice_SupplierPartDao = new PurchaseInvoice_SupplierPartDao();
    // Class để lưu thông tin phụ tùng đã chọn tạm thời
    public static class SelectedPartItem {
        private String partId;
        private String partName;
        private String supplierId;
        private Float quantity;
        private Float price;

        public SelectedPartItem(String partId, String partName, String supplierId, Float quantity, Float price) {
            this.partId = partId;
            this.partName = partName;
            this.supplierId = supplierId;
            this.quantity = quantity;
            this.price = price;
        }

        public String getPartId() { return partId; }
        public String getPartName() { return partName; }
        public String getSupplierId() { return supplierId; }
        public Float getQuantity() { return quantity; }
        public Float getPrice() { return price; }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        
        try {
            if ("finalize".equalsIgnoreCase(action)) {
                handleFinalize(req, resp);
            } else if ("viewInvoice".equalsIgnoreCase(action)) {
                handleViewInvoice(req, resp);
            } else if ("viewSelected".equalsIgnoreCase(action)) {
                handleViewSelectedParts(req, resp);
            } else if ("setSupplier".equalsIgnoreCase(action)) {
                handleSetSupplier(req, resp);
            } else {
                handleViewSelectedParts(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
    private void handleSetSupplier(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String supplierId = req.getParameter("supplierId");
        String supplierName = req.getParameter("supplierName");
        
        if (supplierId != null) {
            req.getSession().setAttribute("selectedSupplierId", supplierId);
            
            // Nếu có supplierName từ parameter, lưu vào session
            if (supplierName != null && !supplierName.isEmpty()) {
                req.getSession().setAttribute("selectedSupplierName", supplierName);
            } else {
                // Nếu không có supplierName, load từ database
                try {
                    com.tkht.dao.SupplierDao supplierDao = new com.tkht.dao.SupplierDao();
                    com.tkht.model.Supplier supplier = supplierDao.findById(supplierId);
                    if (supplier != null) {
                        req.getSession().setAttribute("selectedSupplierName", supplier.getName());
                    }
                } catch (SQLException e) {
                    // Ignore error, chỉ lưu id
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Đọc action từ cả query string và form data
        String action = req.getParameter("action");
        
        // Nếu không có trong parameter, thử đọc từ query string
        if (action == null || action.isEmpty()) {
            action = req.getQueryString();
            if (action != null && action.contains("action=")) {
                action = action.substring(action.indexOf("action=") + 7);
                if (action.contains("&")) {
                    action = action.substring(0, action.indexOf("&"));
                }
            }
        }
        
        // Debug: log action parameter
        System.out.println("PurchaseInvoiceServlet - doPost - action: " + action);
        System.out.println("PurchaseInvoiceServlet - doPost - query string: " + req.getQueryString());
        System.out.println("PurchaseInvoiceServlet - doPost - all parameters: " + req.getParameterMap().toString());
        
        try {
            if (action == null || action.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setContentType("text/plain;charset=UTF-8");
                resp.getWriter().write("Missing action parameter");
                return;
            }
            
            if ("addPart".equalsIgnoreCase(action)) {
                handleAddPart(req, resp);
            } else if ("removePart".equalsIgnoreCase(action)) {
                handleRemovePart(req, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setContentType("text/plain;charset=UTF-8");
                resp.getWriter().write("Invalid action: " + action);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("Database error: " + e.getMessage());
        }
    }

    private void handleAddPart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        HttpSession session = req.getSession();
        
        // Kiểm tra quyền staff
        if (!"staff".equals(session.getAttribute("userRole"))) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }
        
        Member staffMember = (Member) session.getAttribute("staffMember");
        if (staffMember == null || staffMember.getStaffId() == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Staff information not found");
            return;
        }
        
        String partId = req.getParameter("partId");
        String supplierId = req.getParameter("supplierId");
        String quantityStr = req.getParameter("quantity");
        String priceStr = req.getParameter("price");
        
        // Debug: log all parameters
        System.out.println("handleAddPart - partId: " + partId);
        System.out.println("handleAddPart - supplierId: " + supplierId);
        System.out.println("handleAddPart - quantity: " + quantityStr);
        System.out.println("handleAddPart - price: " + priceStr);
        System.out.println("handleAddPart - all parameters: " + req.getParameterMap().toString());
        
        if (partId == null || supplierId == null || quantityStr == null || priceStr == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("Missing parameters - partId: " + partId + ", supplierId: " + supplierId + ", quantity: " + quantityStr + ", price: " + priceStr);
            return;
        }
        
        try {
            Float quantity = Float.parseFloat(quantityStr);
            Float price = Float.parseFloat(priceStr);
            
            if (quantity <= 0 || price < 0) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quantity or price");
                return;
            }
            
            // Lấy thông tin phụ tùng
            Part part = partDao.findById(partId);
            if (part == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Part not found");
                return;
            }
            
            // Kiểm tra hoặc tạo purchase invoice với status "pending"
            String purchaseInvoiceId = (String) session.getAttribute("pendingPurchaseInvoiceId");
            if (purchaseInvoiceId == null) {
                // Tạo purchase invoice mới với status "pending"
                purchaseInvoiceId = generateInvoiceId();
                Purchase_Invoice invoice = new Purchase_Invoice();
                invoice.setId(purchaseInvoiceId);
                invoice.setPurchaseDate(new java.util.Date());
                invoice.setStaffId(staffMember.getStaffId());
                invoice.setStatus("pending");
                
                // Gọi DAO để tạo invoice
                purchaseInvoiceDao.create(invoice);
                
                // Lưu invoice id vào session
                session.setAttribute("pendingPurchaseInvoiceId", purchaseInvoiceId);
            }
            
            // Tìm hoặc tạo supplier_part_id
            Supplier_Part supplierPart = supplierPartDao.findBySupplierIdAndPartId(supplierId, partId);
            String supplierPartId;
            
            if (supplierPart == null) {
                // Tạo supplier_part mới
                supplierPartId = UUID.randomUUID().toString();
                Supplier_Part newSupplierPart = new Supplier_Part();
                newSupplierPart.setId(supplierPartId);
                newSupplierPart.setSupplierId(supplierId);
                newSupplierPart.setPartId(partId);
                
                // Gọi DAO để tạo supplier_part
                supplierPartDao.create(newSupplierPart);
            } else {
                supplierPartId = supplierPart.getId();
            }
            
            // Tạo record trong PurchaseInvoice_SupplierPart
            PurchaseInvoice_SupplierPart purchaseInvoice_SupplierPart = new PurchaseInvoice_SupplierPart();
            purchaseInvoice_SupplierPart.setId(UUID.randomUUID().toString());
            purchaseInvoice_SupplierPart.setQuantity(quantity);
            purchaseInvoice_SupplierPart.setPrice(price);
            purchaseInvoice_SupplierPart.setSupplierPartId(supplierPartId);
            purchaseInvoice_SupplierPart.setPurchaseInvoiceId(purchaseInvoiceId);
            
            // Gọi DAO để tạo record
            purchaseInvoice_SupplierPartDao.create(purchaseInvoice_SupplierPart);
            
            // Lấy danh sách phụ tùng đã chọn từ session để hiển thị
            @SuppressWarnings("unchecked")
            List<SelectedPartItem> selectedParts = (List<SelectedPartItem>) session.getAttribute("selectedParts");
            if (selectedParts == null) {
                selectedParts = new ArrayList<>();
            }
            
            // Kiểm tra xem phụ tùng đã được chọn chưa (cùng partId và supplierId)
            boolean found = false;
            for (int i = 0; i < selectedParts.size(); i++) {
                SelectedPartItem item = selectedParts.get(i);
                if (item.getPartId().equals(partId) && item.getSupplierId().equals(supplierId)) {
                    // Cập nhật số lượng và giá (giá mới sẽ thay thế giá cũ)
                    selectedParts.set(i, new SelectedPartItem(partId, part.getName(), supplierId, 
                                               item.getQuantity() + quantity, price));
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                // Thêm mới
                selectedParts.add(new SelectedPartItem(partId, part.getName(), supplierId, quantity, price));
            }
            
            session.setAttribute("selectedParts", selectedParts);
            session.setAttribute("selectedSupplierId", supplierId);
            
            // Redirect về trang nhập phụ tùng
            resp.sendRedirect(req.getContextPath() + "/view/GdChinhNhanVienKho.jsp?page=purchase");
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("Lỗi định dạng số: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("Lỗi: " + e.getMessage());
        }
    }

    private void handleRemovePart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String partId = req.getParameter("partId");
        
        if (partId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing partId");
            return;
        }
        
        @SuppressWarnings("unchecked")
        List<SelectedPartItem> selectedParts = (List<SelectedPartItem>) session.getAttribute("selectedParts");
        if (selectedParts != null) {
            selectedParts.removeIf(item -> item.getPartId().equals(partId));
            session.setAttribute("selectedParts", selectedParts);
        }
        
        resp.sendRedirect(req.getContextPath() + "/view/GdChinhNhanVienKho.jsp?page=purchase");
    }

    private void handleViewSelectedParts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        
        @SuppressWarnings("unchecked")
        List<SelectedPartItem> selectedParts = (List<SelectedPartItem>) session.getAttribute("selectedParts");
        if (selectedParts == null) {
            selectedParts = new ArrayList<>();
        }
        
        req.setAttribute("selectedParts", selectedParts);
        
        // Nếu là request include, chỉ set attribute, không forward
        String includeRequest = req.getParameter("include");
        if ("true".equals(includeRequest)) {
            // Đã set attribute, return
            return;
        }
        req.getRequestDispatcher("/view/GdPhuTungDaChon.jsp").forward(req, resp);
    }

    private void handleFinalize(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        HttpSession session = req.getSession();
        
        // Kiểm tra quyền staff
        if (!"staff".equals(session.getAttribute("userRole"))) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }
        
        Member staffMember = (Member) session.getAttribute("staffMember");
        if (staffMember == null || staffMember.getStaffId() == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Staff information not found");
            return;
        }
        
        // Lấy purchase invoice pending từ session
        String invoiceId = (String) session.getAttribute("pendingPurchaseInvoiceId");
        if (invoiceId == null) {
            req.setAttribute("error", "Không có hóa đơn nào để hoàn tất");
            req.getRequestDispatcher("/view/GdChinhNhanVienKho.jsp?page=purchase").forward(req, resp);
            return;
        }
        
        try {
            // Lấy purchase invoice từ database
            Purchase_Invoice invoice = purchaseInvoiceDao.findById(invoiceId);
            if (invoice == null) {
                req.setAttribute("error", "Hóa đơn không tồn tại");
                req.getRequestDispatcher("/view/GdChinhNhanVienKho.jsp?page=purchase").forward(req, resp);
                return;
            }
            
            // Kiểm tra status phải là "pending"
            if (!"pending".equals(invoice.getStatus())) {
                req.setAttribute("error", "Hóa đơn đã được xử lý");
                req.getRequestDispatcher("/view/GdChinhNhanVienKho.jsp?page=purchase").forward(req, resp);
                return;
            }
            
            // Update status từ "pending" thành "completed"
            invoice.setStatus("completed");
            
            // Gọi DAO để update invoice
            purchaseInvoiceDao.update(invoice);
            
            // Xóa các thông tin tạm thời khỏi session
            session.removeAttribute("selectedParts");
            session.removeAttribute("selectedSupplierId");
            session.removeAttribute("pendingPurchaseInvoiceId");
            
            // Redirect đến trang xem hóa đơn
            resp.sendRedirect(req.getContextPath() + "/purchaseInvoice?action=viewInvoice&invoiceId=" + invoiceId);
            
        } catch (SQLException e) {
            throw new ServletException("Error finalizing invoice: " + e.getMessage(), e);
        }
    }

    private void handleViewInvoice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String invoiceId = req.getParameter("invoiceId");
        
        if (invoiceId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing invoiceId");
            return;
        }
        
        Purchase_Invoice invoice = purchaseInvoiceDao.findById(invoiceId);
        if (invoice == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found");
            return;
        }
        
        // Lấy tất cả purchaseinvoice_supplierpart của hóa đơn
        List<PurchaseInvoice_SupplierPart> allPurchaseInvoiceSupplierParts = purchaseInvoice_SupplierPartDao.findAll();
        List<PurchaseInvoice_SupplierPart> invoicePurchaseInvoiceSupplierParts = new ArrayList<>();
        for (PurchaseInvoice_SupplierPart pis : allPurchaseInvoiceSupplierParts) {
            if (invoiceId.equals(pis.getPurchaseInvoiceId())) {
                invoicePurchaseInvoiceSupplierParts.add(pis);
            }
        }
        
        // Tạo danh sách items để hiển thị
        List<Map<String, Object>> invoiceItems = new ArrayList<>();
        for (PurchaseInvoice_SupplierPart pis : invoicePurchaseInvoiceSupplierParts) {
            // Lấy supplier_part từ supplier_part_id
            Supplier_Part supplierPart = supplierPartDao.findById(pis.getSupplierPartId());
            if (supplierPart == null) {
                continue;
            }
            
            // Lấy part từ part_id
            Part part = partDao.findById(supplierPart.getPartId());
            
            Map<String, Object> item = new HashMap<>();
            item.put("partId", supplierPart.getPartId());
            item.put("partName", part != null ? part.getName() : "Unknown");
            item.put("quantity", pis.getQuantity());
            item.put("price", pis.getPrice());
            item.put("supplierId", supplierPart.getSupplierId());
            invoiceItems.add(item);
        }
        
        req.setAttribute("invoice", invoice);
        req.setAttribute("invoiceItems", invoiceItems);
        req.getRequestDispatcher("/view/GdHoaDonNhap.jsp").forward(req, resp);
    }

    private String generateInvoiceId() {
        // Tạo ID dựa trên timestamp và UUID
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new java.util.Date());
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "PI-" + dateStr + "-" + uuid;
    }
}
